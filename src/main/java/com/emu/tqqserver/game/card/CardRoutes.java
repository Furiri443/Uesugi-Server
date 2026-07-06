package com.emu.tqqserver.game.card;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class CardRoutes extends BaseRoute {
    @Route("/card/enhance")
    public void enhance(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/enhance"); sendNocontent(ctx, req); }
    @Route("/card/enhanceawake")
    public void enhanceAwake(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/enhanceawake"); sendNocontent(ctx, req); }
    @Route("/card/enhancebyitem")
    public void enhanceByItem(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/enhancebyitem"); sendNocontent(ctx, req); }
    @Route("/card/awake")
    public void awake(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/awake"); sendNocontent(ctx, req); }
    @Route("/card/reawake")
    public void reAwake(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/reawake"); sendNocontent(ctx, req); }
    @Route("/card/sell")
    public void sell(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/sell"); sendNocontent(ctx, req); }
    @Route("/card/protect")
    public void protect(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("card/protect"); sendNocontent(ctx, req); }
    @Route("/card/unprotect")
    public void unprotect(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("card/unprotect"); sendNocontent(ctx, req); }
    @Route("/card/changepicture")
    public void changePicture(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("card/changepicture"); sendNocontent(ctx, req); }
    @Route("/card/bgm")
    public void bgm(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("card/bgm"); sendNocontent(ctx, req); }
    @Route("/card/receivegrowthreward")
    public void receiveGrowthReward(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("card/receivegrowthreward"); sendNocontent(ctx, req); }
}
