package com.emu.tqqserver.game.unit.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/unit/del")
public class UnitDelHandler extends BaseRoute {

    private final com.emu.tqqserver.game.user.UnitDao unitDao = new com.emu.tqqserver.game.user.UnitDao();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 log.debug("unit/del"); sendNocontent(ctx, req); 
    }
}
