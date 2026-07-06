package com.emu.tqqserver.game.minigame;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class MiniGameRoutes extends BaseRoute {
    @Route("/mini_game/top")
    public void top(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("mini_game/top"); sendNocontent(ctx, req); }
    @Route("/mini_game/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("mini_game/start"); sendNocontent(ctx, req); }
    @Route("/mini_game/done")
    public void done(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("mini_game/done"); sendNocontent(ctx, req); }
    @Route("/mini_game/exchange")
    public void exchange(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("mini_game/exchange"); sendNocontent(ctx, req); }
    @Route("/mini_game/use")
    public void use(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("mini_game/use"); sendNocontent(ctx, req); }
}
