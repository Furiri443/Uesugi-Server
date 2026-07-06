package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** ガチャ実行 - execute gacha draw (1x or 10x) */
public class GachaExecHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.GACHA_EXEC, sequenceNo)) return;
        log.info("GACHA_EXEC userId={}", session.getUserId());
        // TODO: implement gacha algorithm and return drawn cards
        sendEmptyOk(session, CommandIds.GACHA_EXEC, sequenceNo);
    }
}
