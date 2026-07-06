package com.emu.tqqserver.game.chat;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_prealtime.ChatMessage;
import com.emu.tqqserver.proto.pkg_prealtime.ChatPublish;
import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.game.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatRoutes extends BaseRoute {
    private static final Logger log = LoggerFactory.getLogger(ChatRoutes.class);
    @Route("/chat/friends")
    public void friends(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("chat/friends"); sendNocontent(ctx, req); }
    @Route("/chat/groups")
    public void groups(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("chat/groups"); sendNocontent(ctx, req); }
    @Route("/chat/messages")
    public void messages(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("chat/messages");
        UserEntity user = requireUser(req);

        com.emu.tqqserver.proto.pkg_proto.ChatMessageList response = 
            com.emu.tqqserver.proto.pkg_proto.ChatMessageList.newBuilder()
                .setStoredData(new com.emu.tqqserver.game.user.StoredDataService().build(user))
                .build();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }
    @Route("/chat/group/create")
    public void groupCreate(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("chat/group/create"); sendNocontent(ctx, req); }
    @Route("/chat/group/disband")
    public void groupDisband(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("chat/group/disband"); sendNocontent(ctx, req); }
    @Route("/chat/group/join")
    public void groupJoin(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("chat/group/join"); sendNocontent(ctx, req); }
    @Route("/chat/group/leave")
    public void groupLeave(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("chat/group/leave"); sendNocontent(ctx, req); }
    @Route("/chat/group/invite")
    public void groupInvite(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("chat/group/invite"); sendNocontent(ctx, req); }
    @Route("/chat/group/invite/friends")
    public void groupInviteFriends(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("chat/group/invite/friends"); sendNocontent(ctx, req); }
    @Route("/chat/group/reject")
    public void groupReject(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("chat/group/reject"); sendNocontent(ctx, req); }
    @Route("/chat/group/kick")
    public void groupKick(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("chat/group/kick"); sendNocontent(ctx, req); }
    @Route("/chat/group/members")
    public void groupMembers(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("chat/group/members"); sendNocontent(ctx, req); }
    @Route("/chat/message")
    public void message(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("chat/message");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        try {
            byte[] body = getBody(req);
            String text = null;

            // Try to parse as ChatMessage first
            try {
                ChatMessage msg = ChatMessage.parseFrom(body);
                if (msg.getMessage() != null && !msg.getMessage().isEmpty()) {
                    text = msg.getMessage();
                }
            } catch (Exception ignored) {}

            // Try ChatPublish if ChatMessage didn't yield text
            if (text == null) {
                try {
                    ChatPublish pub = ChatPublish.parseFrom(body);
                    if (pub.hasBody() && pub.getBody().getMessage() != null) {
                        text = pub.getBody().getMessage();
                    }
                } catch (Exception ignored) {}
            }
            
            if (text != null && text.startsWith("/")) {
                handleCommand(userId, text);
            }
        } catch (Exception e) {
            log.error("Failed to parse chat message", e);
        }

        // Return empty Proto.Nocontent to satisfy the client
        sendNocontent(ctx, req);
    }

    private void handleCommand(long userId, String commandLine) {
        log.info("Player {} executed command: {}", userId, commandLine);
        String[] parts = commandLine.trim().split("\\s+");
        String cmd = parts[0].toLowerCase();
        
        try {
            switch (cmd) {
                case "/add_jewel":
                    if (parts.length >= 2) {
                        int amount = Integer.parseInt(parts[1]);
                        userService.addJewel(userId, amount);
                        log.info("Added {} jewels to user {}", amount, userId);
                    }
                    break;
                case "/add_coin":
                    if (parts.length >= 2) {
                        int amount = Integer.parseInt(parts[1]);
                        userService.addCoin(userId, amount);
                        log.info("Added {} coins to user {}", amount, userId);
                    }
                    break;
                case "/add_item":
                    if (parts.length >= 3) {
                        int itemId = Integer.parseInt(parts[1]);
                        int amount = Integer.parseInt(parts[2]);
                        userService.addItem(userId, itemId, amount);
                        log.info("Added {} items (id: {}) to user {}", amount, itemId, userId);
                    }
                    break;

                default:
                    log.warn("Unknown command: {}", cmd);
                    break;
            }
        } catch (Exception e) {
            log.error("Error executing command {}", commandLine, e);
        }
    }
}
