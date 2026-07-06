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

    @Route("/collection/info")
    public void info(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("collection/info");
        UserEntity me = requireUser(req);
        
        com.emu.tqqserver.proto.pkg_proto.Collection.Builder colBuilder = com.emu.tqqserver.proto.pkg_proto.Collection.newBuilder()
            .setStoredData(storedDataService.build(me));
            
        // Load default layouts from master data
        java.util.List<com.fasterxml.jackson.databind.JsonNode> layouts = com.emu.tqqserver.masterdata.MasterDataLoader.getList("collection_layout.json");
        int idx = 1;
        for (com.fasterxml.jackson.databind.JsonNode layout : layouts) {
            if (layout.has("is_default") && layout.get("is_default").asInt() == 1) {
                colBuilder.addPageList(com.emu.tqqserver.proto.pkg_proto.CollectionPage.newBuilder()
                    .setUid((int)me.getUserId())
                    .setIdx(idx++)
                    .setTitle(layout.get("name").asText())
                    .setLayoutId(layout.get("id").asInt())
                    .setThemeId(1)
                    .setPageSort(idx - 1)
                    .build());
            }
        }
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, colBuilder.build().toByteArray());
    }
    @Route("/collection/otherinfo")
    public void otherInfo(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("collection/otherinfo"); sendNocontent(ctx, req); }
    @Route("/collection/setting")
    public void setting(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("collection/setting"); sendNocontent(ctx, req); }
    @Route("/collection/pagesetting")
    public void pageSetting(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("collection/pagesetting"); sendNocontent(ctx, req); }
    @Route("/collection/pagetransition")
    public void pageTransition(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("collection/pagetransition"); sendNocontent(ctx, req); }
    @Route("/collection/releasepage")
    public void releasePage(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("collection/releasepage"); sendNocontent(ctx, req); }
}
