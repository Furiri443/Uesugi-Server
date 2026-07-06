package com.emu.tqqserver.network.http;

import com.emu.tqqserver.annotation.Route;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.PresentList;
import com.emu.tqqserver.proto.pkg_proto.PresentResult;
import com.emu.tqqserver.proto.pkg_proto.Goods;
import com.emu.tqqserver.proto.pkg_pmisc.Present;
import com.emu.tqqserver.service.PresentService;
import com.emu.tqqserver.service.StoredDataService;
import com.emu.tqqserver.model.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class PresentRoutes extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final PresentService presentService = new PresentService();

    /** POST /present/list */
    @Route("/present/list")
    public void list(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("present/list");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.service.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        UserEntity user = userService.findById(userId);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        List<Present> presents = presentService.getActivePresents(userId);
        if (presents.isEmpty()) {
            presentService.addPresent(userId, 2, 0, 5000, "ごとぱずサーバーへようこそ！"); // 5000 jewels welcome gift
            presentService.addPresent(userId, 1, 0, 100000, "開発者からのプレゼント"); // 100k coins
            presents = presentService.getActivePresents(userId);
        }

        PresentList response = PresentList.newBuilder()
            .setStoredData(storedDataService.build(user))
            .addAllList(presents)
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }

    /** POST /present/receive */
    @Route("/present/receive")
    public void receive(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("present/receive");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.service.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        UserEntity user = userService.findById(userId);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        List<Long> longIds = readInt64ListField(req, 1);
        List<Integer> ids = new ArrayList<>();
        for (long id : longIds) {
            ids.add((int) id);
        }

        List<Goods> goods = presentService.claimPresents(userId, ids);
        List<Present> remaining = presentService.getActivePresents(userId);

        PresentResult response = PresentResult.newBuilder()
            .setStoredData(storedDataService.build(user))
            .setRequestCnt(ids.size())
            .setReceiveCnt(goods.size())
            .addAllGoods(goods)
            .addAllList(remaining)
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }
}
