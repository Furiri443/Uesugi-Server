package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

public class AuthLogoutHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        log.info("LOGOUT userId={}", session.getUserId());
        session.setAuthenticated(false);
        session.setUserId(-1);
        sendEmptyOk(session, CommandIds.AUTH_LOGOUT, sequenceNo);
    }
}
