package com.emu.tqqserver.game.cooking.handler;

import com.emu.tqqserver.game.cooking.CookingService;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;
import java.util.List;

@Route("/cooking/helpers")
public class CookingHelpersHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final CookingService cookingService = new CookingService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.debug("cooking/helpers");
        sendNocontent(ctx, req);
    
    }
}
