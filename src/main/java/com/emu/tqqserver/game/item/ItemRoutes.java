package com.emu.tqqserver.game.item;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class ItemRoutes extends BaseRoute {
    @Route("/item/list")
    public void list(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("item/list"); sendNocontent(ctx, req); }
    @Route("/item/use")
    public void use(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("item/use"); sendNocontent(ctx, req); }
    @Route("/item/sell")
    public void sell(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("item/sell"); sendNocontent(ctx, req); }
    @Route("/item/exchangepool")
    public void exchangePool(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("item/exchangepool"); sendNocontent(ctx, req); }
    @Route("/item/password")
    public void password(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("item/password"); sendNocontent(ctx, req); }
    @Route("/item/likability/use")
    public void likabilityUse(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("item/likability/use"); sendNocontent(ctx, req); }
}
