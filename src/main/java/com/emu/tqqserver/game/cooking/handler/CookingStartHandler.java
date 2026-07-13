package com.emu.tqqserver.game.cooking.handler;

import com.emu.tqqserver.game.cooking.CookingService;
import com.emu.tqqserver.proto.pkg_proto.CookingStart;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;
import java.util.List;

@Route("/cooking/start")
public class CookingStartHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final CookingService cookingService = new CookingService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("cooking/start");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        UserEntity user = userService.findById(userId);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        int[] params = readTwoIntFields(req);
        int trayId = params[0] > 0 ? params[0] : 1;
        int recipeId = params[1] > 0 ? params[1] : 10001;

        // Cook for 60 seconds default
        cookingService.startCooking(userId, trayId, recipeId, 60);

        CookingStart response = CookingStart.newBuilder()
            .setStoredData(storedDataService.build(user))
            .setForecastRank(3) // forecast high quality
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    
    }
}
