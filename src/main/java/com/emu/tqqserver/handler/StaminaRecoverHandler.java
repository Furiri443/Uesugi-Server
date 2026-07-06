package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** スタミナ回復 */
public class StaminaRecoverHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.STAMINA_RECOVER, sequenceNo)) return;
        sendEmptyOk(session, CommandIds.STAMINA_RECOVER, sequenceNo);
    }
}
