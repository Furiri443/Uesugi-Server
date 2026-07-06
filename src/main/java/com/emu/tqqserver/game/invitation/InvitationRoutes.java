package com.emu.tqqserver.game.invitation;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class InvitationRoutes extends BaseRoute {
    @Route("/invitation/top")
    public void top(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("invitation/top"); sendNocontent(ctx, req); }
    @Route("/invitation/verify")
    public void verify(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("invitation/verify"); sendNocontent(ctx, req); }
    @Route("/invitation/share")
    public void share(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("invitation/share"); sendNocontent(ctx, req); }
}
