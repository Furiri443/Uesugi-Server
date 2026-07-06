package com.emu.tqqserver.game.tutorial;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class TutorialRoutes extends BaseRoute {
    @Route("/tutorial/done")
    public void done(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("tutorial/done");
        UserEntity me = requireUser(req);
        if (me != null) {
            int stepId = readIntField(req, 1);
            if (stepId > 0) {
                userService.updateTutorialStep(me.getUserId(), stepId);
                me.setTutorialStep(stepId);
            }
            if (stepId >= 999) {
                com.emu.tqqserver.game.user.StoredDataService storedDataService = new com.emu.tqqserver.game.user.StoredDataService();
                com.emu.tqqserver.proto.pkg_proto.TutorialFinishResult response = com.emu.tqqserver.proto.pkg_proto.TutorialFinishResult.newBuilder()
                    .setStoredData(storedDataService.build(me))
                    .build();
                HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
                return;
            }
        }
        sendNocontent(ctx, req);
    }
    @Route("/functutorial/done")
    public void funcDone(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("functutorial/done"); sendNocontent(ctx, req); }
    @Route("/functutorial/enhance")
    public void funcEnhance(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("functutorial/enhance"); sendNocontent(ctx, req); }
    @Route("/functutorial/appointment")
    public void funcAppointment(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("functutorial/appointment"); sendNocontent(ctx, req); }
}
