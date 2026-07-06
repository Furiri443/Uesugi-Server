package com.emu.tqqserver.game.story;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class StoryRoutes extends BaseRoute {
    @Route("/story/read")
    public void read(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("story/read"); sendNocontent(ctx, req); }
    @Route("/story/group/open")
    public void groupOpen(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("story/group/open"); sendNocontent(ctx, req); }
}
