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
        
        com.fasterxml.jackson.databind.JsonNode json = getJsonBody(req);
        log.info("unit/set payload: {}", json);
        if (json != null && json.has("idx")) {
            int idx = json.get("idx").asInt();
            String name = json.has("unit_name") ? json.get("unit_name").asText() : "Team " + idx;
            int[] members = new int[5];
            long[] cards = new long[5];
            
            for (int i = 0; i < 5; i++) {
                members[i] = json.has("member_id" + (i+1)) ? json.get("member_id" + (i+1)).asInt() : (i+1);
                cards[i] = json.has("card_id" + (i+1)) ? json.get("card_id" + (i+1)).asLong() : 0L;
            }
            
            unitDao.saveUnit(me.getUserId(), idx, name, members, cards);
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
