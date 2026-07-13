package com.emu.tqqserver.game.gacha.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/gacha/panel_board")
public class GachaPanel_boardHandler extends BaseRoute {


    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 
        log.debug("gacha/panel_board"); 
        
        java.util.Map<String, String> params = new java.util.HashMap<>();
        if (req.method().equals(io.netty.handler.codec.http.HttpMethod.GET)) {
            io.netty.handler.codec.http.QueryStringDecoder decoder = new io.netty.handler.codec.http.QueryStringDecoder(req.uri());
            for (java.util.Map.Entry<String, java.util.List<String>> entry : decoder.parameters().entrySet()) {
                params.put(entry.getKey(), entry.getValue().get(0));
            }
        }
        int gachaId = Integer.parseInt(params.getOrDefault("gacha_id", "0"));

        com.emu.tqqserver.proto.pkg_proto.GachaPanelBoard res = com.emu.tqqserver.proto.pkg_proto.GachaPanelBoard.newBuilder()
            .setGachaId(gachaId)
            .build();
        HttpApiHandler.sendProto(ctx, req, io.netty.handler.codec.http.HttpResponseStatus.OK, res.toByteArray());
    
    }
}
