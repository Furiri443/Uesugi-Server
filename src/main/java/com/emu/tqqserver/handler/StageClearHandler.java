package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** ステージクリア - stage clear result, rewards */
public class StageClearHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.STAGE_CLEAR, sequenceNo)) return;
        log.info("STAGE_CLEAR userId={}", session.getUserId());
        // TODO: award experience, items, unlock next stage
        sendEmptyOk(session, CommandIds.STAGE_CLEAR, sequenceNo);
    }
}
