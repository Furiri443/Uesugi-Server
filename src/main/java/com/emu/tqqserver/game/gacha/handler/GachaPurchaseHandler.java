package com.emu.tqqserver.game.gacha.handler;

import com.emu.tqqserver.game.gacha.GachaService;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/gacha/purchase")
public class GachaPurchaseHandler extends BaseRoute {


    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 
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
        com.emu.tqqserver.game.gacha.GachaDao gachaDao = new com.emu.tqqserver.game.gacha.GachaDao();
        com.emu.tqqserver.proto.pkg_pmisc.GachaHistory gachaHistory = gachaDao.getGachaHistory(user.getUserId(), gachaId);
        
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
                if (pay == 1) { // Free Roll
                    if (gachaDef.getFreeSingleGachaCount() <= 0 || gachaHistory.getSingleCnt() >= 1) { // Using single_cnt as free_roll_used
                        log.warn("Free roll not available or already used for user={} gacha={}", user.getUserId(), gachaId);
                        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.BAD_REQUEST, null);
                        return;
                    }
                    cost = 0;
                } else if (pay == 5) { // Ticket (Item)
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
        
        if (pay == 1) {
            gachaDao.markFreeRollUsed(user.getUserId(), gachaId);
        }
        gachaDao.incrementRollCount(user.getUserId(), gachaId, drawCount);
        
        // Fetch updated history
        gachaHistory = gachaDao.getGachaHistory(user.getUserId(), gachaId);

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

        // Check Milestone (Pity)
        if (gachaDef != null && gachaDef.getLimitCount() > 0 && gachaHistory.getLimitCnt() >= gachaDef.getLimitCount()) {
            gachaDao.resetLimitCount(user.getUserId(), gachaId);
            
            // If there's a choice bonus configured
            if (gachaDef.getGachaBonusChoiceId() > 0) {
                // Determine if there's only 1 choice (auto-reward)
                java.util.List<com.emu.tqqserver.data.resources.GachaBonusChoiceDef> choices = 
                    com.emu.tqqserver.data.GameData.getGachaBonusChoiceDataTable().values().stream()
                        .filter(c -> c.getId() == gachaDef.getGachaBonusChoiceId())
                        .collect(java.util.stream.Collectors.toList());
                
                if (choices.size() == 1) {
                    // Auto-give the only card
                    int autoCardId = choices.get(0).getTargetId();
                    cardCounts.put(autoCardId, cardCounts.getOrDefault(autoCardId, 0) + 1);
                    com.emu.tqqserver.proto.pkg_proto.Goods bonusGoods = com.emu.tqqserver.proto.pkg_proto.Goods.newBuilder()
                        .setCategory(2) // Card
                        .setTargetId(autoCardId)
                        .setQuantity(1)
                        .build();
                    resultBuilder.addCountGachaRewards(bonusGoods);
                    // Insert into DB
                    int[] autoDrawn = new int[]{autoCardId};
                    try {
                        new com.emu.tqqserver.game.user.UserDao().ensureDefaultCards(user.getUserId(), autoDrawn);
                        gachaDao.logGacha(user.getUserId(), gachaId, autoCardId, 5); // Milestone reward
                    } catch (Exception e) {}
                } else {
                    // Provide a Choice Bonus token/index for the user to use in /gacha/choicebonus
                    // Let the client know they reached pity and have a choice
                    resultBuilder.setChoiceBonusIdx(gachaDef.getGachaBonusChoiceId());
                    gachaDao.incrementPendingChoiceCount(user.getUserId(), gachaId);
                }
            }
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
}
