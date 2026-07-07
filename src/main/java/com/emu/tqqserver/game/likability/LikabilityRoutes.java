package com.emu.tqqserver.game.likability;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class LikabilityRoutes extends BaseRoute {
    @Route("/likability/set")
    public void set(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("likability/set"); sendNocontent(ctx, req); }

    @Route("/likability/ranking")
    public void ranking(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("likability/ranking");
        com.emu.tqqserver.proto.pkg_proto.DearpointRanking res = com.emu.tqqserver.proto.pkg_proto.DearpointRanking.newBuilder()
            .setRanking(com.emu.tqqserver.proto.pkg_proto.RankingData.newBuilder().build())
            .build();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, res.toByteArray());
    }
    
    @Route("/dearpoint/ranking")
    public void dearpointRanking(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("dearpoint/ranking");
        com.emu.tqqserver.proto.pkg_proto.DearpointRanking res = com.emu.tqqserver.proto.pkg_proto.DearpointRanking.newBuilder()
            .setRanking(com.emu.tqqserver.proto.pkg_proto.RankingData.newBuilder().build())
            .build();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, res.toByteArray());
    }
}
