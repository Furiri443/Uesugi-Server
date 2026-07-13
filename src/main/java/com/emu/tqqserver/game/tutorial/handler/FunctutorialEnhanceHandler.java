package com.emu.tqqserver.game.tutorial.handler;

import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/functutorial/enhance")
public class FunctutorialEnhanceHandler extends BaseRoute {
    private final UserService userService = new UserService();



    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 handleFuncTutorial(ctx, req, "functutorial/enhance"); 
    }

private void handleFuncTutorial(ChannelHandlerContext ctx, FullHttpRequest req, String path) {
        log.info(path);
        UserEntity me = requireUser(req);
        if (me != null) {
            String title = getJsonBody(req).path("title").asText("");
            if (!title.isEmpty()) {
                java.util.Collection<com.emu.tqqserver.data.resources.FuncTutorialDef> funcTutorials = com.emu.tqqserver.data.GameData.getFuncTutorialDataTable().values();
                for (com.emu.tqqserver.data.resources.FuncTutorialDef def : funcTutorials) {
                    if (title.equals(def.getTitle())) {
                        int id = def.getId();
                        userService.addFuncTutorial(me.getUserId(), id);
                        log.info("Saved func tutorial {} ({}) for user {}", title, id, me.getUserId());
                        break;
                    }
                }
            }
        }
        sendNocontent(ctx, req);
    }
}
