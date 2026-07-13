package com.emu.tqqserver.game.present.handler;

import com.emu.tqqserver.game.present.PresentService;
import com.emu.tqqserver.proto.pkg_pmisc.Present;
import com.emu.tqqserver.proto.pkg_proto.PresentList;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.Goods;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;
import java.util.ArrayList;
import java.util.List;

@Route("/present/list")
public class PresentListHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final PresentService presentService = new PresentService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.debug("present/list");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        UserEntity user = userService.findById(userId);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        List<Present> presents = presentService.getActivePresents(userId);
        if (presents.isEmpty()) {
            presentService.addPresent(userId, 2, 0, 5000, "ごとぱずサーバーへようこそ！"); // 5000 jewels welcome gift
            presentService.addPresent(userId, 1, 0, 100000, "開発者からのプレゼント"); // 100k coins
            presents = presentService.getActivePresents(userId);
        }

        PresentList response = PresentList.newBuilder()
            .setStoredData(storedDataService.build(user))
            .addAllList(presents)
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    
    }
}
