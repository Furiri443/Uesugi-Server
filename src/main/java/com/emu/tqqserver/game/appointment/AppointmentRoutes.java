package com.emu.tqqserver.game.appointment;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class AppointmentRoutes extends BaseRoute {
    @Route("/appointment/set")
    public void set(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("appointment/set"); sendNocontent(ctx, req); }
    @Route("/appointment/cancel")
    public void cancel(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("appointment/cancel"); sendNocontent(ctx, req); }
    @Route("/appointment/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("appointment/clear"); sendNocontent(ctx, req); }
    @Route("/appointment/updatestatus")
    public void updateStatus(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("appointment/updatestatus"); sendNocontent(ctx, req); }
    @Route("/appointment/surprisereceive")
    public void surpriseReceive(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("appointment/surprisereceive"); sendNocontent(ctx, req); }
    @Route("/appointment/surprisereset")
    public void surpriseReset(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("appointment/surprisereset"); sendNocontent(ctx, req); }
}
