package com.emu.tqqserver.game.log;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class LogRoutes extends BaseRoute {
    @Route("/log/bootfinish")
    public void bootFinish(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("log/bootfinish"); sendNocontent(ctx, req); }
    @Route("/log/send")
    public void send(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("log/send"); sendNocontent(ctx, req); }
    @Route("/log/storyreadskip")
    public void storyReadSkip(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("log/storyreadskip"); sendNocontent(ctx, req); }
}
