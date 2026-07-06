package com.emu.tqqserver.game.image;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class ImageRoutes extends BaseRoute {
    @Route("/image/jpeg")
    public void jpeg(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("image/jpeg"); sendNocontent(ctx, req); }
    @Route("/image/png")
    public void png(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("image/png"); sendNocontent(ctx, req); }
}
