package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** ホーム画面データ取得 - TODO: return stamina, banners, event info */
public class HomeGetHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.HOME_GET, sequenceNo)) return;
        log.debug("HOME_GET userId={}", session.getUserId());
        // TODO: build proper home response with stamina, login bonus, banners
        sendEmptyOk(session, CommandIds.HOME_GET, sequenceNo);
    }
}
