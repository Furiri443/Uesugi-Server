package com.emu.tqqserver.game.mileage.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/mileage/dailyreward/receive")
public class MileageDailyrewardReceiveHandler extends BaseRoute {


    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("mileage/dailyreward/receive");
        
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        UserEntity user = null;
        if (userId != null) {
            user = userService.findById(userId);
        }
        if (user == null) {
            user = userService.findOrCreate(1, "guest");
            userId = user.getUserId();
        }

        // Update the received time to current time
        int now = (int) (System.currentTimeMillis() / 1000);
        user.setDailyRewardReceivedAt(now);
        userService.updateDailyRewardReceivedAt(user.getUserId(), now);

        // Nocontent uses StoredDataService which now picks up the updated dailyRewardReceivedAt
        sendNocontent(ctx, req, user);
    
    }
}
