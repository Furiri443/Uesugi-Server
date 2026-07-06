package com.emu.tqqserver.game.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

public class SessionService {

    private static final Logger log = LoggerFactory.getLogger(SessionService.class);
    private static final SecureRandom RNG = new SecureRandom();

    /** token -> userId */
    private static final ConcurrentHashMap<String, Long> SESSIONS = new ConcurrentHashMap<>();
    /** userId -> token (latest) */
    private static final ConcurrentHashMap<Long, String> USER_TOKENS = new ConcurrentHashMap<>();

    public static String createSession(long userId) {
        String headerJson = "{\"alg\":\"RS256\",\"typ\":\"JWT\"}";
        String payloadJson = String.format("{\"exp\":2000000000,\"uid\":%d}", userId);

        String headerB64 = Base64.getUrlEncoder().withoutPadding().encodeToString(headerJson.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        String payloadB64 = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        // Append random UUID to signature to make token unique, allowing multiple sessions for same user
        String signatureB64 = "dummy_signature_" + java.util.UUID.randomUUID().toString().replace("-", "");

        String token = headerB64 + "." + payloadB64 + "." + signatureB64;

        USER_TOKENS.put(userId, token);
        SESSIONS.put(token, userId);

        return token;
    }

    public static Long getUserId(String token) {
        if (token == null || token.isEmpty()) return null;
        Long uid = SESSIONS.get(token);
        if (uid != null) return uid;

        // Try parsing JWT payload
        try {
            String[] parts = token.split("\\.");
            if (parts.length == 3) {
                String payloadJson = new String(java.util.Base64.getUrlDecoder().decode(parts[1]), java.nio.charset.StandardCharsets.UTF_8);
                int idx = payloadJson.indexOf("\"uid\":");
                if (idx != -1) {
                    int endIdx = payloadJson.indexOf('}', idx);
                    if (endIdx != -1) {
                        String uidStr = payloadJson.substring(idx + 6, endIdx).trim();
                        Long parsedUid = Long.parseLong(uidStr);
                        SESSIONS.put(token, parsedUid);
                        USER_TOKENS.put(parsedUid, token);
                        return parsedUid;
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Failed to parse JWT token: {}", token);
        }
        return null;
    }

    public static void invalidate(String token) {
        Long uid = SESSIONS.remove(token);
        if (uid != null) USER_TOKENS.remove(uid);
    }
}
