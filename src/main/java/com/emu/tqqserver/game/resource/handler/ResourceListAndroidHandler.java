package com.emu.tqqserver.game.resource.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Route("/resource/list/android")
public class ResourceListAndroidHandler extends BaseRoute {

    public static String getResourceListDir() {
    return System.getProperty(
    "gotopazu.resource.list.dir",
    System.getenv("GOTOPAZU_RESOURCE_DIR") != null
    ? System.getenv("GOTOPAZU_RESOURCE_DIR")
    : "./gotopazu");
    }
    private static final java.util.Map<String, byte[]> cachedProtoBytesMap = new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.Map<String, Integer> cachedProtoCountMap = new java.util.concurrent.ConcurrentHashMap<>();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        String uri = req.uri();

        // Extract platform from URI (for logging only)
        String platform = "Android";
        int lastSlash = uri.lastIndexOf('/');
        if (lastSlash >= 0 && lastSlash < uri.length() - 1) {
            String tail = uri.substring(lastSlash + 1);
            if (!tail.isEmpty()) {
                platform = tail.substring(0, 1).toUpperCase() + tail.substring(1).toLowerCase();
                if (platform.equals("Ios") || platform.equals("Android")) {
                    // ok
                } else if (platform.toLowerCase().startsWith("android")) {
                    platform = "Android";
                } else {
                    platform = "Ios";
                }
            }
        }
        if (!uri.contains("/resource/list/")) {
            String headerPlatform = getPlatform(req);
            platform = headerPlatform.equalsIgnoreCase("ios") ? "Ios" : "Android";
        }

        log.info("resource/list platform={} uri={}", platform, uri);

        byte[] protoBytes = cachedProtoBytesMap.get(platform);
        if (protoBytes == null) {
            synchronized (ResourceListAndroidHandler.class) {
                protoBytes = cachedProtoBytesMap.get(platform);
                if (protoBytes == null) {
                    protoBytes = buildFromManifests(platform, ctx, req);
                    if (protoBytes == null)
                        return; // error already sent
                    cachedProtoBytesMap.put(platform, protoBytes);
                }
            }
        }

        log.debug("Serving resource/list/{} ({} bytes)", platform, protoBytes.length);

        io.netty.handler.codec.http.FullHttpResponse response = new io.netty.handler.codec.http.DefaultFullHttpResponse(
                io.netty.handler.codec.http.HttpVersion.HTTP_1_1,
                io.netty.handler.codec.http.HttpResponseStatus.OK,
                io.netty.buffer.Unpooled.wrappedBuffer(protoBytes));
        response.headers()
                .set(io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE, "application/x-protobuf")
                .set(io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH, protoBytes.length)
                .set("X-Enish-App-Version-Resource", "143440")
                .set("X-Enish-App-Version", "1.43.440")
                .set("X-Enish-App-Version-Master", "143440")
                .set("X-Enish-Resource-Version", "143440")
                .set("X-Enish-Resource-Revision", "1")
                .set("X-Enish-App-Resource-Cnt", String.valueOf(cachedProtoCountMap.getOrDefault(platform, 0)));

        String host = req.headers().get(io.netty.handler.codec.http.HttpHeaderNames.HOST);
        if (host == null || host.isEmpty()) {
            host = "127.0.0.1:8080";
        }
        response.headers().set("X-Enish-Resource-Url", "http://" + host + "/assets-cancer/Resources");

        HttpApiHandler.addCorsHeaders(response);
        HttpApiHandler.writeAndFlush(ctx, req, response);
    
    }

private static byte[] buildFromManifests(String platform, ChannelHandlerContext ctx, FullHttpRequest req) {
        try {
            com.emu.tqqserver.proto.pkg_pcommon.Resources.Builder builder = com.emu.tqqserver.proto.pkg_pcommon.Resources
                    .newBuilder();

            int count = 0;
            java.util.Set<Integer> seen = new java.util.HashSet<>();

            java.io.File file = new java.io.File(getResourceListDir(), platform + ".json");
            if (file.exists()) {
                log.info("Loading platform-specific resource list for {}: {}", platform, file.getName());
                String jsonStr = new String(java.nio.file.Files.readAllBytes(file.toPath()),
                        java.nio.charset.StandardCharsets.UTF_8);
                com.google.gson.JsonObject rootObj = com.google.gson.JsonParser.parseString(jsonStr).getAsJsonObject();
                com.google.gson.JsonObject resourceObj = rootObj.getAsJsonObject("resource");
                if (resourceObj != null) {
                    for (java.util.Map.Entry<String, com.google.gson.JsonElement> entry : resourceObj.entrySet()) {
                        try {
                            int id = Integer.parseInt(entry.getKey());
                            if (seen.contains(id))
                                continue;
                            seen.add(id);

                            com.google.gson.JsonObject fileObj = entry.getValue().getAsJsonObject();
                            String hash = fileObj.get("hash").getAsString();
                            int size = fileObj.get("size") != null ? fileObj.get("size").getAsInt() : 0;

                            com.emu.tqqserver.proto.pkg_pcommon.ResourceInfo info = com.emu.tqqserver.proto.pkg_pcommon.ResourceInfo
                                    .newBuilder()
                                    .setHash(hash)
                                    .setSize(size)
                                    .build();
                            builder.putResource(id, info);
                            count++;
                        } catch (NumberFormatException e) {
                            log.warn("Skipping non-integer resource id in {}.json: {}", platform, entry.getKey());
                        }
                    }
                }
            } else {
                java.io.File fallbackFile = new java.io.File(getResourceListDir(), "AssestBundle.json");
                if (fallbackFile.exists()) {
                    log.info("Platform manifest for {} not found, loading fallback manifest: {}", platform, fallbackFile.getName());
                    String jsonStr = new String(java.nio.file.Files.readAllBytes(fallbackFile.toPath()),
                            java.nio.charset.StandardCharsets.UTF_8);
                    com.google.gson.JsonArray arr = com.google.gson.JsonParser.parseString(jsonStr).getAsJsonArray();

                    for (com.google.gson.JsonElement elem : arr) {
                        com.google.gson.JsonObject obj = elem.getAsJsonObject();
                        if (obj.get("id") == null || obj.get("file") == null)
                            continue;

                        int id = obj.get("id").getAsInt();
                        if (seen.contains(id))
                            continue;
                        seen.add(id);

                        com.google.gson.JsonObject fileObj = obj.getAsJsonObject("file");
                        String hash = fileObj.get("name").getAsString();
                        int size = fileObj.get("size") != null ? fileObj.get("size").getAsInt() : 0;

                        com.emu.tqqserver.proto.pkg_pcommon.ResourceInfo info = com.emu.tqqserver.proto.pkg_pcommon.ResourceInfo
                                .newBuilder()
                                .setHash(hash)
                                .setSize(size)
                                .build();
                        builder.putResource(id, info);
                        count++;
                    }
                } else {
                    log.error("No resource manifest found for platform: {} (tried: {}, fallback: {})", platform, file.getAbsolutePath(), fallbackFile.getAbsolutePath());
                }
            }

            cachedProtoCountMap.put(platform, count);
            byte[] protoBytes = builder.build().toByteArray();
            log.info("Built resource list from manifests for platform {}: {} entries, {} bytes", platform, count, protoBytes.length);
            return protoBytes;
        } catch (Exception e) {
            log.error("Failed to parse manifests for platform " + platform, e);
            throw new RuntimeException(e);
        }
    }
}
