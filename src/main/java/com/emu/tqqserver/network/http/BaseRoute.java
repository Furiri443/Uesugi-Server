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

    protected String readStringField(FullHttpRequest req, int fieldNum) {
        try {
            byte[] body = getBody(req);
            if (body.length == 0) return "";
            com.google.protobuf.CodedInputStream cis = com.google.protobuf.CodedInputStream.newInstance(body);
            while (!cis.isAtEnd()) {
                int tag = cis.readTag();
                if (tag == 0) break;
                int field = com.google.protobuf.WireFormat.getTagFieldNumber(tag);
                int type = com.google.protobuf.WireFormat.getTagWireType(tag);
                if (field == fieldNum && type == com.google.protobuf.WireFormat.WIRETYPE_LENGTH_DELIMITED) {
                    return cis.readString();
                } else {
                    cis.skipField(tag);
                }
            }
        } catch (Exception e) {
            log.warn("Failed to parse string field {}: {}", fieldNum, e.getMessage());
        }
        return "";
    }

    protected static int readIntField(FullHttpRequest req, int fieldNum) {
        try {
            byte[] body = getBody(req);
            if (body.length == 0) return 0;
            com.google.protobuf.CodedInputStream cis = com.google.protobuf.CodedInputStream.newInstance(body);
            while (!cis.isAtEnd()) {
                int tag = cis.readTag();
                if (tag == 0) break;
                int field = com.google.protobuf.WireFormat.getTagFieldNumber(tag);
                int type = com.google.protobuf.WireFormat.getTagWireType(tag);
                if (field == fieldNum && type == com.google.protobuf.WireFormat.WIRETYPE_VARINT) {
                    return cis.readInt32();
                } else {
                    cis.skipField(tag);
                }
            }
        } catch (Exception e) {
            log.warn("Failed to parse int field {}: {}", fieldNum, e.getMessage());
        }
        return 0;
    }

    protected static int[] readTwoIntFields(FullHttpRequest req) {
        int[] result = new int[2];
        try {
            byte[] body = getBody(req);
            if (body.length == 0) return result;
            com.google.protobuf.CodedInputStream cis = com.google.protobuf.CodedInputStream.newInstance(body);
            while (!cis.isAtEnd()) {
                int tag = cis.readTag();
                if (tag == 0) break;
                int field = com.google.protobuf.WireFormat.getTagFieldNumber(tag);
                int type = com.google.protobuf.WireFormat.getTagWireType(tag);
                if (field == 1 && type == com.google.protobuf.WireFormat.WIRETYPE_VARINT) {
                    result[0] = cis.readInt32();
                } else if (field == 2 && type == com.google.protobuf.WireFormat.WIRETYPE_VARINT) {
                    result[1] = cis.readInt32();
                } else {
                    cis.skipField(tag);
                }
            }
        } catch (Exception e) {
            log.warn("Failed to parse two int fields", e);
        }
        return result;
    }

    protected java.util.List<Long> readInt64ListField(FullHttpRequest req, int fieldNum) {
        java.util.List<Long> result = new java.util.ArrayList<>();
        try {
            byte[] body = getBody(req);
            if (body.length == 0) return result;
            com.google.protobuf.CodedInputStream cis = com.google.protobuf.CodedInputStream.newInstance(body);
            while (!cis.isAtEnd()) {
                int tag = cis.readTag();
                if (tag == 0) break;
                int field = com.google.protobuf.WireFormat.getTagFieldNumber(tag);
                int type = com.google.protobuf.WireFormat.getTagWireType(tag);
                if (field == fieldNum) {
                    if (type == com.google.protobuf.WireFormat.WIRETYPE_VARINT) {
                        result.add(cis.readInt64());
                    } else if (type == com.google.protobuf.WireFormat.WIRETYPE_LENGTH_DELIMITED) {
                        int length = cis.readRawVarint32();
                        int limit = cis.pushLimit(length);
                        while (!cis.isAtEnd()) {
                            result.add(cis.readInt64());
                        }
                        cis.popLimit(limit);
                    }
                } else {
                    cis.skipField(tag);
                }
            }
        } catch (Exception e) {
            log.warn("Failed to parse int64 list field {}: {}", fieldNum, e.getMessage());
        }
        return result;
    }
}
