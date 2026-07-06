package com.emu.tqqserver.game.cooking;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.CookingTrays;
import com.emu.tqqserver.proto.pkg_proto.CookingStart;
import com.emu.tqqserver.proto.pkg_proto.CookingClear;
import com.emu.tqqserver.proto.pkg_proto.CookingShorten;
import com.emu.tqqserver.game.cooking.CookingService;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;

import java.util.List;

public class CookingRoutes extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final CookingService cookingService = new CookingService();

    /** POST /cooking/trays */
    @Route("/cooking/trays")
    public void trays(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("cooking/trays");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        long targetUid = userId;
        io.netty.handler.codec.http.QueryStringDecoder decoder = new io.netty.handler.codec.http.QueryStringDecoder(req.uri());
        List<String> targetUidParam = decoder.parameters().get("target_uid");
        if (targetUidParam != null && !targetUidParam.isEmpty()) {
            try {
                targetUid = Long.parseLong(targetUidParam.get(0));
            } catch (NumberFormatException ignored) {}
        } else {
            int bodyTarget = readIntField(req, 1);
            if (bodyTarget > 0) {
                targetUid = bodyTarget;
            }
        }

        UserEntity user = userService.findById(targetUid);
            if (user == null) throw new RuntimeException("User not found: " + targetUid);

        com.emu.tqqserver.proto.pkg_proto.CookingTrays response = CookingTrays.newBuilder()
            .setStoredData(storedDataService.build(user))
            .addAllTrays(cookingService.getTrays(targetUid))
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }

    /** POST /cooking/start */
    @Route("/cooking/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("cooking/start");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        UserEntity user = userService.findById(userId);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        int[] params = readTwoIntFields(req);
        int trayId = params[0] > 0 ? params[0] : 1;
        int recipeId = params[1] > 0 ? params[1] : 10001;

        // Cook for 60 seconds default
        cookingService.startCooking(userId, trayId, recipeId, 60);

        CookingStart response = CookingStart.newBuilder()
            .setStoredData(storedDataService.build(user))
            .setForecastRank(3) // forecast high quality
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }

    /** POST /cooking/clear */
    @Route("/cooking/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("cooking/clear");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        UserEntity user = userService.findById(userId);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        int trayId = readIntField(req, 1);
        cookingService.clearCooking(userId, trayId);

        CookingClear response = CookingClear.newBuilder()
            .setStoredData(storedDataService.build(user))
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }

    /** POST /cooking/shorten */
    @Route("/cooking/shorten")
    public void shorten(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("cooking/shorten");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        UserEntity user = userService.findById(userId);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        int trayId = readIntField(req, 1);
        // Instant complete by setting remaining time to 0
        cookingService.startCooking(userId, trayId, 10001, 0);

        CookingShorten response = CookingShorten.newBuilder()
            .setStoredData(storedDataService.build(user))
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }

    /** POST /cooking/shorten_ad */
    @Route("/cooking/shorten_ad")
    public void shortenAd(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("cooking/shorten_ad");
        shorten(ctx, req);
    }

    /** POST /cooking/helpers */
    @Route("/cooking/helpers")
    public void helpers(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("cooking/helpers");
        sendNocontent(ctx, req);
    }

    /** POST /cooking/help */
    @Route("/cooking/help")
    public void help(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("cooking/help");
        sendNocontent(ctx, req);
    }

    /** POST /cooking/help/targets */
    @Route("/cooking/help/targets")
    public void helpTargets(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("cooking/help/targets");
        sendNocontent(ctx, req);
    }

    /** POST /cooking/rankreward/receive */
    @Route("/cooking/rankreward/receive")
    public void rankRewardReceive(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("cooking/rankreward/receive");
        sendNocontent(ctx, req);
    }
}
