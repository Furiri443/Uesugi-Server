package com.emu.tqqserver.game.puzzle.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.puzzle.PuzzleService;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/puzzle/fail")
public class PuzzleFailHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final PuzzleService puzzleService = new PuzzleService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("puzzle/fail");
        UserEntity user = requireUser(req);
        
        // Clear session on fail
        puzzleService.clearSession(user.getUserId());
        
        com.emu.tqqserver.proto.pkg_proto.StoredData.Builder sdBuilder = storedDataService.build(user).toBuilder();
        sdBuilder.clearPuzzle();
        
        com.emu.tqqserver.proto.pkg_proto.Nocontent response = com.emu.tqqserver.proto.pkg_proto.Nocontent.newBuilder()
            .setStoredData(sdBuilder.build())
            .build();
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }
}
