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
}
