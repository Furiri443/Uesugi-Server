package com.emu.tqqserver.game.unitskill;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class UnitSkillRoutes extends BaseRoute {
    @Route("/unitskill/enhance")
    public void enhance(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("unitskill/enhance"); sendNocontent(ctx, req); }
    @Route("/unitskill/reduce")
    public void reduce(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unitskill/reduce"); sendNocontent(ctx, req); }
    @Route("/unitskill/set")
    public void set(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unitskill/set"); sendNocontent(ctx, req); }
}
