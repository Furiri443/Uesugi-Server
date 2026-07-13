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

@Route("/shop/account")
public class ShopAccountHandler extends BaseRoute {


    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.debug("shop/account");
        PaymentAccount account = PaymentAccount.newBuilder()
            .setBirthday(true)
            .setMonthlyPaymentLimit(50000)
            .setMonthlyPaymentAmount(0)
            .build();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, account.toByteArray());
    
    }
}
