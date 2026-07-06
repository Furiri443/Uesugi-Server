package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;
import com.emu.tqqserver.proto.pkg_prealtime.ChatPublish;
import com.emu.tqqserver.game.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.emu.tqqserver.annotation.OpCode;
import com.emu.tqqserver.command.CommandManager;

@OpCode(11)
public class ChatPublishHandler implements CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(ChatPublishHandler.class);
    private final UserService userService = new UserService();

    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) throws Exception {
        try {
            ChatPublish req = ChatPublish.parseFrom(body);
            String text = null;
            if (req.hasBody() && req.getBody().getMessage() != null) {
                text = req.getBody().getMessage();
            }

            if (text != null && text.startsWith("/")) {
                handleCommand(session.getUserId(), text);
            } else {
                log.info("Chat message from {}: {}", session.getUserId(), text);
            }
        } catch (Exception e) {
            log.error("Failed to parse ChatPublish", e);
        }

        // Send Empty response to acknowledge the publish, preventing client freeze
        session.sendPacket(11, sequenceNo, new byte[0]);
    }

    private void handleCommand(long userId, String commandLine) {
        log.info("Player {} executed GM command: {}", userId, commandLine);
        com.emu.tqqserver.command.CommandManager.getInstance().executeCommand(userId, commandLine);
    }
}
