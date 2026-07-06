package com.emu.tqqserver.network.http;

import com.emu.tqqserver.annotation.Route;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.model.entity.UserEntity;
import com.emu.tqqserver.proto.pkg_proto.Master;
import com.emu.tqqserver.service.MasterDataService;
import com.emu.tqqserver.service.StoredDataService;

/**
 * POST /master/all
 *
 * Returns all static master data (cards, stages, items, etc.) in a single response.
 * Client checks its cached version against X-Enish-App-Version-Master header value.
 * If server version is newer, full master data is returned.
 *
 * Response: Master { all: All { ... hundreds of master data lists ... } }
 */
public class MasterRoutes extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    @Route("/master/all")
    public void all(ChannelHandlerContext ctx, FullHttpRequest req) {
        String clientVersion = getMasterVersion(req);
        log.info("master/all clientVersion={}", clientVersion);

        UserEntity user = requireUser(req);

        byte[] bytes = MasterDataService.getInstance().getAllBytes();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, bytes);
    }
}
