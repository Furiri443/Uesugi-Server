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

@Route("/account/reset")
public class AccountResetHandler extends BaseRoute {

    private static final Logger log = LoggerFactory.getLogger(AccountResetHandler.class);
    private final StoredDataService storedDataService = new StoredDataService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.warn("ACCOUNT_RESET requested");
        try {
            com.emu.tqqserver.game.user.UserEntity user = requireUser(req);
            com.emu.tqqserver.game.user.UserService userService = new com.emu.tqqserver.game.user.UserService();
            userService.deleteUser(user.getUserId());
            log.info("Deleted user {}", user.getUserId());
            
            com.emu.tqqserver.proto.pkg_proto.Nocontent nocontent = com.emu.tqqserver.proto.pkg_proto.Nocontent.newBuilder().build();
            HttpApiHandler.sendProto(ctx, req, io.netty.handler.codec.http.HttpResponseStatus.OK, nocontent.toByteArray());
        } catch(Exception e) {
            log.error("Failed to delete user", e);
            HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.INTERNAL_SERVER_ERROR, buildErrorResponse(500, "Internal Server Error"));
        }
    
    }
}
