package com.emu.tqqserver.game.chat.handler;

import com.emu.tqqserver.proto.pkg_prealtime.ChatMessage;
import com.emu.tqqserver.proto.pkg_prealtime.ChatPublish;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.game.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route("/chat/message")
public class ChatMessageHandler extends BaseRoute {

    private static final Logger log = LoggerFactory.getLogger(ChatMessageHandler.class);
    private final UserService userService = new UserService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

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
