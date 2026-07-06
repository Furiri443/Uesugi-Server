package com.emu.tqqserver.game.photobooth;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class PhotoboothRoutes extends BaseRoute {
    @Route("/photobooth/hicheese")
    public void hicheese(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("photobooth/hicheese"); sendNocontent(ctx, req); }
    @Route("/photobooth/sns")
    public void sns(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("photobooth/sns"); sendNocontent(ctx, req); }
}
