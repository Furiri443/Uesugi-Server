package com.emu.tqqserver.game.chapter;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class ChapterRoutes extends BaseRoute {
    @Route("/chapter/release")
    public void release(ChannelHandlerContext ctx, FullHttpRequest req) { 
        log.info("chapter/release"); 
        try {
            UserEntity me = requireUser(req);
            if (me == null) {
                sendNocontent(ctx, req);
                return;
            }
            byte[] body = getBody(req);
            String bodyStr = new String(body, java.nio.charset.StandardCharsets.UTF_8);
            io.netty.handler.codec.http.QueryStringDecoder decoder = new io.netty.handler.codec.http.QueryStringDecoder("?" + bodyStr);
            
            java.util.List<String> groupIdStr = decoder.parameters().get("chapter_group_id");
            java.util.List<String> itemIdStr = decoder.parameters().get("item_id");
            java.util.List<String> qtyStr = decoder.parameters().get("quantity");
            
            if (itemIdStr != null && !itemIdStr.isEmpty() && groupIdStr != null && !groupIdStr.isEmpty()) {
                int itemId = Integer.parseInt(itemIdStr.get(0));
                int groupId = Integer.parseInt(groupIdStr.get(0));
                int quantity = 1;
                if (qtyStr != null && !qtyStr.isEmpty()) {
                    quantity = Integer.parseInt(qtyStr.get(0));
                }
                
                com.emu.tqqserver.game.user.UserService userService = new com.emu.tqqserver.game.user.UserService();
                boolean success = userService.deductItem(me.getUserId(), itemId, quantity);
                if (success) {
                    log.info("User {} released chapter group {} with item {} x{}", me.getUserId(), groupId, itemId, quantity);
                    
                    // Unlock all chapters with this groupId
                    java.util.Collection<com.emu.tqqserver.data.resources.ChapterDef> chapters = com.emu.tqqserver.data.GameData.getChapterDataTable().values();
                    for (com.emu.tqqserver.data.resources.ChapterDef chapter : chapters) {
                        if (chapter.getGroupId() == groupId) {
                            userService.unlockChapter(me.getUserId(), chapter.getId());
                        }
                    }
                    
                    // Add expiration for the group (e.g. 1 year from now)
                    long expiresAt = (System.currentTimeMillis() / 1000) + 31536000L;
                    userService.unlockChapterGroup(me.getUserId(), groupId, expiresAt);
                } else {
                    log.warn("User {} failed to release chapter group {} due to insufficient item {}", me.getUserId(), groupId, itemId);
                }
            }
        } catch (Exception e) {
            log.error("Error in chapter/release", e);
        }
        sendNocontent(ctx, req); 
    }
}
