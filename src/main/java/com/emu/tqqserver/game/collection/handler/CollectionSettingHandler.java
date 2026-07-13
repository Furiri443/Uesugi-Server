package com.emu.tqqserver.game.collection.handler;

import com.emu.tqqserver.game.collection.CollectionDao;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/collection/setting")
public class CollectionSettingHandler extends BaseRoute {

    private final com.emu.tqqserver.game.user.StoredDataService storedDataService = new com.emu.tqqserver.game.user.StoredDataService();
    private final CollectionDao collectionDao = new CollectionDao();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("collection/setting");
        UserEntity me = requireUser(req);
        try {
            byte[] bytes = new byte[req.content().readableBytes()];
            req.content().readBytes(bytes);
            com.emu.tqqserver.proto.pkg_puser.Collection col = com.emu.tqqserver.proto.pkg_puser.Collection.parseFrom(bytes);
            collectionDao.updateCollection(me.getUserId(), col.getBgmId());
        } catch (Exception e) {
            log.error("Failed to parse collection setting", e);
        }
        sendNocontent(ctx, req); 
    
    }
}
