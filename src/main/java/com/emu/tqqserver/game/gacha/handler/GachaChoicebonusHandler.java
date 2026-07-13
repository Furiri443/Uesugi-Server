package com.emu.tqqserver.game.gacha.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/gacha/choicebonus")
public class GachaChoicebonusHandler extends BaseRoute {


    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 
        log.info("gacha/choicebonus");
        try {
            String bodyString = req.content().toString(io.netty.util.CharsetUtil.UTF_8);
            log.info("choiceBonus requested with body: {}", bodyString);
            
            com.emu.tqqserver.game.user.UserEntity user = requireUser(req);
            io.netty.handler.codec.http.QueryStringDecoder decoder = new io.netty.handler.codec.http.QueryStringDecoder(bodyString, false);
            java.util.Map<String, java.util.List<String>> params = decoder.parameters();
            
            int gachaId = Integer.parseInt(params.getOrDefault("gacha_id", java.util.List.of("0")).get(0));
            int targetId = Integer.parseInt(params.getOrDefault("target_id", java.util.List.of("0")).get(0));
            if (targetId == 0) targetId = Integer.parseInt(params.getOrDefault("choice_id", java.util.List.of("0")).get(0));
            if (targetId == 0) targetId = Integer.parseInt(params.getOrDefault("card_id", java.util.List.of("0")).get(0));
            
            com.emu.tqqserver.game.gacha.GachaDao gachaDao = new com.emu.tqqserver.game.gacha.GachaDao();
            int pending = gachaDao.getPendingChoiceCount(user.getUserId(), gachaId);
            if (pending <= 0) {
                log.warn("No pending choice bonus for user={} gacha={}", user.getUserId(), gachaId);
                HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.BAD_REQUEST, null);
                return;
            }
            
            // Deduct pending choice
            gachaDao.decrementPendingChoiceCount(user.getUserId(), gachaId);
            
            // Grant the card
            com.emu.tqqserver.proto.pkg_proto.GachaResult.Builder resultBuilder = com.emu.tqqserver.proto.pkg_proto.GachaResult.newBuilder();
            com.emu.tqqserver.proto.pkg_proto.Goods bonusGoods = com.emu.tqqserver.proto.pkg_proto.Goods.newBuilder()
                .setCategory(2) // Card
                .setTargetId(targetId)
                .setQuantity(1)
                .build();
            resultBuilder.addCountGachaRewards(bonusGoods);
            
            try {
                new com.emu.tqqserver.game.user.UserDao().ensureDefaultCards(user.getUserId(), new int[]{targetId});
                gachaDao.logGacha(user.getUserId(), gachaId, targetId, 5); // Milestone reward
            } catch (Exception e) {}
            
            com.emu.tqqserver.game.user.UserService userService = new com.emu.tqqserver.game.user.UserService();
            user = userService.findById(user.getUserId());
            resultBuilder.setStoredData(new com.emu.tqqserver.game.user.StoredDataService().build(user));
            
            HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, resultBuilder.build().toByteArray());
            return;
        } catch (Exception e) {
            log.error("Failed to process choiceBonus", e);
        }
        sendNocontent(ctx, req); 
    
    }
}
