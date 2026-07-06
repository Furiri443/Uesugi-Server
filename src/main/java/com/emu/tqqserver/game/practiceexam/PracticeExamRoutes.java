package com.emu.tqqserver.game.practiceexam;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class PracticeExamRoutes extends BaseRoute {
    @Route("/practice_exam/chapter/myranks")
    public void chapterMyRanks(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("practice_exam/chapter/myranks"); sendNocontent(ctx, req); }
    @Route("/practice_exam/myrank")
    public void myRank(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("practice_exam/myrank"); sendNocontent(ctx, req); }
    @Route("/practice_exam/ranking/score/daily")
    public void rankingScoreDaily(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("practice_exam/ranking/score/daily"); sendNocontent(ctx, req); }
    @Route("/practice_exam/ranking/score/total")
    public void rankingScoreTotal(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("practice_exam/ranking/score/total"); sendNocontent(ctx, req); }
    @Route("/practice_exam/report")
    public void report(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("practice_exam/report"); sendNocontent(ctx, req); }
    @Route("/practice_exam/stats")
    public void stats(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("practice_exam/stats"); sendNocontent(ctx, req); }
}
