package com.emu.tqqserver.game.cooking.handler;

import com.emu.tqqserver.game.cooking.CookingService;
import com.emu.tqqserver.proto.pkg_proto.CookingTrays;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;
import java.util.List;

@Route("/cooking/trays")
public class CookingTraysHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final CookingService cookingService = new CookingService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.debug("cooking/trays");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        long targetUid = userId;
        io.netty.handler.codec.http.QueryStringDecoder decoder = new io.netty.handler.codec.http.QueryStringDecoder(req.uri());
        List<String> targetUidParam = decoder.parameters().get("target_uid");
        if (targetUidParam != null && !targetUidParam.isEmpty()) {
            try {
                targetUid = Long.parseLong(targetUidParam.get(0));
            } catch (NumberFormatException ignored) {}
        } else {
            int bodyTarget = readIntField(req, 1);
            if (bodyTarget > 0) {
                targetUid = bodyTarget;
            }
        }

        UserEntity user = userService.findById(targetUid);
            if (user == null) throw new RuntimeException("User not found: " + targetUid);

        com.emu.tqqserver.proto.pkg_proto.CookingTrays response = CookingTrays.newBuilder()
            .setStoredData(storedDataService.build(user))
            .addAllTrays(cookingService.getTrays(targetUid))
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    
    }
}
