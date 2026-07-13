package com.emu.tqqserver.game.challenge.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route("/challenge/reward")
public class ChallengeRewardHandler extends BaseRoute {

    private static final Logger log = LoggerFactory.getLogger(ChallengeRewardHandler.class);

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 log.info("challenge/reward"); sendNocontent(ctx, req); 
    }
}
