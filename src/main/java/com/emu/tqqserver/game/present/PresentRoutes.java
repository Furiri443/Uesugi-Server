package com.emu.tqqserver.game.present;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.PresentList;
import com.emu.tqqserver.proto.pkg_proto.PresentResult;
import com.emu.tqqserver.proto.pkg_proto.Goods;
import com.emu.tqqserver.proto.pkg_pmisc.Present;
import com.emu.tqqserver.game.present.PresentService;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;

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
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
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
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        UserEntity user = userService.findById(userId);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        List<Integer> ids = new ArrayList<>();
        try {
            String bodyString = getBodyString(req);
            if (bodyString != null && !bodyString.isEmpty()) {
                for (String param : bodyString.split("&")) {
                    String[] pair = param.split("=");
                    if (pair.length == 2) {
                        String key = pair[0];
                        String value = pair[1];
                        if (key.equals("pids") || key.equals("pids[]")) {
                            // Can be comma separated too, let's split just in case
                            for (String v : value.split(",")) {
                                ids.add(Integer.parseInt(v));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to parse present receive pids", e);
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
