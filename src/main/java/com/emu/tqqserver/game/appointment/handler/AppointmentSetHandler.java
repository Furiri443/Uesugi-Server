package com.emu.tqqserver.game.appointment.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/appointment/set")
public class AppointmentSetHandler extends BaseRoute {


    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 log.info("appointment/set"); sendNocontent(ctx, req); 
    }
}
