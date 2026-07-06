package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** ストーリー一覧取得 */
public class StoryGetHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.STORY_GET, sequenceNo)) return;
        sendEmptyOk(session, CommandIds.STORY_GET, sequenceNo);
    }
}
