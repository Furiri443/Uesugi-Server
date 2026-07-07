package com.emu.tqqserver.handler;

import com.emu.tqqserver.annotation.OpCode;
import com.emu.tqqserver.network.websocket.GameSession;
import com.emu.tqqserver.proto.pkg_prealtime.Channel;

@OpCode(6)
public class ChatChannelListHandler extends BaseHandler {

    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) throws Exception {
        log.info("Handling ChatChannelList (opCode 6) seq={}", sequenceNo);
        
        // Return dummy channels so client array is not null
        com.emu.tqqserver.proto.pkg_prealtime.Channel req = com.emu.tqqserver.proto.pkg_prealtime.Channel.parseFrom(body);
        com.emu.tqqserver.game.chat.ChatService chatService = com.emu.tqqserver.game.chat.ChatService.getInstance();
        
        if (req.getChannelsList() != null) {
            for (String ch : req.getChannelsList()) {
                chatService.subscribe(ch, session);
            }
        }
        
        session.sendPacket(6, sequenceNo, com.emu.tqqserver.proto.pkg_prealtime.Empty.getDefaultInstance().toByteArray());
    }
}
