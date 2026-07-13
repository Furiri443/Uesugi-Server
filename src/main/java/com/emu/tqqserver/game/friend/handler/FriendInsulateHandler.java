package com.emu.tqqserver.game.friend.handler;

import com.emu.tqqserver.game.friend.FriendService;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.RankListUser;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;
import java.util.ArrayList;
import java.util.List;

@Route("/friend/insulate")
public class FriendInsulateHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final FriendService friendService = new FriendService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.debug("friend/insulate");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            int targetId = 0;
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            if (body.has("target_id")) targetId = body.get("target_id").asInt();
            else if (body.has("uid")) targetId = body.get("uid").asInt();
            
            if (targetId > 0) {
                friendService.deleteFriend(userId, targetId);
            }
        }
        sendNocontent(ctx, req);
    
    }
}
