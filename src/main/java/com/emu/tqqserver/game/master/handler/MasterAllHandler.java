package com.emu.tqqserver.game.master.handler;

import com.emu.tqqserver.game.master.MasterDataService;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.game.user.StoredDataService;

@Route("/master/all")
public class MasterAllHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        String clientVersion = getMasterVersion(req);
        log.info("master/all clientVersion={}", clientVersion);

        UserEntity user = requireUser(req);

        byte[] bytes = MasterDataService.getInstance().getAllBytes();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, bytes);
    
    }
}
