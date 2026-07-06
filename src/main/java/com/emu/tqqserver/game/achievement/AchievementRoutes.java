package com.emu.tqqserver.game.achievement;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class AchievementRoutes extends BaseRoute {
    @Route("/achievement/ranking")
    public void ranking(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("achievement/ranking"); sendNocontent(ctx, req); }
}
