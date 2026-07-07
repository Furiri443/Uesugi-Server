package com.emu.tqqserver.handler;

import com.emu.tqqserver.annotation.OpCode;
import com.emu.tqqserver.network.websocket.GameSession;
import com.emu.tqqserver.proto.pkg_prealtime.StatNumSub;

@OpCode(5)
public class ChatChannelStatHandler extends BaseHandler {

    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) throws Exception {
        log.info("Handling ChatChannelStat/Subscribe (opCode 5) seq={}", sequenceNo);
        
        // The client sends a Channel message containing the channels it wants to subscribe to
        com.emu.tqqserver.proto.pkg_prealtime.Channel req = 
            com.emu.tqqserver.proto.pkg_prealtime.Channel.parseFrom(body);
            
        StatNumSub.Builder builder = StatNumSub.newBuilder();
        com.emu.tqqserver.game.chat.ChatService chatService = com.emu.tqqserver.game.chat.ChatService.getInstance();
        
        if (req.getChannelsList() != null) {
            for (String ch : req.getChannelsList()) {
                chatService.subscribe(ch, session);
                builder.putNums(ch, chatService.getSubscriberCount(ch));
            }
        }
        
        StatNumSub response = builder.build();
        session.sendPacket(5, sequenceNo, response.toByteArray());
    }
}
