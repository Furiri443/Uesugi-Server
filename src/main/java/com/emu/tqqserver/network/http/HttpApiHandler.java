package com.emu.tqqserver.network.http;

import com.emu.tqqserver.game.account.AccountRoutes;
import com.emu.tqqserver.game.achievement.AchievementRoutes;
import com.emu.tqqserver.game.advertising.AdvertisingRoutes;
import com.emu.tqqserver.game.appointment.AppointmentRoutes;
import com.emu.tqqserver.game.block.BlockRoutes;
import com.emu.tqqserver.game.bonds.BondsRoutes;
import com.emu.tqqserver.game.book.BookRoutes;
import com.emu.tqqserver.game.card.CardRoutes;
import com.emu.tqqserver.game.cardspecial.CardSpecialRoutes;
import com.emu.tqqserver.game.challenge.ChallengeRoutes;
import com.emu.tqqserver.game.chapter.ChapterRoutes;
import com.emu.tqqserver.game.chat.ChatRoutes;
import com.emu.tqqserver.game.collection.CollectionRoutes;
import com.emu.tqqserver.game.cooking.CookingRoutes;
import com.emu.tqqserver.game.encore.EncoreRoutes;
import com.emu.tqqserver.game.fcm.FcmRoutes;
import com.emu.tqqserver.game.feature.FeatureRoutes;
import com.emu.tqqserver.game.friend.FriendRoutes;
import com.emu.tqqserver.game.gacha.GachaRoutes;
import com.emu.tqqserver.game.home.HomeRoutes;
import com.emu.tqqserver.game.image.ImageRoutes;
import com.emu.tqqserver.game.invitation.InvitationRoutes;
import com.emu.tqqserver.game.item.ItemRoutes;
import com.emu.tqqserver.game.likability.LikabilityRoutes;
import com.emu.tqqserver.game.log.LogRoutes;
import com.emu.tqqserver.game.lottery.LotteryRoutes;
import com.emu.tqqserver.game.master.MasterRoutes;
import com.emu.tqqserver.game.member.MemberRoutes;
import com.emu.tqqserver.game.mileage.MileageRoutes;
import com.emu.tqqserver.game.minigame.MiniGameRoutes;
import com.emu.tqqserver.game.news.NewsRoutes;
import com.emu.tqqserver.game.notice.NoticeRoutes;
import com.emu.tqqserver.game.options.OptionsRoutes;
import com.emu.tqqserver.game.photobooth.PhotoboothRoutes;
import com.emu.tqqserver.game.practiceexam.PracticeExamRoutes;
import com.emu.tqqserver.game.present.PresentRoutes;
import com.emu.tqqserver.game.puzzle.PuzzleRoutes;
import com.emu.tqqserver.game.quintupletgame.QuintupletGameRoutes;
import com.emu.tqqserver.game.resource.ResourceRoutes;
import com.emu.tqqserver.game.retryable.RetryableRoutes;
import com.emu.tqqserver.game.review.ReviewRoutes;
import com.emu.tqqserver.game.shop.ShopRoutes;
import com.emu.tqqserver.game.specialcontent.SpecialContentRoutes;
import com.emu.tqqserver.game.stage.StageRoutes;
import com.emu.tqqserver.game.story.StoryRoutes;
import com.emu.tqqserver.game.synthesis.SynthesisRoutes;
import com.emu.tqqserver.game.teambattle.TeamBattleRoutes;
import com.emu.tqqserver.game.tutorial.TutorialRoutes;
import com.emu.tqqserver.game.unit.UnitRoutes;
import com.emu.tqqserver.game.unitskill.UnitSkillRoutes;
import com.emu.tqqserver.game.user.UserRoutes;
import com.emu.tqqserver.game.vr.VrRoutes;
import com.emu.tqqserver.game.work.WorkRoutes;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP handler for all game API endpoints and asset CDN.
 *
 * <p><b>Discovered server URLs</b> (from Il2CppDumper + binary analysis):
 * <ul>
 *   <li>API base:   {@code https://www-cancer.enish-games.com}
 *   <li>Asset CDN:  {@code https://assets.enish-games.com/assets-cancer/Resources}
 *   <li>Realtime:   {@code ws://rt-cancer.enish-games.com}  (→ GameWebSocketHandler)
 * </ul>
 *
 * <p><b>Transport</b>: HTTP POST, Content-Type: application/x-protobuf
 *
 * <p><b>Required request headers</b> (from enish_headers.txt analysis):
 * <pre>
 *   X-Enish-App-Language          "ja"
 *   X-Enish-App-Platform          "android" / "ios"
 *   X-Enish-App-Resource-Cnt      resource download count
 *   X-Enish-App-Review            "0"
 *   X-Enish-App-Season-ID         current event season id
 *   X-Enish-App-Session           session token from account/certificate
 *   X-Enish-App-Store             "google" / "apple"
 *   X-Enish-App-User-Agent        app UA
 *   X-Enish-App-Version           "1.43.440"
 *   X-Enish-App-Version-Master    master data version
 *   X-Enish-App-Version-Resource  resource version
 *   X-Enish-Date                  unix timestamp string
 *   X-Enish-Expiredate            expiry unix timestamp string
 * </pre>
 */
@ChannelHandler.Sharable
public class HttpApiHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger log = LoggerFactory.getLogger(HttpApiHandler.class);

    /** Original production API host (stripped by patched client) */
    public static final String ORIGINAL_API_HOST = "www-cancer.enish-games.com";
    /** Original CDN host */
    public static final String ORIGINAL_CDN_HOST = "assets.enish-games.com";
    /** Original WS host */
    public static final String ORIGINAL_WS_HOST  = "rt-cancer.enish-games.com";

    /**
     * Asset CDN root — files are stored flat as {hash} (32-char hex filename).
     * URL pattern: GET /assets-cancer/Resources/{android|ios}/{hash}
     * Served from: ASSETS_CDN_DIR/{hash}
     *
     * Total: ~54,054 files, ~9.3GB
     * Android entries: 29,978  |  iOS entries: 30,015  (v1.43.440)
     */
    public static String getAssetsCdnDir() {
        return System.getProperty(
            "gotopazu.cdn.dir",
            "/Volumes/OCungRoi/PRJ/GTPZ-PS/tqq-server/assets_cdn"
        );
    }

    // ── Endpoint registry ────────────────────────────────────────────────────
    // Map of URI path → handler lambda(ctx, request) → void
    // Populated from the full endpoint list in endpoints.txt

    private static final Map<String, RouteHandler> ROUTES = new HashMap<>();

    @FunctionalInterface
    interface RouteHandler {
        void handle(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception;
    }

    static {
        Class<?>[] controllers = {
            AccountRoutes.class,
            AchievementRoutes.class,
            AdvertisingRoutes.class,
            AppointmentRoutes.class,
            BlockRoutes.class,
            BondsRoutes.class,
            BookRoutes.class,
            CardRoutes.class,
            CardSpecialRoutes.class,
            ChallengeRoutes.class,
            ChapterRoutes.class,
            ChatRoutes.class,
            CollectionRoutes.class,
            CookingRoutes.class,
            EncoreRoutes.class,
            FcmRoutes.class,
            FeatureRoutes.class,
            FriendRoutes.class,
            GachaRoutes.class,
            HomeRoutes.class,
            ImageRoutes.class,
            InvitationRoutes.class,
            ItemRoutes.class,
            LikabilityRoutes.class,
            LogRoutes.class,
            LotteryRoutes.class,
            MasterRoutes.class,
            MemberRoutes.class,
            MileageRoutes.class,
            MiniGameRoutes.class,
            NewsRoutes.class,
            NoticeRoutes.class,
            OptionsRoutes.class,
            PhotoboothRoutes.class,
            PracticeExamRoutes.class,
            PresentRoutes.class,
            PuzzleRoutes.class,
            QuintupletGameRoutes.class,
            ResourceRoutes.class,
            RetryableRoutes.class,
            ReviewRoutes.class,
            ShopRoutes.class,
            SpecialContentRoutes.class,
            StageRoutes.class,
            StoryRoutes.class,
            SynthesisRoutes.class,
            TeamBattleRoutes.class,
            TutorialRoutes.class,
            UnitRoutes.class,
            UnitSkillRoutes.class,
            UserRoutes.class,
            VrRoutes.class,
            WorkRoutes.class
        };

        for (Class<?> clazz : controllers) {
            Object controllerInstance = null;
            try {
                controllerInstance = clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                log.error("Failed to instantiate controller: {}", clazz.getName(), e);
                continue;
            }
            final Object instance = controllerInstance;

            for (java.lang.reflect.Method method : clazz.getDeclaredMethods()) {
                com.emu.tqqserver.annotation.Route[] routes = method.getAnnotationsByType(com.emu.tqqserver.annotation.Route.class);
                for (com.emu.tqqserver.annotation.Route route : routes) {
                    ROUTES.put(route.value(), (ctx, req) -> {
                        try {
                            method.invoke(instance, ctx, req);
                        } catch (java.lang.reflect.InvocationTargetException e) {
                            if (e.getCause() instanceof Exception) {
                                throw (Exception) e.getCause();
                            }
                            throw new RuntimeException(e.getCause());
                        }
                    });
                }
            }
        }
    }

    // ── Request handler ─────────────────────────────────────────────────────

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String rawUri = request.uri();
        // Strip query string for routing
        String uri = rawUri.contains("?") ? rawUri.substring(0, rawUri.indexOf('?')) : rawUri;

        // Strip app ID and version prefix (e.g. /aaaaaaaa/v1_43_440/account/authorize -> /account/authorize)
        if (uri.matches("^/[^/]+/v[0-9_]+/.*")) {
            uri = uri.replaceFirst("^/[^/]+/v[0-9_]+", "");
        }

        // Normalize trailing slash on prefix routes
        String routeKey = uri.endsWith("/") && uri.length() > 1 ? uri : uri;

        // Health check
        if (uri.equals("/health")) {
            sendJson(ctx, request, HttpResponseStatus.OK,
                "{\"status\":\"ok\",\"server\":\"gotopazu-private\",\"version\":\"1.43.440\"}");
            return;
        }

        // Asset CDN: /assets-cancer/Resources/<bundle_name>
        if (uri.contains("/assets-cancer/")) {
            String assetUri = uri.substring(uri.indexOf("/assets-cancer/"));
            serveAsset(ctx, request, assetUri);
            return;
        }

        // API routes
        RouteHandler handler = ROUTES.get(routeKey);

        // Prefix routes (e.g. /resource/list/, /gacha/serialnumber/)
        if (handler == null) {
            for (Map.Entry<String, RouteHandler> entry : ROUTES.entrySet()) {
                if (entry.getKey().endsWith("/") && uri.startsWith(entry.getKey())) {
                    handler = entry.getValue();
                    break;
                }
            }
        }

        if (handler != null) {
            log.debug("{} {} ({}B)", request.method(), uri,
                request.content().readableBytes());
            // Read session token from header
            String session = request.headers().get("X-Enish-App-Session", "");
            handler.handle(ctx, request);
        } else {
            log.warn("Unmapped route: {} {} — returning stub OK",
                request.method(), uri);
            // Return stub empty Nocontent for unknown routes
            sendProto(ctx, request, HttpResponseStatus.OK, new byte[0]);
        }
    }

    // ── Asset CDN serving ────────────────────────────────────────────────────
    //
    // URL pattern (after iOS/Android patch):
    //   GET /assets-cancer/Resources/{android|ios}/{hash}
    //   GET /assets-cancer/Resources/share/{hash}
    //
    // The hash is a 32-char hex string = exact filename in ASSETS_CDN_DIR.
    // Files are stored flat (no subdirectory).
    //
    // Examples:
    //   /assets-cancer/Resources/android/2003e7d515ec2d5f4f379e3dfaf26de5
    //   → ASSETS_CDN_DIR/2003e7d515ec2d5f4f379e3dfaf26de5
    //
    // The platform segment (android/ios/share) is ignored for the file lookup
    // because assets_cdn stores files by hash regardless of platform.

    private void serveAsset(ChannelHandlerContext ctx, FullHttpRequest req, String uri) {
        // Strip prefix: /assets-cancer/Resources/{platform}/{hash}
        // Split path segments after /assets-cancer/Resources/
        String afterPrefix = uri.replaceFirst("^/assets-cancer/Resources/?", "");
        // afterPrefix = "android/2003e7d..." or "ios/2003e7d..." or "share/2003e7d..."

        String hash;
        if (afterPrefix.contains("/")) {
            // {platform}/{hash} → take last segment
            hash = afterPrefix.substring(afterPrefix.lastIndexOf('/') + 1);
        } else {
            hash = afterPrefix;
        }

        // Validate: 32-char lowercase hex hash
        if (!hash.matches("[0-9a-f]{32}")) {
            log.warn("CDN invalid hash in URI: {} → '{}'", uri, hash);
            sendJson(ctx, req, HttpResponseStatus.BAD_REQUEST,
                "{\"error\":\"invalid hash\",\"hash\":\"" + hash + "\"}");
            return;
        }

        File file = new File(getAssetsCdnDir(), hash);

        if (!file.exists() || !file.isFile()) {
            log.warn("CDN asset not found: {} (hash={})", uri, hash);
            sendJson(ctx, req, HttpResponseStatus.NOT_FOUND,
                "{\"error\":\"asset not found\",\"hash\":\"" + hash + "\"}");
            return;
        }

        // ETag = hash (content-addressed → immutable)
        String eTag        = "\"" + hash + "\"";
        String ifNoneMatch = req.headers().get(HttpHeaderNames.IF_NONE_MATCH, "");
        if (eTag.equals(ifNoneMatch)) {
            FullHttpResponse r304 = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_MODIFIED);
            r304.headers()
                .set(HttpHeaderNames.ETAG, eTag)
                .set(HttpHeaderNames.CACHE_CONTROL, "public, max-age=2592000, immutable");
            addCorsHeaders(r304);
            writeAndFlush(ctx, req, r304);
            log.debug("CDN 304 hash={}", hash);
            return;
        }

        // Stream file qua ChunkedWriteHandler (đã có trong pipeline)
        // → không load toàn bộ file vào heap (bundle có thể lên tới ~10MB)
        try {
            long fileLen = file.length();
            RandomAccessFile raf = new RandomAccessFile(file, "r");

            HttpResponse headers = new DefaultHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                
            long nowTs = System.currentTimeMillis() / 1000;
            String appVer = req.headers().get("X-Enish-App-Version", "1.43.440");
            String digits = appVer.replaceAll("\\D+", "");
            if (digits.isEmpty()) digits = "143440";
            
            headers.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "application/octet-stream")
                .set(HttpHeaderNames.CONTENT_LENGTH, fileLen)
                .set(HttpHeaderNames.CACHE_CONTROL, "public, max-age=2592000, immutable")
                .set(HttpHeaderNames.ETAG, eTag)
                .set("X-Enish-Date", String.valueOf(nowTs))
                .set("X-Enish-Expiredate", String.valueOf(nowTs + 3600))
                .set("X-Enish-App-Version", appVer)
                .set("X-Enish-App-Version-Master", req.headers().get("X-Enish-App-Version-Master", digits))
                .set("X-Enish-App-Version-Resource", req.headers().get("X-Enish-App-Version-Resource", digits))
                .set("X-Enish-App-Session", req.headers().get("X-Enish-App-Session", "preservation-session-0001"));
                
            addCorsHeaders(headers);

            boolean keepAlive = HttpUtil.isKeepAlive(req);
            if (keepAlive) {
                headers.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }

            ctx.write(headers);
            ChannelFuture lastWrite;
            if (req.method().equals(io.netty.handler.codec.http.HttpMethod.HEAD)) {
                lastWrite = ctx.writeAndFlush(io.netty.handler.codec.http.LastHttpContent.EMPTY_LAST_CONTENT);
            } else {
                ctx.write(new io.netty.channel.DefaultFileRegion(raf.getChannel(), 0, fileLen));
                lastWrite = ctx.writeAndFlush(io.netty.handler.codec.http.LastHttpContent.EMPTY_LAST_CONTENT);
            }
            if (!keepAlive) {
                lastWrite.addListener(ChannelFutureListener.CLOSE);
            }

            log.debug("CDN 200 {} hash={} size={}", uri, hash, fileLen);
        } catch (Exception e) {
            log.error("CDN error {} hash={}", uri, hash, e);
            sendJson(ctx, req, HttpResponseStatus.INTERNAL_SERVER_ERROR,
                "{\"error\":\"io error\"}");
        }
    }

    // ── Response helpers ─────────────────────────────────────────────────────

    public static void sendProto(ChannelHandlerContext ctx, FullHttpRequest req,
                                  HttpResponseStatus status, byte[] protoBytes) {
        boolean isHead = req.method().equals(io.netty.handler.codec.http.HttpMethod.HEAD);
        FullHttpResponse response = new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1, status,
            isHead ? Unpooled.EMPTY_BUFFER : Unpooled.wrappedBuffer(protoBytes)
        );
        response.headers()
            .set(HttpHeaderNames.CONTENT_TYPE, "application/x-protobuf")
            .set(HttpHeaderNames.CONTENT_LENGTH, protoBytes.length);
        addCorsHeaders(response);
        writeAndFlush(ctx, req, response);
    }

    public static void sendJson(ChannelHandlerContext ctx, FullHttpRequest req,
                                 HttpResponseStatus status, String json) {
        byte[] bytes = json.getBytes(CharsetUtil.UTF_8);
        boolean isHead = req.method().equals(io.netty.handler.codec.http.HttpMethod.HEAD);
        FullHttpResponse response = new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1, status,
            isHead ? Unpooled.EMPTY_BUFFER : Unpooled.wrappedBuffer(bytes)
        );
        response.headers()
            .set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8")
            .set(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
        addCorsHeaders(response);
        writeAndFlush(ctx, req, response);
    }

    public static void writeAndFlush(ChannelHandlerContext ctx, FullHttpRequest req,
                                       FullHttpResponse response) {
        boolean keepAlive = HttpUtil.isKeepAlive(req);
        if (keepAlive) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.writeAndFlush(response);
        } else {
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }

    public static void addCorsHeaders(HttpMessage r) {
        r.headers()
            .set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
            .set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, OPTIONS")
            .set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS,
                "Content-Type, X-Enish-App-Session, X-Enish-App-Version, " +
                "X-Enish-Date, X-Enish-Expiredate, X-Enish-App-Platform");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("HTTP handler error", cause);
        ctx.close();
    }
}
