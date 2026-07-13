package com.emu.tqqserver.game.chat.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.game.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route("/chat/group/kick")
public class ChatGroupKickHandler extends BaseRoute {

    private static final Logger log = LoggerFactory.getLogger(ChatGroupKickHandler.class);

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 log.debug("chat/group/kick"); sendNocontent(ctx, req); 
    }
}
