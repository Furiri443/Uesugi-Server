package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** ステージ開始 */
public class StageStartHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.STAGE_START, sequenceNo)) return;
        log.info("STAGE_START userId={}", session.getUserId());
        sendEmptyOk(session, CommandIds.STAGE_START, sequenceNo);
    }
}
