package com.emu.tqqserver.game.member;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class MemberRoutes extends BaseRoute {
    @Route("/member/resetdearlevelflag")
    public void resetDearLevelFlag(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("member/resetdearlevelflag"); sendNocontent(ctx, req); }
}
