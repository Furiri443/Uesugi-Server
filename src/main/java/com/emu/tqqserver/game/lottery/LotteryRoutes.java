package com.emu.tqqserver.game.lottery;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class LotteryRoutes extends BaseRoute {
    @Route("/lottery/list")
    public void list(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("lottery/list"); sendNocontent(ctx, req); }
    @Route("/lottery/apply")
    public void apply(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("lottery/apply"); sendNocontent(ctx, req); }
    @Route("/lottery/agreeterms")
    public void agreeTerms(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("lottery/agreeterms"); sendNocontent(ctx, req); }
    @Route("/lottery/seenresult")
    public void seenResult(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("lottery/seenresult"); sendNocontent(ctx, req); }
}
