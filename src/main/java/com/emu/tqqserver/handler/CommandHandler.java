package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/**
 * Interface for all WebSocket command handlers.
 */
public interface CommandHandler {
    /**
     * Handle an incoming game packet.
     *
     * @param session    the client session
     * @param sequenceNo the client-supplied sequence number to echo in the response
     * @param body       raw protobuf-net serialized request body
     * @throws Exception on any processing error
     */
    void handle(GameSession session, int sequenceNo, byte[] body) throws Exception;
}
