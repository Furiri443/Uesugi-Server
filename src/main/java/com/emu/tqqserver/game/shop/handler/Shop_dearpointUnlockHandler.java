package com.emu.tqqserver.game.shop.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.IAPProductList;
import com.emu.tqqserver.proto.pkg_proto.PaymentAccount;
import com.emu.tqqserver.game.shop.IAPProductService;
import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.game.user.StoredDataService;
import java.util.Map;
import java.util.List;

@Route("/shop_dearpoint/unlock")
public class Shop_dearpointUnlockHandler extends BaseRoute {


    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 log.debug("shop_dearpoint/unlock"); sendNocontent(ctx, req); 
    }
}
