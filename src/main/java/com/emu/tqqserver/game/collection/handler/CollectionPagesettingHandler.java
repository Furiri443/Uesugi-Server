package com.emu.tqqserver.game.collection.handler;

import com.emu.tqqserver.game.collection.CollectionDao;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/collection/pagesetting")
public class CollectionPagesettingHandler extends BaseRoute {

    private final com.emu.tqqserver.game.user.StoredDataService storedDataService = new com.emu.tqqserver.game.user.StoredDataService();
    private final CollectionDao collectionDao = new CollectionDao();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("collection/pagesetting");
        UserEntity me = requireUser(req);
        try {
            byte[] bytes = new byte[req.content().readableBytes()];
            req.content().readBytes(bytes);
            
            // It could be pkg_proto.Collection with page_list
            com.emu.tqqserver.proto.pkg_proto.Collection col = com.emu.tqqserver.proto.pkg_proto.Collection.parseFrom(bytes);
            if (col.getPageListCount() > 0) {
                collectionDao.saveCollectionPages(me.getUserId(), col.getPageListList());
            } else {
                log.warn("collection/pagesetting: no pages found in Collection. Maybe it's a different protobuf?");
            }
        } catch (Exception e) {
            log.error("Failed to parse collection/pagesetting", e);
        }
        sendNocontent(ctx, req); 
    
    }
}
