package com.emu.tqqserver.game.cardspecial;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

public class CardSpecialRoutes extends BaseRoute {
    @Route("/cardspecial/realize")
    public void realize(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("cardspecial/realize"); sendNocontent(ctx, req); }
    @Route("/cardspecial/addpicture")
    public void addPicture(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/addpicture"); sendNocontent(ctx, req); }
    @Route("/cardspecial/addvoice")
    public void addVoice(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/addvoice"); sendNocontent(ctx, req); }
    @Route("/cardspecial/addslot")
    public void addSlot(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/addslot"); sendNocontent(ctx, req); }
    @Route("/cardspecial/restoreslot")
    public void restoreSlot(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/restoreslot"); sendNocontent(ctx, req); }
    @Route("/cardspecial/exchangeslot")
    public void exchangeSlot(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/exchangeslot"); sendNocontent(ctx, req); }
    @Route("/cardspecial/setslot")
    public void setSlot(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/setslot"); sendNocontent(ctx, req); }
    @Route("/cardspecial/setpassiveskill")
    public void setPassiveSkill(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/setpassiveskill"); sendNocontent(ctx, req); }
    @Route("/cardspecial/relotpassiveskill")
    public void relotPassiveSkill(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/relotpassiveskill"); sendNocontent(ctx, req); }
}
