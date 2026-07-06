package com.emu.tqqserver.game.review;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class ReviewRoutes extends BaseRoute {
    @Route("/review/accept")
    public void accept(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("review/accept"); sendNocontent(ctx, req); }
    @Route("/review/decline")
    public void decline(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("review/decline"); sendNocontent(ctx, req); }
    @Route("/review/suspend")
    public void suspend(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("review/suspend"); sendNocontent(ctx, req); }
}
