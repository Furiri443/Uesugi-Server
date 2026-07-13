package com.emu.tqqserver.game.cooking.handler;

import com.emu.tqqserver.game.cooking.CookingService;
import com.emu.tqqserver.proto.pkg_proto.CookingShorten;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;
import java.util.List;

@Route("/cooking/shorten")
public class CookingShortenHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final CookingService cookingService = new CookingService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("cooking/shorten");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        UserEntity user = userService.findById(userId);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        int trayId = readIntField(req, 1);
        // Instant complete by setting remaining time to 0
        cookingService.startCooking(userId, trayId, 10001, 0);

        CookingShorten response = CookingShorten.newBuilder()
            .setStoredData(storedDataService.build(user))
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    
    }
}
