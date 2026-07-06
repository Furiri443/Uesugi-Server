package com.emu.tqqserver.game.collection;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class CollectionRoutes extends BaseRoute {
    private final com.emu.tqqserver.game.user.StoredDataService storedDataService = new com.emu.tqqserver.game.user.StoredDataService();
    private final CollectionDao collectionDao = new CollectionDao();

    @Route("/collection/info")
    public void info(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("collection/info");
        UserEntity me = requireUser(req);
        
        com.emu.tqqserver.proto.pkg_proto.Collection.Builder colBuilder = com.emu.tqqserver.proto.pkg_proto.Collection.newBuilder()
            .setStoredData(storedDataService.build(me))
            .setCollection(collectionDao.getCollection(me.getUserId()));
            
        java.util.List<com.emu.tqqserver.proto.pkg_proto.CollectionPage> pages = collectionDao.getCollectionPages(me.getUserId());
        
        if (pages.isEmpty()) {
            // Load default layouts from master data
            java.util.List<com.fasterxml.jackson.databind.JsonNode> layouts = com.emu.tqqserver.masterdata.MasterDataLoader.getList("collection_layout.json");
            int idx = 1;
            for (com.fasterxml.jackson.databind.JsonNode layout : layouts) {
                if (layout.has("is_default") && layout.get("is_default").asInt() == 1) {
                    pages.add(com.emu.tqqserver.proto.pkg_proto.CollectionPage.newBuilder()
                        .setUid((int)me.getUserId())
                        .setIdx(idx++)
                        .setTitle(layout.get("name").asText())
                        .setLayoutId(layout.get("id").asInt())
                        .setThemeId(1)
                        .setPageSort(idx - 1)
                        .setTransitionId(1)
                        .build());
                }
            }
            collectionDao.saveCollectionPages(me.getUserId(), pages);
        }
        
        colBuilder.addAllPageList(pages);
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, colBuilder.build().toByteArray());
    }
    @Route("/collection/otherinfo")
    public void otherInfo(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("collection/otherinfo"); sendNocontent(ctx, req); }
    @Route("/collection/setting")
    public void setting(ChannelHandlerContext ctx, FullHttpRequest req) {
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
    @Route("/collection/pagesetting")
    public void pageSetting(ChannelHandlerContext ctx, FullHttpRequest req) {
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
    @Route("/collection/pagetransition")
    public void pageTransition(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("collection/pagetransition"); sendNocontent(ctx, req); }
    @Route("/collection/releasepage")
    public void releasePage(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("collection/releasepage"); sendNocontent(ctx, req); }
}
