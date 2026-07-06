package com.emu.tqqserver.network.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.db.DatabaseManager;
import com.emu.tqqserver.game.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Base utilities for HTTP route handler classes.
 *
 * <p>All routes receive protobuf-net serialized request bodies and respond with
 * protobuf-net serialized data wrapped in {@code Master}, {@code Nocontent}, or {@code Error}.
 *
 * <p>Protocol:
 * <ul>
 *   <li>Request:  POST body = protobuf-net bytes (Content-Type: application/x-protobuf)
 *   <li>Response: protobuf-net bytes, always HTTP 200 even on error
 *   <li>Auth:     X-Enish-App-Session header = token from account/certificate
 * </ul>
 */
public abstract class BaseRoute {

    protected static final Logger log = LoggerFactory.getLogger(BaseRoute.class);
    protected final UserService userService = new UserService();

    /** Get the authenticated user for the current request, or throw an exception if unauthorized. */
    protected com.emu.tqqserver.game.user.UserEntity requireUser(FullHttpRequest req) {
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            com.emu.tqqserver.game.user.UserEntity user = userService.findById(userId);
            if (user != null) return user;
        }
        throw new RuntimeException("Unauthorized session: " + session);
    }

    /** Send an empty Nocontent response (stored_data only, no payload). */
    protected void sendNocontent(ChannelHandlerContext ctx, FullHttpRequest req) {
        com.emu.tqqserver.game.user.UserEntity user = requireUser(req);
        sendNocontent(ctx, req, user);
    }

    protected void sendNocontent(ChannelHandlerContext ctx, FullHttpRequest req, com.emu.tqqserver.game.user.UserEntity user) {
        com.emu.tqqserver.proto.pkg_proto.Nocontent nocontent = com.emu.tqqserver.proto.pkg_proto.Nocontent.newBuilder()
            .setStoredData(new com.emu.tqqserver.game.user.StoredDataService().build(user))
            .build();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, nocontent.toByteArray());
    }

    /** Extract session token from request header. */
    protected String getSession(FullHttpRequest req) {
        return req.headers().get("X-Enish-App-Session", "");
    }

    /** Extract platform from request header. */
    protected static String getPlatform(FullHttpRequest req) {
        return req.headers().get("X-Enish-App-Platform", "android");
    }

    /** Extract app version from request header. */
    protected static String getAppVersion(FullHttpRequest req) {
        return req.headers().get("X-Enish-App-Version", "1.43.440");
    }

    /** Extract master version from request header. */
    protected static String getMasterVersion(FullHttpRequest req) {
        return req.headers().get("X-Enish-App-Version-Master", "0");
    }

    /** Read the raw POST request body as UTF-8 string. */
    protected static String getBodyString(FullHttpRequest req) {
        return req.content().toString(io.netty.util.CharsetUtil.UTF_8);
    }

    /** Helper to parse the JSON body as a JsonNode. Returns an empty node if failed. */
    protected com.fasterxml.jackson.databind.JsonNode getJsonBody(FullHttpRequest req) {
        String body = getBodyString(req);
        if (body == null || body.trim().isEmpty()) {
            return com.fasterxml.jackson.databind.node.JsonNodeFactory.instance.objectNode();
        }
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readTree(body);
        } catch (Exception e) {
            // Might be form-urlencoded, let's try mapping form to json simply
            if (body.contains("=")) {
                com.fasterxml.jackson.databind.node.ObjectNode node = com.fasterxml.jackson.databind.node.JsonNodeFactory.instance.objectNode();
                for (String param : body.split("&")) {
                    String[] pair = param.split("=");
                    if (pair.length > 1) {
                        try {
                            node.put(java.net.URLDecoder.decode(pair[0], "UTF-8"), java.net.URLDecoder.decode(pair[1], "UTF-8"));
                        } catch (Exception ignored) {}
                    }
                }
                return node;
            }
            log.warn("Failed to parse POST body as JSON/Form: {}", e.getMessage());
            return com.fasterxml.jackson.databind.node.JsonNodeFactory.instance.objectNode();
        }
    }

    // Keep legacy signatures but make them warn and return default values, or try to parse from JSON keys if they somehow match
    protected String readStringField(FullHttpRequest req, int fieldNum) {
        log.warn("readStringField called for field {}. This relies on old protobuf parsing which is removed.", fieldNum);
        return "";
    }

    protected static int readIntField(FullHttpRequest req, int fieldNum) {
        log.warn("readIntField called for field {}. This relies on old protobuf parsing which is removed.", fieldNum);
        return 0;
    }

    protected static long readInt64Field(FullHttpRequest req, int fieldNum) {
        log.warn("readInt64Field called for field {}. This relies on old protobuf parsing which is removed.", fieldNum);
        return 0;
    }

    protected static java.util.List<Long> readInt64ListField(FullHttpRequest req, int fieldNum) {
        log.warn("readInt64ListField called for field {}. This relies on old protobuf parsing which is removed.", fieldNum);
        return new java.util.ArrayList<>();
    }

    /** Read the raw protobuf request body. */
    protected static byte[] getBody(FullHttpRequest req) {
        byte[] body = new byte[req.content().readableBytes()];
        req.content().readBytes(body);
        return body;
    }

    /** Get a database connection. */
    protected static Connection getConnection() throws SQLException {
        return DatabaseManager.getInstance().getConnection();
    }

    protected int[] readTwoIntFields(FullHttpRequest req) {
        log.warn("readTwoIntFields called. This relies on old protobuf parsing which is removed.");
        return new int[2];
    }
}
