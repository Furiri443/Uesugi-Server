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

@Route("/account/inherit")
public class AccountInheritHandler extends BaseRoute {

    private static final Logger log = LoggerFactory.getLogger(AccountInheritHandler.class);
    private final StoredDataService storedDataService = new StoredDataService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("INHERIT");
        new AccountAuthorizeHandler().handle(ctx, req);
    
    }
}
