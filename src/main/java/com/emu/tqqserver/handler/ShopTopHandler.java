package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** ショップトップ */
public class ShopTopHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.SHOP_TOP, sequenceNo)) return;
        sendEmptyOk(session, CommandIds.SHOP_TOP, sequenceNo);
    }
}
