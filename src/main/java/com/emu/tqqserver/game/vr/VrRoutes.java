package com.emu.tqqserver.game.vr;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class VrRoutes extends BaseRoute {
    @Route("/vr/open")
    public void open(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("vr/open"); sendNocontent(ctx, req); }
    @Route("/vr/watch")
    public void watch(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("vr/watch"); sendNocontent(ctx, req); }
}
