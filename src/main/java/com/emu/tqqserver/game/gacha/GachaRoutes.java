package com.emu.tqqserver.game.gacha;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class GachaRoutes extends BaseRoute {
    @Route("/gacha/purchase")
    public void purchase(ChannelHandlerContext ctx, FullHttpRequest req) { 
        log.info("gacha/purchase"); 
        
        String bodyString = "";
        try {
            byte[] body = getBody(req);
            bodyString = new String(body, java.nio.charset.StandardCharsets.UTF_8);
            log.info("RAW: " + bodyString);
        } catch (Exception e) {
            log.error("Failed to decode req", e);
        }

        com.emu.tqqserver.game.user.UserEntity user = requireUser(req);
        
        java.util.Map<String, String> params = new java.util.HashMap<>();
        if (bodyString != null && !bodyString.isEmpty()) {
            for (String param : bodyString.split("&")) {
                String[] pair = param.split("=");
                if (pair.length == 2) {
                    params.put(pair[0], pair[1]);
                }
            }
        }

        int gachaId = Integer.parseInt(params.getOrDefault("gacha_id", "0"));
        int mode = Integer.parseInt(params.getOrDefault("mode", "1"));
        int pay = Integer.parseInt(params.getOrDefault("pay", "1"));
        int ticketId = Integer.parseInt(params.getOrDefault("ticket_id", "0"));

        com.emu.tqqserver.game.user.UserService userService = new com.emu.tqqserver.game.user.UserService();
        
        // Deduct ticket/currency
        boolean deductionSuccess = true;
        int cost = 0;
        int deductFreeJewel = 0;
        int deductPaidJewel = 0;
        int deductTicket = 0;

        com.emu.tqqserver.data.resources.GachaDef gachaDef = com.emu.tqqserver.data.GameData.getGachaDataTable().get(gachaId);
        if (gachaDef != null) {
            com.emu.tqqserver.data.resources.GachaOptionDef opt = com.emu.tqqserver.data.GameData.getGachaOptionDataTable().get(gachaDef.getGachaOptionId());
            if (opt != null) {
                if (pay == 5) { // Ticket (Item)
                    cost = (mode == 2) ? opt.getLumpNumTicket() : 1;
                    if (cost <= 0) cost = 1; // Fallback
                    deductTicket = cost;
                } else if (pay == 3) { // Normal Jewel payment (Free + Paid)
                    cost = (mode == 2) ? opt.getLumpPrice() : opt.getSinglePrice();
                    if (user.getJewel() >= cost) { // getJewel() is Free Jewel
                        deductFreeJewel = cost;
                    } else {
                        deductFreeJewel = user.getJewel();
                        deductPaidJewel = cost - user.getJewel();
                    }
                } else if (pay == 4) { // Paid Jewel ONLY
                    cost = (mode == 2) ? opt.getPaidLumpPrice() : opt.getPaidSinglePrice();
                    if (cost <= 0) cost = (mode == 2) ? opt.getLumpPrice() : opt.getSinglePrice();
                    deductPaidJewel = cost;
                } else {
                    cost = (mode == 2) ? opt.getLumpPrice() : opt.getSinglePrice();
                    deductFreeJewel = cost;
                }
            }
        }

        if (deductTicket > 0 && ticketId > 0 && pay == 5) {
            deductionSuccess = userService.deductItem(user.getUserId(), ticketId, deductTicket);
            if (!deductionSuccess) {
                log.warn("User does not have enough ticket_id={} amount={}", ticketId, deductTicket);
                HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.BAD_REQUEST, null);
                return;
            }
        } else if (deductFreeJewel > 0 || deductPaidJewel > 0) {
            deductionSuccess = userService.deductJewels(user.getUserId(), deductFreeJewel, deductPaidJewel);
            if (!deductionSuccess) {
                log.warn("User does not have enough jewels. Need Free: {}, Paid: {}", deductFreeJewel, deductPaidJewel);
                HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.BAD_REQUEST, new byte[0]);
                return;
            }
        }

        int drawCount = (mode == 2) ? 10 : 1;
        com.emu.tqqserver.proto.pkg_proto.GachaResult.Builder resultBuilder = com.emu.tqqserver.proto.pkg_proto.GachaResult.newBuilder();
        
        int[] drawnCards = new int[drawCount];
        java.util.Map<Integer, Integer> cardCounts = new java.util.HashMap<>();
        
        GachaService gachaService = new GachaService();
        for (int i = 0; i < drawCount; i++) {
            // For 10-pulls (mode == 2), the 10th card (index 9) is the guaranteed bonus pull
            boolean isBonus = (mode == 2 && i == 9);
            int cardId = gachaService.drawCard(gachaId, isBonus);
            drawnCards[i] = cardId;
            cardCounts.put(cardId, cardCounts.getOrDefault(cardId, 0) + 1);
        }

        for (java.util.Map.Entry<Integer, Integer> entry : cardCounts.entrySet()) {
            com.emu.tqqserver.proto.pkg_proto.Goods card = com.emu.tqqserver.proto.pkg_proto.Goods.newBuilder()
                .setCategory(2)
                .setTargetId(entry.getKey())
                .setQuantity(entry.getValue())
                .build();
            resultBuilder.addCards(card);
        }

        try {
            new com.emu.tqqserver.game.user.UserDao().ensureDefaultCards(user.getUserId(), drawnCards);
            com.emu.tqqserver.game.gacha.GachaDao gachaDao = new com.emu.tqqserver.game.gacha.GachaDao();
            for (int cardId : drawnCards) {
                gachaDao.logGacha(user.getUserId(), gachaId, cardId, 3); // Default rarity 3 for now
            }
        } catch (Exception e) {
            log.error("Failed to insert cards and logs", e);
        }

        // Reload user from DB to reflect the newly deducted coin and jewel balances
        user = userService.findById(user.getUserId());
            
        resultBuilder.setStoredData(new com.emu.tqqserver.game.user.StoredDataService().build(user));
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, resultBuilder.build().toByteArray());
    }
    @Route("/gacha/resetbonus")
    public void resetBonus(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/resetbonus"); sendNocontent(ctx, req); }
    @Route("/gacha/reset_box")
    public void resetBox(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/reset_box"); sendNocontent(ctx, req); }
    @Route("/gacha/switch_box")
    public void switchBox(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/switch_box"); sendNocontent(ctx, req); }
    @Route("/gacha/choicebonus")
    public void choiceBonus(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/choicebonus"); sendNocontent(ctx, req); }
    @Route("/gacha/panel_board")
    public void panelBoard(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/panel_board"); sendNocontent(ctx, req); }
    @Route("/gacha/panel_board/lot")
    public void panelBoardLot(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/panel_board/lot"); sendNocontent(ctx, req); }
    @Route("/gacha/panel_board/reset")
    public void panelBoardReset(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/panel_board/reset"); sendNocontent(ctx, req); }
    @Route("/gacha/panel_board/select_panel_reward")
    public void panelBoardSelectReward(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/panel_board/select_panel_reward"); sendNocontent(ctx, req); }
    @Route("/gacha/serialnumber/")
    public void serialNumber(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/serialnumber/"); sendNocontent(ctx, req); }
}
