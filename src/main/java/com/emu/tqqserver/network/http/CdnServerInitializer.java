package com.emu.tqqserver.network.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;
import com.emu.tqqserver.server.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Dedicated CDN server for asset bundle file serving.
 *
 * <p>Listens on the CDN port (default 8081) and serves files from
 * {@code assets_cdn/} directory, looked up by the 32-char hex hash
 * extracted from the request URI.
 *
 * <p><b>Supported URL patterns</b>:
 * <pre>
 *   GET /assets-cancer/Resources/android/{hash}
 *   GET /assets-cancer/Resources/ios/{hash}
 *   GET /assets-cancer/Resources/share/{hash}
 *   GET /{hash}                                     ← direct hash lookup
 * </pre>
 *
 * <p><b>CDN directory</b>: {@code /Volumes/OCungRoi/PRJ/GTPZ-PS/assets_cdn/}
 * Files: 54,054 flat files, ~9.3 GB total (v1.43.440)
 *
 * <p><b>Coverage</b>:
 * <ul>
 *   <li>Android proto: 29,978 entries → 27,855 (92.9%) found in CDN</li>
 *   <li>iOS proto:     30,015 entries → only 13 found (iOS bundles differ from Android)</li>
 *   <li>Extra files:   26,198 files in CDN not referenced by current proto (older versions?)</li>
 * </ul>
 * Files are served by hash regardless of platform segment in the URL.
 *
 * <p>Uses Netty {@link ChunkedWriteHandler} for large file streaming
 * to avoid loading entire asset bundles (up to ~10MB each) into heap.
 *
 * <p>ETag = hash value → 304 Not Modified on re-request saves bandwidth.
 */
public class CdnServerInitializer extends ChannelInitializer<SocketChannel> {

    private final ServerConfig config;
    private final CdnFileHandler cdnHandler;

    public CdnServerInitializer(ServerConfig config) {
        this.config = config;
        this.cdnHandler = new CdnFileHandler(config.getCdnDir());
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(8192));   // headers only, no large bodies
        p.addLast(new ChunkedWriteHandler());         // for streaming large files
        p.addLast(cdnHandler);
    }

    // ── CDN file handler ─────────────────────────────────────────────────────

    @ChannelHandler.Sharable
    static class CdnFileHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        private static final Logger log = LoggerFactory.getLogger(CdnFileHandler.class);

        /** Regex for a 32-char lowercase hex MD5 hash */
        private static final String HASH_PATTERN = "[0-9a-f]{32}";

        private final String cdnDir;

        CdnFileHandler(String cdnDir) {
            this.cdnDir = cdnDir;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
            String uri = req.uri();
            // Strip query string
            if (uri.contains("?")) uri = uri.substring(0, uri.indexOf('?'));

            // Health check
            if (uri.equals("/health")) {
                send200Json(ctx, req, "{\"status\":\"ok\",\"server\":\"gotopazu-cdn\"}");
                return;
            }

            // Extract hash from URI:
            //   /assets-cancer/Resources/android/{hash}  → last segment
            //   /{hash}                                   → first/only segment
            String hash = extractHash(uri);
            if (hash == null) {
                log.warn("CDN bad request: {} — no valid hash", uri);
                send404(ctx, req, "no hash in uri");
                return;
            }

            File file = new File(cdnDir, hash);
            if (!file.exists() || !file.isFile()) {
                log.warn("CDN miss: {} hash={}", uri, hash);
                send404(ctx, req, hash);
                return;
            }

            // ETag = hash (content-addressed, hash IS the fingerprint)
            String eTag = "\"" + hash + "\"";
            String ifNoneMatch = req.headers().get(HttpHeaderNames.IF_NONE_MATCH, "");
            if (eTag.equals(ifNoneMatch)) {
                FullHttpResponse r304 = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_MODIFIED);
                r304.headers()
                    .set(HttpHeaderNames.ETAG, eTag)
                    .set(HttpHeaderNames.CACHE_CONTROL, "public, max-age=2592000, immutable");
                addCors(r304);
                writeAndClose(ctx, req, r304);
                log.debug("CDN 304 hash={}", hash);
                return;
            }

            // Stream the file
            long fileLen = file.length();
            RandomAccessFile raf = new RandomAccessFile(file, "r");

            HttpResponse headers = new DefaultHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            headers.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "application/octet-stream")
                .set(HttpHeaderNames.CONTENT_LENGTH, fileLen)
                .set(HttpHeaderNames.CACHE_CONTROL, "public, max-age=2592000, immutable")
                .set(HttpHeaderNames.ETAG, eTag);
            addCors(headers);

            boolean keepAlive = HttpUtil.isKeepAlive(req);
            if (keepAlive) {
                headers.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }

            ctx.write(headers);
            // ChunkedFile streams in 8K chunks without heap overflow
            ChannelFuture lastWrite = ctx.writeAndFlush(new ChunkedFile(raf, 0, fileLen, 8192));
            if (!keepAlive) {
                lastWrite.addListener(ChannelFutureListener.CLOSE);
            }

            log.debug("CDN 200 {} hash={} size={}", uri, hash, fileLen);
        }

        /** Extract 32-char hex hash from any URI format. Returns null if not found. */
        private static String extractHash(String uri) {
            // Walk segments from right to left
            String[] parts = uri.split("/");
            for (int i = parts.length - 1; i >= 0; i--) {
                if (parts[i].matches(HASH_PATTERN)) {
                    return parts[i];
                }
            }
            return null;
        }

        private static void send200Json(ChannelHandlerContext ctx, FullHttpRequest req, String json) {
            byte[] bytes = json.getBytes(io.netty.util.CharsetUtil.UTF_8);
            FullHttpResponse r = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(bytes));
            r.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8")
                .set(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
            addCors(r);
            writeAndClose(ctx, req, r);
        }

        private static void send404(ChannelHandlerContext ctx, FullHttpRequest req, String hash) {
            String body = "{\"error\":\"not found\",\"hash\":\"" + hash + "\"}";
            byte[] bytes = body.getBytes(io.netty.util.CharsetUtil.UTF_8);
            FullHttpResponse r = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND, Unpooled.wrappedBuffer(bytes));
            r.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8")
                .set(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
            addCors(r);
            writeAndClose(ctx, req, r);
        }

        private static void addCors(HttpResponse r) {
            r.headers()
                .set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "GET, HEAD, OPTIONS");
        }

        private static void writeAndClose(ChannelHandlerContext ctx, FullHttpRequest req,
                                           FullHttpResponse r) {
            if (HttpUtil.isKeepAlive(req)) {
                r.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                ctx.writeAndFlush(r);
            } else {
                ctx.writeAndFlush(r).addListener(ChannelFutureListener.CLOSE);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error("CDN handler error", cause);
            ctx.close();
        }
    }
}
