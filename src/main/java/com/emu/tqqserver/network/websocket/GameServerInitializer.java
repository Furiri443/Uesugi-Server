package com.emu.tqqserver.network.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import com.emu.tqqserver.network.http.HttpApiHandler;
import com.emu.tqqserver.server.ServerConfig;

/**
 * Single-port pipeline — API + CDN + WebSocket trên cùng một port.
 *
 * <p>Routing theo URI:
 * <ul>
 *   <li>{@code GET  /assets-cancer/Resources/{platform}/{hash}} → CDN (serve file từ assets_cdn/)
 *   <li>{@code POST /resource/list/{Android|Ios}}              → API trả proto resource list
 *   <li>{@code POST /account/*, /user/*, /puzzle/*, …}         → API handlers
 *   <li>{@code GET  /ws} (Upgrade: websocket)                  → GameWebSocketHandler
 * </ul>
 *
 * <p>Pipeline order:
 * <pre>
 *   HttpServerCodec
 *   HttpObjectAggregator(8MB)   ← đủ cho API request body (proto nhỏ)
 *   ChunkedWriteHandler         ← streaming file lớn (CDN bundles)
 *   WebSocketServerCompressionHandler
 *   WebSocketServerProtocolHandler (/ws)
 *   HttpApiHandler              ← xử lý tất cả HTTP + CDN GET
 *   GameWebSocketHandler        ← xử lý WS frames sau upgrade
 * </pre>
 *
 * <p>Lưu ý về {@code HttpObjectAggregator}: aggregate toàn bộ request body vào memory.
 * API request body (protobuf) thường < 4KB. CDN request chỉ là GET không có body.
 * 8MB là an toàn cho mọi trường hợp.
 */
public class GameServerInitializer extends ChannelInitializer<SocketChannel> {

    /** WebSocket upgrade path */
    public static final String WS_PATH = "/aaaaaa";

    /** Max aggregated HTTP request size — 8MB đủ cho mọi API proto body */
    private static final int MAX_HTTP_CONTENT_LENGTH = 8 * 1024 * 1024;

    private final ServerConfig config;
    private final HttpApiHandler apiHandler = new HttpApiHandler();

    public GameServerInitializer(ServerConfig config) {
        this.config = config;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();

        // ── HTTP codec ────────────────────────────────────────────────────────
        p.addLast("httpCodec",      new HttpServerCodec());
        p.addLast("httpAggregator", new HttpObjectAggregator(MAX_HTTP_CONTENT_LENGTH));

        // ── Chunked streaming (dùng cho CDN file serving) ─────────────────────
        p.addLast("chunkedWriter",  new ChunkedWriteHandler());

        // ── WebSocket upgrade support ─────────────────────────────────────────
        p.addLast("wsCompressor",   new WebSocketServerCompressionHandler());
        p.addLast("wsProtocol",     new WebSocketServerProtocolHandler(
            WS_PATH,
            null,   // subprotocols
            true,   // allow extensions
            65536   // max WS frame size
        ));

        // ── Business handlers (shared instance — @Sharable) ───────────────────
        // HttpApiHandler xử lý: API POST, CDN GET, health check
        p.addLast("apiHandler",     apiHandler);
        // GameWebSocketHandler xử lý WS binary frames sau upgrade
        p.addLast("wsHandler",      new GameWebSocketHandler());
    }
}
