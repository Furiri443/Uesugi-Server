package com.emu.tqqserver.game.news;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class NewsRoutes extends BaseRoute {
    @Route("/news/read")
    public void read(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("news/read"); sendNocontent(ctx, req); }
}
