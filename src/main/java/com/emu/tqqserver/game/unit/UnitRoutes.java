package com.emu.tqqserver.game.unit;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class UnitRoutes extends BaseRoute {
    private final com.emu.tqqserver.game.user.UnitDao unitDao = new com.emu.tqqserver.game.user.UnitDao();

    @Route("/unit/set")
    public void set(ChannelHandlerContext ctx, FullHttpRequest req) { 
        log.debug("unit/set"); 
        UserEntity me = requireUser(req);
        
        String bodyStr = getBodyString(req);
        log.info("unit/set RAW body: {}", bodyStr);
        
        io.netty.handler.codec.http.QueryStringDecoder decoder = new io.netty.handler.codec.http.QueryStringDecoder("?" + bodyStr);
        java.util.Map<String, java.util.List<String>> params = decoder.parameters();
        
        if (params.containsKey("uidx")) {
            int uidx = Integer.parseInt(params.get("uidx").get(0));
            int mode = params.containsKey("mode") ? Integer.parseInt(params.get("mode").get(0)) : 0;
            
            // Synchronize globally to prevent race conditions from concurrent unit/set requests
            synchronized(UnitRoutes.class) {
                // Fetch existing unit
                com.emu.tqqserver.proto.pkg_puser.Unit existingUnit = null;
                for (com.emu.tqqserver.proto.pkg_puser.Unit u : unitDao.getUnits(me.getUserId())) {
                    if (u.getIdx() == uidx) {
                        existingUnit = u;
                        break;
                    }
                }
                
                if (existingUnit != null) {
                    int[] members = { existingUnit.getMemberId1(), existingUnit.getMemberId2(), existingUnit.getMemberId3(), existingUnit.getMemberId4(), existingUnit.getMemberId5() };
                    long[] cards = { existingUnit.getCardId1(), existingUnit.getCardId2(), existingUnit.getCardId3(), existingUnit.getCardId4(), existingUnit.getCardId5() };
                    String name = existingUnit.getUnitName();
                    
                    if (mode == 1) {
                        java.util.List<String> memberIdsStr = params.get("member_id");
                        java.util.List<String> mainCardIdsStr = params.get("main_card_id");
                        if (memberIdsStr != null && mainCardIdsStr != null) {
                            for (int i = 0; i < Math.min(memberIdsStr.size(), 5); i++) {
                                members[i] = Integer.parseInt(memberIdsStr.get(i));
                            }
                            for (int i = 0; i < Math.min(mainCardIdsStr.size(), 5); i++) {
                                cards[i] = Long.parseLong(mainCardIdsStr.get(i));
                            }
                        }
                    }
                    
                    unitDao.saveUnit(me.getUserId(), uidx, name, members, cards);
                }
            }
        }
        
        com.emu.tqqserver.game.user.StoredDataService storedDataService = new com.emu.tqqserver.game.user.StoredDataService();
        com.emu.tqqserver.proto.pkg_proto.Nocontent.Builder builder = com.emu.tqqserver.proto.pkg_proto.Nocontent.newBuilder()
            .setStoredData(storedDataService.build(me));
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, builder.build().toByteArray());
    }

    @Route("/unit/del")
    public void delete(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unit/del"); sendNocontent(ctx, req); }
    @Route("/unit/editname")
    public void editName(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unit/editname"); sendNocontent(ctx, req); }
    @Route("/unit/auto")
    public void auto(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unit/auto"); sendNocontent(ctx, req); }
}
