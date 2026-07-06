package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** ガチャトップ - gacha banners and pools */
public class GachaTopHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.GACHA_TOP, sequenceNo)) return;
        sendEmptyOk(session, CommandIds.GACHA_TOP, sequenceNo);
    }
}
