package com.emu.tqqserver.game.challenge;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChallengeRoutes extends BaseRoute {
    private static final Logger log = LoggerFactory.getLogger(ChallengeRoutes.class);

    @Route("/challenge/reward")
    public void reward(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("challenge/reward"); sendNocontent(ctx, req); }

    @Route("/challenge/newrank")
    public void newRank(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("challenge/newrank"); sendNocontent(ctx, req); }

    @Route("/challenge/clearbeginner")
    public void clearBeginner(ChannelHandlerContext ctx, FullHttpRequest req) { 
        log.info("challenge/clearbeginner"); 
        com.emu.tqqserver.game.user.UserEntity me = requireUser(req);
        if (me == null) {
            sendNocontent(ctx, req);
            return;
        }
        com.emu.tqqserver.proto.pkg_proto.ChallengeReceiveResult.Builder builder = com.emu.tqqserver.proto.pkg_proto.ChallengeReceiveResult.newBuilder();
        com.emu.tqqserver.game.user.UserService userService = new com.emu.tqqserver.game.user.UserService();
        me = userService.findById(me.getUserId());
        com.emu.tqqserver.proto.pkg_proto.StoredData.Builder storedDataBuilder = new com.emu.tqqserver.game.user.StoredDataService().build(me).toBuilder();
        
        // Extract category_title from raw POST body (e.g. "category_title=ShowShop")
        String categoryTitle = "";
        try {
            byte[] body = new byte[req.content().readableBytes()];
            req.content().getBytes(req.content().readerIndex(), body);
            String rawStr = new String(body, io.netty.util.CharsetUtil.UTF_8);
            if (rawStr.contains("category_title=")) {
                categoryTitle = rawStr.split("category_title=")[1].split("&")[0];
            }
        } catch (Exception e) {
            log.warn("Failed to extract category_title", e);
        }

        if (!categoryTitle.isEmpty()) {
            storedDataBuilder.addClear(categoryTitle);
        }
        builder.setStoredData(storedDataBuilder.build());
        
        builder.setReceiveCnt(1);
        builder.setRequestCnt(1);
        com.emu.tqqserver.proto.pkg_proto.Goods goods = com.emu.tqqserver.proto.pkg_proto.Goods.newBuilder()
            .setCategory(1)
            .setTargetId(1)
            .setQuantity(100)
            .build();
        builder.addGoods(goods);
        
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, builder.build().toByteArray());
    }

    @Route("/challenge/forcecleargroup")
    public void forceClearGroup(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("challenge/forcecleargroup"); sendNocontent(ctx, req); }
}
