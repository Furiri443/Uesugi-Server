package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** アイテム購入 */
public class ShopBuyHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.SHOP_BUY, sequenceNo)) return;
        log.info("SHOP_BUY userId={}", session.getUserId());
        sendEmptyOk(session, CommandIds.SHOP_BUY, sequenceNo);
    }
}
