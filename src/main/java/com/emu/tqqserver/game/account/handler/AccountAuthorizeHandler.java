package com.emu.tqqserver.game.account.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.proto.pkg_proto.Cert;
import com.emu.tqqserver.proto.pkg_proto.Master;
import com.emu.tqqserver.proto.pkg_proto.Nocontent;
import com.emu.tqqserver.proto.pkg_pcommon.Error;
import com.emu.tqqserver.proto.pkg_pmaster.OpeningShow;
import com.emu.tqqserver.proto.pkg_pmaster.Version;
import com.emu.tqqserver.game.master.MasterDataService;
import com.emu.tqqserver.game.user.SessionService;
import com.emu.tqqserver.game.user.StoredDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route("/account/authorize")
public class AccountAuthorizeHandler extends BaseRoute {

    private static final Logger log = LoggerFactory.getLogger(AccountAuthorizeHandler.class);
    private final StoredDataService storedDataService = new StoredDataService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

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
}
