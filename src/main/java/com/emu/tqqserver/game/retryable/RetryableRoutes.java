package com.emu.tqqserver.game.retryable;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class RetryableRoutes extends BaseRoute {
    @Route("/retryable/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("retryable/start"); sendNocontent(ctx, req); }
    @Route("/retryable/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("retryable/clear"); sendNocontent(ctx, req); }
    @Route("/retryable/continue")
    public void resume(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("retryable/continue"); sendNocontent(ctx, req); }
    @Route("/retryable/fail")
    public void fail(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("retryable/fail"); sendNocontent(ctx, req); }
    @Route("/retryable/helper/use")
    public void helperUse(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("retryable/helper/use"); sendNocontent(ctx, req); }
    @Route("/retryable/chapter/reset")
    public void chapterReset(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("retryable/chapter/reset"); sendNocontent(ctx, req); }
}
