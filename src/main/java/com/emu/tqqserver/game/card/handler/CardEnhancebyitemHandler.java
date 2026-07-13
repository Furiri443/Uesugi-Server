package com.emu.tqqserver.game.card.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Route("/card/enhancebyitem")
public class CardEnhancebyitemHandler extends BaseRoute {


    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("card/enhancebyitem");
        try {
            UserEntity me = requireUser(req);
            
            io.netty.handler.codec.http.QueryStringDecoder decoder = new io.netty.handler.codec.http.QueryStringDecoder("?" + getBodyString(req));
            java.util.Map<String, java.util.List<String>> params = decoder.parameters();
            
            if (params.containsKey("base")) {
                long baseId = Long.parseLong(params.get("base").get(0));
                
                com.emu.tqqserver.game.user.UserDao userDao = new com.emu.tqqserver.game.user.UserDao();
                java.util.List<com.emu.tqqserver.game.user.CardEntity> userCards = userDao.getUserCards(me.getUserId());
                com.emu.tqqserver.game.user.CardEntity baseCard = null;
                for (com.emu.tqqserver.game.user.CardEntity c : userCards) {
                    if (c.getId() == baseId) {
                        baseCard = c;
                        break;
                    }
                }
                
                if (baseCard != null) {
                    // Fetch definitions
                    com.emu.tqqserver.data.resources.CardDef cardDef = com.emu.tqqserver.data.GameData.getCardDataTable().get(baseCard.getCardId());
                    if (cardDef == null) {
                        sendNocontent(ctx, req);
                        return;
                    }
                    
                    com.emu.tqqserver.data.resources.CardMaxLevelDef maxLevelDef = com.emu.tqqserver.data.GameData.getCardMaxLevelDataTable().get(cardDef.getMaxLevelId());
                    int maxLevel = 15;
                    if (maxLevelDef != null) {
                        maxLevel = maxLevelDef.getInitMaxLv();
                        int rank = baseCard.getLimitbreakRank();
                        if (rank >= 1) maxLevel += maxLevelDef.getLimitbreakRank1();
                        if (rank >= 2) maxLevel += maxLevelDef.getLimitbreakRank2();
                        if (rank >= 3) maxLevel += maxLevelDef.getLimitbreakRank3();
                        if (rank >= 4) maxLevel += maxLevelDef.getLimitbreakRank4();
                        if (rank >= 5) maxLevel += maxLevelDef.getLimitbreakRank5();
                    }

                    int levelGrowthId = cardDef.getLevelGrowthId();
                    int baseExp = 0;
                    for (com.emu.tqqserver.data.resources.LevelGrowthDef growth : com.emu.tqqserver.data.GameData.getLevelGrowthDataTable().values()) {
                        if (growth.getId() == levelGrowthId && growth.getLevel() == 1) {
                            baseExp = growth.getValue();
                            break;
                        }
                    }
                    
                    int oldExp = baseCard.getExp();
                    if (oldExp <= baseExp) {
                        oldExp = baseExp + 1;
                    }
                    
                    int addedExp = 5000; // placeholder 5000 EXP for item
                    int newExp = oldExp + addedExp;
                    int newLevel = 1;
                    
                    for (com.emu.tqqserver.data.resources.LevelGrowthDef growth : com.emu.tqqserver.data.GameData.getLevelGrowthDataTable().values()) {
                        if (growth.getId() == levelGrowthId) {
                            if (newExp >= growth.getValue()) {
                                if (growth.getLevel() > newLevel) {
                                    newLevel = growth.getLevel();
                                }
                            }
                        }
                    }
                    
                    if (newLevel > maxLevel) {
                        newLevel = maxLevel;
                        int finalMaxLevel = maxLevel;
                        com.emu.tqqserver.data.resources.LevelGrowthDef maxGrowth = com.emu.tqqserver.data.GameData.getLevelGrowthDataTable().values().stream()
                            .filter(g -> g.getId() == levelGrowthId && g.getLevel() == finalMaxLevel)
                            .findFirst().orElse(null);
                        if (maxGrowth != null && newExp > maxGrowth.getValue()) {
                            newExp = maxGrowth.getValue();
                        }
                    }
                    
                    addedExp = newExp - oldExp; // Calculate actual added EXP for the response

                    userDao.updateCardExpAndLevel(baseId, newExp, newLevel);
                    
                    // Return EnhancementResult
                    com.emu.tqqserver.proto.pkg_proto.EnhancementResult result = com.emu.tqqserver.proto.pkg_proto.EnhancementResult.newBuilder()
                        .setStoredData(new com.emu.tqqserver.game.user.StoredDataService().build(me))
                        .setBeforeResourceIdx(1)
                        .setAfterResourceIdx(1)
                        .setPassiveSkill1(true)
                        .build();
                    HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, result.toByteArray());
                    return;
                }
            }
            sendNocontent(ctx, req);
        } catch (Exception e) {
            log.error("card/enhancebyitem error", e);
            sendNocontent(ctx, req);
        }
    
    }
}
