package com.emu.tqqserver.game.challenge;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class ChallengeRoutes extends BaseRoute {
    @Route("/challenge/reward")
    public void reward(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("challenge/reward"); sendNocontent(ctx, req); }
    @Route("/challenge/newrank")
    public void newRank(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("challenge/newrank"); sendNocontent(ctx, req); }
    @Route("/challenge/clearbeginner")
    public void clearBeginner(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("challenge/clearbeginner"); sendNocontent(ctx, req); }
    @Route("/challenge/forcecleargroup")
    public void forceClearGroup(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("challenge/forcecleargroup"); sendNocontent(ctx, req); }
}
