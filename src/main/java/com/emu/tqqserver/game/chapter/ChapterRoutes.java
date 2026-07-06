package com.emu.tqqserver.game.chapter;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class ChapterRoutes extends BaseRoute {
    @Route("/chapter/release")
    public void release(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("chapter/release"); sendNocontent(ctx, req); }
}
