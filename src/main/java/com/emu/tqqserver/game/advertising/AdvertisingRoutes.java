package com.emu.tqqserver.game.advertising;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class AdvertisingRoutes extends BaseRoute {
    @Route("/advertising/view")
    public void view(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("advertising/view"); sendNocontent(ctx, req); }
}
