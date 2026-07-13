package com.emu.tqqserver.game.block.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.ListUsers;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import com.emu.tqqserver.game.friend.FriendService;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;
import java.util.List;

@Route("/block/del")
public class BlockDelHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final FriendService friendService = new FriendService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.debug("block/del");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            int targetId = readIntField(req, 1);
            if (targetId > 0) {
                friendService.unblockUser(userId, targetId);
            }
        }
        sendNocontent(ctx, req);
    
    }
}
