package com.emu.tqqserver.game.fcm;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class FcmRoutes extends BaseRoute {
    @Route("/fcm/token")
    public void token(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("fcm/token"); sendNocontent(ctx, req); }
}
