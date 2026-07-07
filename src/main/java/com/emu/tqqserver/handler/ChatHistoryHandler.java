package com.emu.tqqserver.handler;

import com.emu.tqqserver.annotation.OpCode;
import com.emu.tqqserver.network.websocket.GameSession;
import com.emu.tqqserver.proto.pkg_prealtime.Channel;
import com.emu.tqqserver.proto.pkg_prealtime.ChatMessageList;

@OpCode(7)
public class ChatHistoryHandler extends BaseHandler {

    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) throws Exception {
        log.info("Handling ChatHistory (opCode 7) seq={}", sequenceNo);
        
        com.emu.tqqserver.proto.pkg_prealtime.Channel req = com.emu.tqqserver.proto.pkg_prealtime.Channel.parseFrom(body);
        
        com.emu.tqqserver.game.chat.ChatService chatService = com.emu.tqqserver.game.chat.ChatService.getInstance();
        
        if (req.getChannelsList() != null) {
            for (String ch : req.getChannelsList()) {
                chatService.unsubscribe(ch, session);
            }
        }
        
        session.sendPacket(7, sequenceNo, com.emu.tqqserver.proto.pkg_prealtime.Empty.getDefaultInstance().toByteArray());
    }
}
