package com.emu.tqqserver.handler;

import com.emu.tqqserver.model.entity.UserEntity;
import com.emu.tqqserver.network.websocket.GameSession;
import com.emu.tqqserver.service.UserService;
import com.emu.tqqserver.annotation.OpCode;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Handles AUTH_GUEST_LOGIN - Anonymous guest login using device ID.
 */
@OpCode(2)
public class AuthLoginHandler extends BaseHandler {

    private final UserService userService = new UserService();

    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        log.info("AUTH_LOGIN request seq={} bodyLen={}", sequenceNo, body.length);

        // Extract token directly from body
        String token = new String(body, StandardCharsets.UTF_8).trim();

        Long userId = com.emu.tqqserver.service.SessionService.getUserId(token);
        if (userId == null) {
            log.warn("AUTH_LOGIN failed: invalid token {}", token);
            session.getChannel().close();
            return;
        }

        UserEntity user = userService.findById(userId);
        if (user == null) {
            log.warn("AUTH_LOGIN failed: user not found {}", userId);
            session.getChannel().close();
            return;
        }

        session.setUserId(user.getUserId());
        session.setSessionToken(token);
        session.setAuthenticated(true);

        session.sendPacket(CommandIds.AUTH_LOGIN, sequenceNo, new byte[0]);

        log.info("AUTH_LOGIN success userId={} new={}", user.getUserId(), user.isNewUser());
    }
}
