package com.emu.tqqserver.game.options;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class OptionsRoutes extends BaseRoute {
    @Route("/options/change")
    public void change(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("options/change"); sendNocontent(ctx, req); }
}
