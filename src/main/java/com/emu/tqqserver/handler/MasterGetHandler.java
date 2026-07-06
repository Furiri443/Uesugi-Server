package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/**
 * マスターデータ取得
 *
 * <p>Master data contains all static game data: characters, cards, stages, items, etc.
 * The client downloads master data on first launch and checks for updates.
 * Data is likely stored as protobuf-net or ZeroFormatter binary blobs.
 *
 * TODO: Load master data from extracted AssetBundles in assets/android/AssetBundle/
 */
public class MasterGetHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.MASTER_GET, sequenceNo)) return;
        log.info("MASTER_GET userId={}", session.getUserId());
        // TODO: return master data version and CDN URL for download
        sendEmptyOk(session, CommandIds.MASTER_GET, sequenceNo);
    }
}
