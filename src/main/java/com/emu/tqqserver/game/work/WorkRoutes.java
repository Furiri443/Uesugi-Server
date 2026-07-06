package com.emu.tqqserver.game.work;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class WorkRoutes extends BaseRoute {
    @Route("/work/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("work/start"); sendNocontent(ctx, req); }
    @Route("/work/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("work/clear"); sendNocontent(ctx, req); }
    @Route("/work/reset")
    public void reset(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("work/reset"); sendNocontent(ctx, req); }
    @Route("/work/update")
    public void update(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("work/update"); sendNocontent(ctx, req); }
    @Route("/work/shorten")
    public void shorten(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("work/shorten"); sendNocontent(ctx, req); }
}
