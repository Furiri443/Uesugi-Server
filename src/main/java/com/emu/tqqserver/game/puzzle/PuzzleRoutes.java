package com.emu.tqqserver.game.puzzle;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.PuzzleStart;
import com.emu.tqqserver.proto.pkg_proto.PuzzleResult;
import com.emu.tqqserver.proto.pkg_proto.Goods;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import com.emu.tqqserver.game.puzzle.PuzzleService;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;

import java.util.List;

public class PuzzleRoutes extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final PuzzleService puzzleService = new PuzzleService();

    /** POST /puzzle/start */
    @Route("/puzzle/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) {
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
        } else if (reqBody.has("stage_id[]")) { // sometimes arrays are passed in form-url-encoded
            stageId = reqBody.get("stage_id[]").asInt(1001);
        }

        puzzleService.startPuzzle(userId, stageId);

        // Build a mock helper list user to show on screen
        com.emu.tqqserver.proto.pkg_pmaster.Card leaderCard = com.emu.tqqserver.proto.pkg_pmaster.Card.newBuilder()
            .setId(1823880390)
            .setMemberId(10003)
            .setCostumeId(10651)
            .build();

        ListUser helper = ListUser.newBuilder()
            .setUid(10003)
            .setLevel(75)
            .setName("三玖ちゃん")
            .setComment("助っ人です！よろしくね")
            .setLastLoginTs((int) (System.currentTimeMillis() / 1000))
            .setLeader(leaderCard)
            .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
            .build();

        PuzzleStart response = PuzzleStart.newBuilder()
            .setStoredData(storedDataService.build(user))
            .setHelper(helper)
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }

    /** POST /puzzle/clear */
    @Route("/puzzle/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("puzzle/clear");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId == null) userId = 1L;

        UserEntity user = userService.findById(userId);
        if (user == null) throw new RuntimeException("User not found: " + userId);

        com.fasterxml.jackson.databind.JsonNode reqBody = getJsonBody(req);
        String uniqId = reqBody.has("uniqId") ? reqBody.get("uniqId").asText() : reqBody.path("uniq_id").asText("");
        int score = reqBody.has("score") ? reqBody.get("score").asInt() : 0;
        int clearType = reqBody.has("clearType") ? reqBody.get("clearType").asInt() : reqBody.path("clear_type").asInt(3);
        int stars = clearType > 0 ? clearType : 3;

        int stageId = 1001;
        if (reqBody.has("stage_id")) {
            stageId = reqBody.get("stage_id").asInt(1001);
        } else if (uniqId != null && !uniqId.isEmpty()) {
            try {
                String digits = uniqId.replaceAll("\\D+", "");
                if (!digits.isEmpty()) {
                    stageId = Integer.parseInt(digits);
                }
            } catch (Exception e) {
                // ignore
            }
        }

        List<Goods> rewards = puzzleService.clearPuzzle(userId, stageId, score, stars);

        PuzzleResult response = PuzzleResult.newBuilder()
            .setStoredData(storedDataService.build(user))
            .setNewRecord(true)
            .setScore(score)
            .addAllStageClearReward(rewards)
            .build();
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }

    /** POST /puzzle/retire */
    @Route("/puzzle/retire")
    public void retire(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("puzzle/retire");
        sendNocontent(ctx, req);
    }

    /** POST /puzzle/continue */
    @Route("/puzzle/continue")
    public void pcontinue(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("puzzle/continue");
        sendNocontent(ctx, req);
    }

    /** POST /puzzle/skip */
    @Route("/puzzle/skip")
    public void skip(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("puzzle/skip");
        sendNocontent(ctx, req);
    }

    /** POST /puzzle/unlock */
    @Route("/puzzle/unlock")
    public void unlock(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("puzzle/unlock");
        sendNocontent(ctx, req);
    }

    /** POST /puzzle/helper/list */
    @Route("/puzzle/helper/list")
    public void helperList(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("puzzle/helper/list");
        UserEntity me = requireUser(req);
        
        java.util.List<UserEntity> friends = new com.emu.tqqserver.game.friend.FriendService().getFriends(me.getUserId());
        
        com.emu.tqqserver.proto.pkg_proto.PuzzleHelper.Builder responseBuilder = com.emu.tqqserver.proto.pkg_proto.PuzzleHelper.newBuilder()
            .setStoredData(storedDataService.build(me))
            .setNextFriendIndex(0)
            .setNextNonFriendIndex(0);

        for (UserEntity u : friends) {

            com.emu.tqqserver.proto.pkg_pmaster.Card leaderCard = com.emu.tqqserver.proto.pkg_pmaster.Card.newBuilder()
                .setId(1823880390) // Default card id for now
                .setMemberId(10003)
                .setCostumeId(10651)
                .build();

            ListUser helper = ListUser.newBuilder()
                .setUid((int)u.getUserId())
                .setLevel(u.getRank() > 0 ? u.getRank() : 1)
                .setName(u.getNickname() != null ? u.getNickname() : "Player")
                .setComment(u.getComment() != null ? u.getComment() : "Hello!")
                .setLastLoginTs((int) (System.currentTimeMillis() / 1000))
                .setLeader(leaderCard)
                .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
                .build();
                
            responseBuilder.addHelpers(helper);
        }
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, responseBuilder.build().toByteArray());
    }

    /** POST /puzzle/ranking/ */
    @Route("/puzzle/ranking/")
    public void ranking(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("puzzle/ranking/");
        UserEntity me = requireUser(req);
        
        java.util.List<UserEntity> allUsers = new com.emu.tqqserver.game.user.UserService().getAllUsers();
        java.util.List<UserEntity> friends = new com.emu.tqqserver.game.friend.FriendService().getFriends(me.getUserId());
        java.util.Set<Long> friendIds = new java.util.HashSet<>();
        for (UserEntity f : friends) friendIds.add(f.getUserId());
        friendIds.add(me.getUserId()); // Always include self in friend ranking
        
        com.emu.tqqserver.proto.pkg_proto.Ranking.Builder leaderBuilder = com.emu.tqqserver.proto.pkg_proto.Ranking.newBuilder();
        com.emu.tqqserver.proto.pkg_proto.Ranking.Builder friendBuilder = com.emu.tqqserver.proto.pkg_proto.Ranking.newBuilder();
        
        int rank = 1;
        int friendRank = 1;
        for (UserEntity u : allUsers) {
            com.emu.tqqserver.proto.pkg_proto.RankUser ru = com.emu.tqqserver.proto.pkg_proto.RankUser.newBuilder()
                .setUid((int)u.getUserId())
                .setName(u.getNickname() != null ? u.getNickname() : "Unknown")
                .setRank(rank)
                .setScore(9999999 - rank) // Fake score for display
                .build();
                
            leaderBuilder.addRanker(ru);
            
            if (friendIds.contains(u.getUserId())) {
                com.emu.tqqserver.proto.pkg_proto.RankUser fru = ru.toBuilder().setRank(friendRank).build();
                friendBuilder.addRanker(fru);
                if (u.getUserId() == me.getUserId()) {
                    friendBuilder.setPlayer(fru);
                }
                friendRank++;
            }
            
            if (u.getUserId() == me.getUserId()) {
                leaderBuilder.setPlayer(ru);
            }
            rank++;
        }
        
        leaderBuilder.setTotal(allUsers.size());
        friendBuilder.setTotal(friendIds.size());
        
        com.emu.tqqserver.proto.pkg_proto.PuzzleRanking response = com.emu.tqqserver.proto.pkg_proto.PuzzleRanking.newBuilder()
            .setStoredData(storedDataService.build(me))
            .setLeader(leaderBuilder.build())
            .setFriend(friendBuilder.build())
            .build();
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }
}
