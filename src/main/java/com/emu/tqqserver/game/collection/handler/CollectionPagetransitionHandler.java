package com.emu.tqqserver.game.collection.handler;

import com.emu.tqqserver.game.collection.CollectionDao;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/collection/pagetransition")
public class CollectionPagetransitionHandler extends BaseRoute {

    private final com.emu.tqqserver.game.user.StoredDataService storedDataService = new com.emu.tqqserver.game.user.StoredDataService();
    private final CollectionDao collectionDao = new CollectionDao();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 log.debug("collection/pagetransition"); sendNocontent(ctx, req); 
    }
}
