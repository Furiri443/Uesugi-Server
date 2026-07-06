package com.emu.tqqserver.game.teambattle;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class TeamBattleRoutes extends BaseRoute {
    @Route("/teambattle/top")
    public void top(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/top"); sendNocontent(ctx, req); }
    @Route("/teambattle/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("teambattle/start"); sendNocontent(ctx, req); }
    @Route("/teambattle/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("teambattle/clear"); sendNocontent(ctx, req); }
    @Route("/teambattle/fail")
    public void fail(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/fail"); sendNocontent(ctx, req); }
    @Route("/teambattle/retire")
    public void retire(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/retire"); sendNocontent(ctx, req); }
    @Route("/teambattle/continue")
    public void resume(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/continue"); sendNocontent(ctx, req); }
    @Route("/teambattle/skip")
    public void skip(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/skip"); sendNocontent(ctx, req); }
    @Route("/teambattle/timeover")
    public void timeover(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/timeover"); sendNocontent(ctx, req); }
    @Route("/teambattle/result")
    public void result(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("teambattle/result"); sendNocontent(ctx, req); }
    @Route("/teambattle/result/read")
    public void resultRead(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/result/read"); sendNocontent(ctx, req); }
    @Route("/teambattle/ranking")
    public void ranking(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/ranking"); sendNocontent(ctx, req); }
    @Route("/teambattle/apply")
    public void apply(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("teambattle/apply"); sendNocontent(ctx, req); }
    @Route("/teambattle/join")
    public void join(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("teambattle/join"); sendNocontent(ctx, req); }
    @Route("/teambattle/step/clear")
    public void stepClear(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/step/clear"); sendNocontent(ctx, req); }
    @Route("/teambattle/teamstatus")
    public void teamStatus(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/teamstatus"); sendNocontent(ctx, req); }
    @Route("/teambattle/agreeterms")
    public void agreeTerms(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/agreeterms"); sendNocontent(ctx, req); }
    @Route("/teambattle/tutorialdone")
    public void tutorialDone(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/tutorialdone"); sendNocontent(ctx, req); }
    @Route("/teambattle/setselectiontarget")
    public void setSelectionTarget(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/setselectiontarget"); sendNocontent(ctx, req); }
}
