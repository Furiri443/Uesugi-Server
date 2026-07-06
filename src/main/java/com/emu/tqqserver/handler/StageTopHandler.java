package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** ステージ一覧 */
public class StageTopHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.STAGE_TOP, sequenceNo)) return;
        sendEmptyOk(session, CommandIds.STAGE_TOP, sequenceNo);
    }
}
