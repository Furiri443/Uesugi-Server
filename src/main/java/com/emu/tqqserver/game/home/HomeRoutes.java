package com.emu.tqqserver.game.home;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class HomeRoutes extends BaseRoute {
    @Route("/home/actor/set")
    public void actorSet(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("home/actor/set");
        UserEntity me = requireUser(req);
        if (me != null) {
            try {
                byte[] body = getBody(req);
                com.emu.tqqserver.proto.pkg_puser.HomeActorList list = com.emu.tqqserver.proto.pkg_puser.HomeActorList.parseFrom(body);
                if (list.getListCount() > 0) {
                    com.emu.tqqserver.game.home.HomeService homeService = new com.emu.tqqserver.game.home.HomeService();
                    homeService.saveHomeActors(me.getUserId(), list.getListList());
                }
            } catch (Exception e) {
                log.error("Failed to parse HomeActorList", e);
            }
        }
        sendNocontent(ctx, req);
    }
}
