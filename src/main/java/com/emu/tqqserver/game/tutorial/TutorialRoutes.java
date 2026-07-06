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
    private void handleFuncTutorial(ChannelHandlerContext ctx, FullHttpRequest req, String path) {
        log.info(path);
        UserEntity me = requireUser(req);
        if (me != null) {
            String title = getJsonBody(req).path("title").asText("");
            if (!title.isEmpty()) {
                java.util.List<com.fasterxml.jackson.databind.JsonNode> funcTutorials = com.emu.tqqserver.masterdata.MasterDataLoader.getList("func_tutorial.json");
                for (com.fasterxml.jackson.databind.JsonNode node : funcTutorials) {
                    if (title.equals(node.path("title").asText())) {
                        int id = node.path("id").asInt();
                        userService.addFuncTutorial(me.getUserId(), id);
                        log.info("Saved func tutorial {} ({}) for user {}", title, id, me.getUserId());
                        break;
                    }
                }
            }
        }
        sendNocontent(ctx, req);
    }

    @Route("/functutorial/done")
    public void funcDone(ChannelHandlerContext ctx, FullHttpRequest req) { handleFuncTutorial(ctx, req, "functutorial/done"); }
    @Route("/functutorial/enhance")
    public void funcEnhance(ChannelHandlerContext ctx, FullHttpRequest req) { handleFuncTutorial(ctx, req, "functutorial/enhance"); }
    @Route("/functutorial/appointment")
    public void funcAppointment(ChannelHandlerContext ctx, FullHttpRequest req) { handleFuncTutorial(ctx, req, "functutorial/appointment"); }
}
