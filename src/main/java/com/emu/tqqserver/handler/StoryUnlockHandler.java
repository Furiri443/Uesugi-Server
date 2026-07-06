package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/** ストーリー解放 */
public class StoryUnlockHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.STORY_UNLOCK, sequenceNo)) return;
        sendEmptyOk(session, CommandIds.STORY_UNLOCK, sequenceNo);
    }
}
