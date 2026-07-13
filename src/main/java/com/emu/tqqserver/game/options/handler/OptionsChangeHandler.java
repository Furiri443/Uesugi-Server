package com.emu.tqqserver.game.options.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.emu.tqqserver.game.user.UserDao;

@Route("/options/change")
public class OptionsChangeHandler extends BaseRoute {

    private final UserDao userDao = new UserDao();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 
        log.info("options/change"); 
        UserEntity me = requireUser(req);
        
        try {
            JsonNode body = getJsonBody(req);
            int bgm = body.has("bgm") ? body.get("bgm").asInt() : me.getOptionBgm();
            int se = body.has("se") ? body.get("se").asInt() : me.getOptionSe();
            int voice = body.has("voice") ? body.get("voice").asInt() : me.getOptionVoice();
            int pR6 = body.has("protect_card_r6") ? body.get("protect_card_r6").asInt() : me.getOptionProtectCardR6();
            int pR5 = body.has("protect_card_r5") ? body.get("protect_card_r5").asInt() : me.getOptionProtectCardR5();
            int pFirst = body.has("protect_card_first") ? body.get("protect_card_first").asInt() : me.getOptionProtectCardFirst();

            userDao.updateOptions(me.getUserId(), bgm, se, voice, pR6, pR5, pFirst);

            sendNocontent(ctx, req);
        } catch (Exception e) {
            log.error("Failed to parse options/change", e);
            sendNocontent(ctx, req);
        }
    
    }
}
