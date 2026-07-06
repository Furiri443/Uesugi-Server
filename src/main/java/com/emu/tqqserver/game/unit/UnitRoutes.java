package com.emu.tqqserver.game.unit;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class UnitRoutes extends BaseRoute {
    @Route("/unit/set")
    public void set(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unit/set"); sendNocontent(ctx, req); }
    @Route("/unit/del")
    public void delete(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unit/del"); sendNocontent(ctx, req); }
    @Route("/unit/editname")
    public void editName(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unit/editname"); sendNocontent(ctx, req); }
    @Route("/unit/auto")
    public void auto(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unit/auto"); sendNocontent(ctx, req); }
}
