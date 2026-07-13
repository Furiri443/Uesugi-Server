package com.emu.tqqserver.game.likability.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/likability/ranking")
public class LikabilityRankingHandler extends BaseRoute {


    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("likability/ranking");
        com.emu.tqqserver.proto.pkg_proto.DearpointRanking res = com.emu.tqqserver.proto.pkg_proto.DearpointRanking.newBuilder()
            .setRanking(com.emu.tqqserver.proto.pkg_proto.RankingData.newBuilder().build())
            .build();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, res.toByteArray());
    
    }
}
