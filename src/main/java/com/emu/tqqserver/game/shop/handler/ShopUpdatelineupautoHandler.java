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

@Route("/shop/updatelineupauto")
public class ShopUpdatelineupautoHandler extends BaseRoute {


    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("shop/updatelineupauto");
        UserEntity me = requireUser(req);
        if (me == null) {
            sendNocontent(ctx, req);
            return;
        }
        UserService userService = new UserService();
        me = userService.findById(me.getUserId());
        com.emu.tqqserver.proto.pkg_proto.ShopResult.Builder builder = com.emu.tqqserver.proto.pkg_proto.ShopResult.newBuilder();
        builder.setStoredData(new StoredDataService().build(me));
        
        // Prevent infinite loop by returning some IDs indicating the lineup was updated
        builder.addId(100);
        builder.addId(101);
        builder.addId(102);
        builder.addId(103);
        
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, builder.build().toByteArray());
    
    }
}
