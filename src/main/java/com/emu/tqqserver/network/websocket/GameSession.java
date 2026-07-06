package com.emu.tqqserver.network.websocket;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Represents an active game client session over WebSocket.
 */
public class GameSession {

    private final Channel channel;
    private long userId = -1;
    private String sessionToken;
    private boolean authenticated = false;

    public GameSession(Channel channel) {
        this.channel = channel;
    }

    /** Send raw bytes as a binary WebSocket frame */
    public void send(byte[] data) {
        if (channel.isActive()) {
            channel.writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(data)));
        }
    }

    /**
     * Build and send a response packet.
     *
     * @param commandId the response command ID
     * @param sequenceNo the echoed request sequence number
     * @param body      serialized protobuf body (may be empty)
     */
    public void sendPacket(int commandId, int sequenceNo, byte[] body) {
        sendPacket(1, sequenceNo, 0, body);
    }

    /**
     * Sends a Reply (MessageType = 1) to the client.
     */
    public void sendPacket(int messageType, int messageId, int errorCode, byte[] body) {
        // MessageType = 1 (Reply)
        // messageId = 4 bytes
        // errorCode = 4 bytes
        ByteBuffer buf = ByteBuffer.allocate(9 + body.length).order(ByteOrder.LITTLE_ENDIAN);
        buf.put((byte) messageType);
        buf.putInt(messageId);
        buf.putInt(errorCode);
        buf.put(body);
        send(buf.array());
    }

    /**
     * Sends a Message/Push (MessageType = 0) to the client.
     */
    public void sendPush(int opCode, byte[] body) {
        // MessageType = 0 (Message)
        // opCode = 1 byte (or 4 bytes? Let's use 1 byte to match request)
        ByteBuffer buf = ByteBuffer.allocate(2 + body.length).order(ByteOrder.LITTLE_ENDIAN);
        buf.put((byte) 0);
        buf.put((byte) opCode);
        buf.put(body);
        send(buf.array());
    }

    public long getUserId()           { return userId; }
    public void setUserId(long id)    { this.userId = id; }
    public String getSessionToken()   { return sessionToken; }
    public void setSessionToken(String t) { this.sessionToken = t; }
    public boolean isAuthenticated()  { return authenticated; }
    public void setAuthenticated(boolean v) { this.authenticated = v; }
    public Channel getChannel()       { return channel; }
}
