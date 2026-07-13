package com.emu.tqqserver.game.puzzle.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

@Route("/puzzle/skip")
public class PuzzleSkipHandler extends BaseRoute {
    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("puzzle/skip");
        sendNocontent(ctx, req);
    }
}
