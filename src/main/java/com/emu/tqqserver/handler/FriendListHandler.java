package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** フレンド一覧 */
public class FriendListHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.FRIEND_LIST, sequenceNo)) return;
        sendEmptyOk(session, CommandIds.FRIEND_LIST, sequenceNo);
    }
}
