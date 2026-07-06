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
        if (ticketId > 0 && pay > 0) {
            boolean success = userService.deductItem(user.getUserId(), ticketId, pay);
            if (!success) {
                log.warn("User does not have enough ticket_id=" + ticketId + " amount=" + pay);
                // Optionally return an error code, but we continue for now
            }
        }

        int drawCount = (mode == 2) ? 10 : 1;
        com.emu.tqqserver.proto.pkg_proto.GachaResult.Builder resultBuilder = com.emu.tqqserver.proto.pkg_proto.GachaResult.newBuilder();
        
        int[] drawnCards = new int[drawCount];
        java.util.Map<Integer, Integer> cardCounts = new java.util.HashMap<>();
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < drawCount; i++) {
            // Pick a random card from valid range (e.g., 10001 - 10020)
            int cardId = 10001 + rand.nextInt(20);
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
