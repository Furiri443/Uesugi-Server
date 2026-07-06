package com.emu.tqqserver.game.bonds;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class BondsRoutes extends BaseRoute {
    @Route("/bonds/confessionevent")
    public void confessionEvent(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("bonds/confessionevent"); sendNocontent(ctx, req); }
    @Route("/bonds/step/reward")
    public void stepReward(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("bonds/step/reward"); sendNocontent(ctx, req); }
    @Route("/bonds/lastseenstep")
    public void lastSeenStep(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("bonds/lastseenstep"); sendNocontent(ctx, req); }
    @Route("/bonds/ranking")
    public void ranking(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("bonds/ranking"); sendNocontent(ctx, req); }
    @Route("/bonds/unlock")
    public void unlock(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("bonds/unlock"); sendNocontent(ctx, req); }
    @Route("/bonds/buy/premiumstep")
    public void buyPremiumStep(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("bonds/buy/premiumstep"); sendNocontent(ctx, req); }
}
