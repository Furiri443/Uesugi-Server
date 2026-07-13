package com.emu.tqqserver.game.encore.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/encore/unitskill/use")
public class EncoreUnitskillUseHandler extends BaseRoute {


    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 log.debug("encore/unitskill/use"); sendNocontent(ctx, req); 
    }
}
