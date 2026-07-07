package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

/**
 * Dispatches incoming binary packets to the appropriate command handler.
 *
 * <p>Each incoming packet is parsed as:
 * <pre>
 *   int32 command_id  (little-endian)
 *   int32 sequence_no (little-endian)
 *   byte[] body       (protobuf-net encoded message)
 * </pre>
 *
 * <p>Command IDs are mapped to specific {@link CommandHandler} implementations.
 * Unknown command IDs are logged for reverse engineering purposes.
 *
 * TODO: Populate command ID table from IDA analysis of proto.dll / proto_serializer.dll
 */
public class PacketDispatcher {

    private static final Logger log = LoggerFactory.getLogger(PacketDispatcher.class);

    private static final int HEADER_SIZE = 5; // 4 (sequenceNo) + 1 (opCode)

    private final Map<Integer, CommandHandler> handlers = new HashMap<>();

    public PacketDispatcher() {
        registerHandlers();
    }

    private void registerHandlers() {
        Class<?>[] handlerClasses = {
            AuthLoginHandler.class,
            ChatPublishHandler.class,
            ChatChannelStatHandler.class,
            ChatChannelListHandler.class,
            ChatHistoryHandler.class
        };

        for (Class<?> clazz : handlerClasses) {
            com.emu.tqqserver.annotation.OpCode opCodeAnnotation = clazz.getAnnotation(com.emu.tqqserver.annotation.OpCode.class);
            if (opCodeAnnotation != null) {
                try {
                    CommandHandler handlerInstance = (CommandHandler) clazz.getDeclaredConstructor().newInstance();
                    register(opCodeAnnotation.value(), handlerInstance);
                } catch (Exception e) {
                    log.error("Failed to register handler: {}", clazz.getSimpleName(), e);
                }
            }
        }
    }

    private void register(int commandId, CommandHandler handler) {
        handlers.put(commandId, handler);
    }

    public void dispatch(GameSession session, byte[] data) {
        if (data.length < HEADER_SIZE) {
            log.warn("Packet too short ({} bytes) from userId={}", data.length, session.getUserId());
            return;
        }

        ByteBuffer buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        int sequenceNo = buf.getInt();
        int opCode = buf.get() & 0xFF;

        byte[] body = new byte[data.length - 5];
        buf.get(body);

        log.debug("Received opCode={} seqNo={} bodyLen={} userId={}",
                opCode, sequenceNo, body.length, session.getUserId());

        CommandHandler handler = handlers.get(opCode);
        if (handler != null) {
            try {
                handler.handle(session, sequenceNo, body);
            } catch (Exception e) {
                log.error("Handler error for opCode={}: {}", opCode, e.getMessage(), e);
                // Send error reply
                session.sendPacket(opCode, sequenceNo, new byte[0]);
            }
        } else {
            log.warn("Unknown opCode={} seqNo={} bodyLen={} - STUB NEEDED",
                    opCode, sequenceNo, body.length);
            // Echo back empty reply so client doesn't hang
            session.sendPacket(opCode, sequenceNo, new byte[0]);
        }
    }
}
