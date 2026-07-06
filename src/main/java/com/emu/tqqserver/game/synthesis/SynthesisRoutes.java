package com.emu.tqqserver.game.synthesis;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class SynthesisRoutes extends BaseRoute {
    @Route("/synthesis/exec")
    public void exec(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("synthesis/exec"); sendNocontent(ctx, req); }
}
