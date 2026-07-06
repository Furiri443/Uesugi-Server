package com.emu.tqqserver.game.notice;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.proto.pkg_proto.NoticeAll;
import com.emu.tqqserver.game.notice.NoticeService;
import com.emu.tqqserver.game.user.StoredDataService;

public class NoticeRoutes extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();

    /** POST /notifications */
    @Route("/notifications")
    public void list(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("notifications");

        UserEntity user = requireUser(req);

        NoticeAll response = NoticeAll.newBuilder()
            .setStoredData(storedDataService.build(user))
            .addAllNewsList(NoticeService.getInstance().getNewsList())
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }
}
