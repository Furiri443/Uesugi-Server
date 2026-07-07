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

            if (text != null && req.getChannelsList() != null) {
                for (String channel : req.getChannelsList()) {
                    // Create chat message
                    int msgType = req.hasBody() ? req.getBody().getType() : 1;
                    int msgTag = req.hasBody() ? req.getBody().getTag() : 0;

                    com.emu.tqqserver.proto.pkg_prealtime.ChatMessage msg = com.emu.tqqserver.proto.pkg_prealtime.ChatMessage.newBuilder()
                            .setId(System.currentTimeMillis())
                            .setChannel(channel)
                            .setUid((int) session.getUserId())
                            .setType(msgType)
                            .setTag(msgTag)
                            .setStamp(0)
                            .setMessage(text)
                            .setPostedAt((int) (System.currentTimeMillis() / 1000))
                            .build();
                    com.emu.tqqserver.game.chat.ChatService.getInstance().addMessage(channel, msg);
                    // Push the user's message back to them
                    session.sendPush(12, msg.toByteArray());

                    if (text.startsWith("/")) {
                        String result = CommandManager.getInstance().executeCommand(session.getUserId(), text);
                        // Send bot reply
                        com.emu.tqqserver.proto.pkg_prealtime.ChatMessage botReply = com.emu.tqqserver.proto.pkg_prealtime.ChatMessage.newBuilder()
                                .setId(System.currentTimeMillis() + 1)
                                .setChannel(channel)
                                .setUid(999) // GM Bot ID
                                .setType(msgType)
                                .setTag(msgTag)
                                .setStamp(0)
                                .setMessage(result)
                                .setPostedAt((int) (System.currentTimeMillis() / 1000))
                                .build();
                        com.emu.tqqserver.game.chat.ChatService.getInstance().addMessage(channel, botReply);
                        // Push bot reply to the user
                        session.sendPush(12, botReply.toByteArray());
                    } else {
                        log.info("Chat message from {}: {}", session.getUserId(), text);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to parse ChatPublish", e);
        }

        // Send Empty response to acknowledge the publish, preventing client freeze
        session.sendPacket(11, sequenceNo, new byte[0]);
    }
}
