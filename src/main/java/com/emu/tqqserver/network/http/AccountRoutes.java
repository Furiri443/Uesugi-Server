package com.emu.tqqserver.network.http;

import com.emu.tqqserver.annotation.Route;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.model.entity.UserEntity;
import com.emu.tqqserver.proto.pkg_proto.Cert;
import com.emu.tqqserver.proto.pkg_proto.Master;
import com.emu.tqqserver.proto.pkg_proto.Nocontent;
import com.emu.tqqserver.proto.pkg_pcommon.Error;
import com.emu.tqqserver.proto.pkg_pmaster.OpeningShow;
import com.emu.tqqserver.proto.pkg_pmaster.Version;
import com.emu.tqqserver.service.MasterDataService;
import com.emu.tqqserver.service.SessionService;
import com.emu.tqqserver.service.StoredDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * POST /account/* endpoints.
 *
 * <p>Authentication flow (đúng theo traffic thật đã capture):
 * <ol>
 *   <li>POST /account/certificate → trả {@code Cert { version }}, KHÔNG có session token
 *       (server thật không cấp session ở bước này — session sinh ra khi authorize).
 *   <li>POST /account/authorize → trả {@code AccountAuthorize { session, opening_shows, term_revision }}
 * </ol>
 */
public class AccountRoutes extends BaseRoute {

    private static final Logger log = LoggerFactory.getLogger(AccountRoutes.class);
    private final StoredDataService storedDataService = new StoredDataService();

    /**
     * POST /account/certificate
     *
     * Response thật (capture v1.43.440):
     *   Cert {
     *     version: Version { platform, application, resource, master, term, revision }
     *   }
     */
    @Route("/account/certificate")
    public void certificate(ChannelHandlerContext ctx, FullHttpRequest req) {
        String platform = getPlatform(req);
        log.info("CERTIFICATE platform={}", platform);

        Version version = Version.newBuilder()
            .setPlatform(platform)
            .setApplication(getAppVersion(req))
            .setResource(1906)
            .setMaster(1497)
            .setTerm(2)
            .setRevision(2)
            .build();

        com.emu.tqqserver.proto.pkg_proto.AccountCertificate cert =
            com.emu.tqqserver.proto.pkg_proto.AccountCertificate.newBuilder()
                .setVersion(version)
                .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, cert.toByteArray());
    }

    /**
     * POST /account/authorize
     *
     * Tạo (hoặc tìm lại) user dựa trên device/platform id, phát hành session token,
     * trả về {@code AccountAuthorize} — KHÔNG trả toàn bộ Master ở bước này
     * (đúng với traffic thật: client gọi /master/all riêng sau đó).
     */
    @Route("/account/authorize")
    public void authorize(ChannelHandlerContext ctx, FullHttpRequest req) {
        String platform = getPlatform(req);
        
        String deviceId = "guest-" + System.currentTimeMillis(); // Fallback
        
        byte[] body = getBody(req);
        String bodyStr = new String(body, java.nio.charset.StandardCharsets.UTF_8);
        
        if (bodyStr.contains("uuid=")) {
            String[] params = bodyStr.split("&");
            for (String p : params) {
                if (p.startsWith("uuid=")) {
                    deviceId = p.substring(5);
                    break;
                }
            }
        } else {
            // Also check headers just in case
            String hdr = req.headers().get("X-Enish-App-Device-Id");
            if (hdr != null && !hdr.isEmpty()) {
                deviceId = hdr;
            }
        }

        UserEntity user = userService.findOrCreateGuest(deviceId);
        String token = SessionService.createSession(user.getUserId());

        com.emu.tqqserver.proto.pkg_proto.AccountAuthorize authorize =
            com.emu.tqqserver.proto.pkg_proto.AccountAuthorize.newBuilder()
                .setSession(token)
                .setTermRevision(2)
                .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, authorize.toByteArray());
        log.info("AUTHORIZE userId={} token={}...", user.getUserId(),
            token.substring(0, Math.min(8, token.length())));
    }

    /** POST /account/inherit — account transfer (trả cùng shape với authorize) */
    @Route("/account/inherit")
    public void inherit(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("INHERIT");
        authorize(ctx, req);
    }

    /** POST /account/inherit/password */
    @Route("/account/inherit/password")
    public void inheritPassword(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("INHERIT_PASSWORD");
        com.emu.tqqserver.proto.pkg_proto.AuthorizeInheritPassword resp =
            com.emu.tqqserver.proto.pkg_proto.AuthorizeInheritPassword.newBuilder()
                .setCode("000000")
                .build();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, resp.toByteArray());
    }

    /** POST /account/reset — reset player data (dev/debug only) */
    @Route("/account/reset")
    public void reset(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.warn("ACCOUNT_RESET requested");
        try {
            com.emu.tqqserver.model.entity.UserEntity user = requireUser(req);
            com.emu.tqqserver.service.UserService userService = new com.emu.tqqserver.service.UserService();
            userService.deleteUser(user.getUserId());
            log.info("Deleted user {}", user.getUserId());
            
            com.emu.tqqserver.proto.pkg_proto.Nocontent nocontent = com.emu.tqqserver.proto.pkg_proto.Nocontent.newBuilder().build();
            HttpApiHandler.sendProto(ctx, req, io.netty.handler.codec.http.HttpResponseStatus.OK, nocontent.toByteArray());
        } catch(Exception e) {
            log.error("Failed to delete user", e);
            HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.INTERNAL_SERVER_ERROR, buildErrorResponse(500, "Internal Server Error"));
        }
    }

    static byte[] buildErrorResponse(int code, String message) {
        Error error = Error.newBuilder()
            .setCode(code)
            .setMsg(message)
            .setTs((int) (System.currentTimeMillis() / 1000))
            .build();
        return error.toByteArray();
    }
}
