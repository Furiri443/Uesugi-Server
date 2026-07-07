package com.emu.tqqserver.network.websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.*;
import com.emu.tqqserver.handler.PacketDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * WebSocket handler for game protocol messages.
 *
 * <p>The game uses websocket-sharp (C# library) to maintain a persistent
 * WebSocket connection. Messages are binary frames containing protobuf-net
 * serialized data with a command ID header.
 *
 * <p>Packet format (inferred from binary analysis):
 * <pre>
 *   [4 bytes: command_id (little-endian int32)]
 *   [4 bytes: sequence_no (little-endian int32)]
 *   [N bytes: protobuf-net serialized body]
 * </pre>
 *
 * TODO: Confirm exact packet format via traffic capture / deeper IDA analysis.
 */
@ChannelHandler.Sharable
public class GameWebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger log = LoggerFactory.getLogger(GameWebSocketHandler.class);

    /** Active sessions: channelId -> GameSession */
    private static final ConcurrentMap<String, GameSession> sessions = new ConcurrentHashMap<>();

    private final PacketDispatcher dispatcher = new PacketDispatcher();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String id = ctx.channel().id().asShortText();
        sessions.put(id, new GameSession(ctx.channel()));
        log.info("Client connected: {} (total: {})", id, sessions.size());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String id = ctx.channel().id().asShortText();
        GameSession session = sessions.remove(id);
        if (session != null) {
            log.info("Client disconnected: {} userId={}", id, session.getUserId());
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof BinaryWebSocketFrame binaryFrame) {
            byte[] data = new byte[binaryFrame.content().readableBytes()];
            binaryFrame.content().readBytes(data);

            String sessionId = ctx.channel().id().asShortText();
            GameSession session = sessions.get(sessionId);

            if (session != null) {
                StringBuilder hex = new StringBuilder();
                for (byte b : data) {
                    hex.append(String.format("%02X ", b));
                }
                log.info("WS BinaryFrame [{} bytes]: {}", data.length, hex.toString());
                dispatcher.dispatch(session, data);
            }
        } else if (frame instanceof TextWebSocketFrame textFrame) {
            // Some commands may be sent as JSON text frames
            log.debug("Text frame from {}: {}", ctx.channel().id().asShortText(), textFrame.text());
        } else if (frame instanceof PingWebSocketFrame) {
            ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
        } else if (frame instanceof CloseWebSocketFrame) {
            ctx.channel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("WebSocket error for channel {}", ctx.channel().id().asShortText(), cause);
        ctx.close();
    }

    /** Send a binary packet to a specific session */
    public static void sendToSession(String sessionId, byte[] packet) {
        GameSession session = sessions.get(sessionId);
        if (session != null) {
            session.send(packet);
        }
    }

    /** Broadcast a packet to all connected sessions */
    public static void broadcast(byte[] packet) {
        sessions.values().forEach(s -> s.send(packet));
    }

    /** Find a session by userId */
    public static GameSession getSessionByUserId(long userId) {
        for (GameSession session : sessions.values()) {
            if (session.getUserId() == userId) {
                return session;
            }
        }
        return null;
    }
}
