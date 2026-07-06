package com.emu.tqqserver.game.quintupletgame;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class QuintupletGameRoutes extends BaseRoute {
    @Route("/quintuplet_game/send_result")
    public void sendResult(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("quintuplet_game/send_result"); sendNocontent(ctx, req); }
}
