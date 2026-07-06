package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** ユーザープロフィール取得 */
public class UserGetProfileHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.USER_GET_PROFILE, sequenceNo)) return;
        log.debug("USER_PROFILE userId={}", session.getUserId());
        // TODO: fetch user from DB and serialize
        sendEmptyOk(session, CommandIds.USER_GET_PROFILE, sequenceNo);
    }
}
