package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;

/**
 * AssetBundle カタログ取得
 *
 * <p>Returns a list of AssetBundles the client needs to download.
 * The game stores bundles under assets/android/AssetBundle/ in the APK.
 * For the private server, we serve these directly via HTTP.
 *
 * Observed bundle names from APK:
 * - commonresources
 * - 60000064 (likely character/card pack)
 */
public class AssetCatalogHandler extends BaseHandler {
    @Override
    public void handle(GameSession session, int sequenceNo, byte[] body) {
        if (!requireAuth(session, CommandIds.ASSET_CATALOG, sequenceNo)) return;
        log.info("ASSET_CATALOG userId={}", session.getUserId());
        // TODO: return CDN catalog pointing to local HTTP server
        sendEmptyOk(session, CommandIds.ASSET_CATALOG, sequenceNo);
    }
}
