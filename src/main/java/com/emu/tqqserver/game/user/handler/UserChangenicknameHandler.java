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

@Route("/user/changenickname")
public class UserChangenicknameHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("user/changenickname");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            String nickname = "";
            if (body.has("nickname")) nickname = body.get("nickname").asText();
            else if (body.has("name")) nickname = body.get("name").asText();
            else if (body.size() > 0) nickname = body.elements().next().asText();
            
            if (!nickname.isEmpty()) {
                userService.updateNickname(userId, nickname);
            }
        }
        sendNocontent(ctx, req);
    
    }
}
