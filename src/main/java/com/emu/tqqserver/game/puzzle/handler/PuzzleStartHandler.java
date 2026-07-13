package com.emu.tqqserver.game.puzzle.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.PuzzleStart;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import com.emu.tqqserver.game.puzzle.PuzzleService;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/puzzle/start")
public class PuzzleStartHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final PuzzleService puzzleService = new PuzzleService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("puzzle/start");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        UserEntity user = userService.findById(userId);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        com.fasterxml.jackson.databind.JsonNode reqBody = getJsonBody(req);
        
        int stageId = 1001; // default fallback stage
        if (reqBody.has("stage_id")) {
            stageId = reqBody.get("stage_id").asInt(1001);
        } else if (reqBody.has("stage_id[]")) { 
            stageId = reqBody.get("stage_id[]").asInt(1001);
        } else if (reqBody.has("sid")) {
            stageId = reqBody.get("sid").asInt(1001);
        } else if (reqBody.has("stage_group_id")) {
            stageId = reqBody.get("stage_group_id").asInt(1001);
        }
        
        int ap = reqBody.path("ap").asInt(1);
        int helperUid = reqBody.path("helper_uid").asInt(0);

        PuzzleService.PuzzleSession pSession = puzzleService.startPuzzle(userId, stageId, ap);

        // Update stored data with the new PUID
        com.emu.tqqserver.proto.pkg_proto.StoredData.Builder sdBuilder = storedDataService.build(user).toBuilder();
        if (pSession != null) {
            sdBuilder.setPuzzle(com.emu.tqqserver.proto.pkg_puser.Puzzle.newBuilder()
                .setPuid(pSession.puid)
                .setStageId(stageId)
                .setStatus(1)
                .build());
        }

        // Build a mock helper list user to show on screen
        com.emu.tqqserver.proto.pkg_puser.Card leaderCard = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
            .setId(1823880390)
            .setUid(helperUid > 0 ? helperUid : 10003)
            .setCardId(10651)
            .setCardPropertyId(106511)
            .setCardPropertyId2(106511)
            .setLevel(50)
            .setLevelAwake(50)
            .setLimitbreakRank(4)
            .setAwakePriority(1)
            .setActiveSkillLevel(5)
            .setPassiveSkillLevel1(5)
            .setPassiveSkillLevel2(5)
            .setPassiveSkillLevel3(5)
            .setKirameki(0)
            .setTokimeki(0)
            .build();

        ListUser helper = ListUser.newBuilder()
            .setUid(helperUid > 0 ? helperUid : 10003)
            .setLevel(75)
            .setName(helperUid > 0 ? "Friend " + helperUid : "三玖ちゃん")
            .setComment("助っ人です！よろしくね")
            .setPlayerTitleId(50701001)
            .setPlayerTitleTargetId(1)
            .setLastLoginTs((int) (System.currentTimeMillis() / 1000))
            .setFriend("none")
            .setLeader(leaderCard)
            .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
            .build();

        PuzzleStart response = PuzzleStart.newBuilder()
            .setStoredData(sdBuilder.build())
            .setHelper(helper)
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }
}
