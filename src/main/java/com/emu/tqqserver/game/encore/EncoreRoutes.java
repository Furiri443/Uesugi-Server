package com.emu.tqqserver.game.encore;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class EncoreRoutes extends BaseRoute {
    @Route("/encore/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("encore/start"); sendNocontent(ctx, req); }
    @Route("/encore/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("encore/clear"); sendNocontent(ctx, req); }
    @Route("/encore/fail")
    public void fail(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/fail"); sendNocontent(ctx, req); }
    @Route("/encore/retry")
    public void retry(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/retry"); sendNocontent(ctx, req); }
    @Route("/encore/continue")
    public void resume(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/continue"); sendNocontent(ctx, req); }
    @Route("/encore/timeover")
    public void timeover(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/timeover"); sendNocontent(ctx, req); }
    @Route("/encore/close")
    public void close(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/close"); sendNocontent(ctx, req); }
    @Route("/encore/helper/list")
    public void helperList(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/helper/list"); sendNocontent(ctx, req); }
    @Route("/encore/helper/use")
    public void helperUse(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/helper/use"); sendNocontent(ctx, req); }
    @Route("/encore/unitskill/use")
    public void unitSkillUse(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/unitskill/use"); sendNocontent(ctx, req); }
}
