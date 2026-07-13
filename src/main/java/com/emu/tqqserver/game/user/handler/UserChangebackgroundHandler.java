package com.emu.tqqserver.game.user.handler;

import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.Profile;
import com.emu.tqqserver.proto.pkg_proto.ProfilePictureForTimeline;
import com.emu.tqqserver.proto.pkg_proto.ProfilePictureForTimelineList;
import com.emu.tqqserver.game.user.StoredDataService;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.List;

@Route("/user/changebackground")
public class UserChangebackgroundHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("user/changebackground");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            int bgId = 0;
            if (body.has("bg_id")) bgId = body.get("bg_id").asInt();
            else if (body.has("background_id")) bgId = body.get("background_id").asInt();
            else if (body.has("id")) bgId = body.get("id").asInt();
            else if (body.size() > 0) bgId = body.elements().next().asInt();
            
            if (bgId > 0) {
                userService.updateBackground(userId, bgId);
                com.emu.tqqserver.game.home.HomeService homeService = new com.emu.tqqserver.game.home.HomeService();
                homeService.updateHomeBackground(userId, bgId);
            }
        }
        sendNocontent(ctx, req);
    
    }
}
