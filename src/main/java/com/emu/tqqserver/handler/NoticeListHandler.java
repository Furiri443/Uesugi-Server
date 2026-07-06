package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** お知らせ一覧 */
public class NoticeListHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.NOTICE_LIST, sequenceNo)) return;
        sendEmptyOk(session, CommandIds.NOTICE_LIST, sequenceNo);
    }
}
