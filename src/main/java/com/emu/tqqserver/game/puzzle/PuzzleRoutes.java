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

import com.emu.tqqserver.proto.pkg_proto.PuzzleHelper;

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
            .setActiveSkillLevel(5)
            .setPassiveSkillLevel1(5)
            .setPassiveSkillLevel2(5)
            .setPassiveSkillLevel3(5)
            .build();

        ListUser helper = ListUser.newBuilder()
            .setUid(helperUid > 0 ? helperUid : 10003)
            .setLevel(75)
            .setName(helperUid > 0 ? "Friend " + helperUid : "三玖ちゃん")
            .setComment("助っ人です！よろしくね")
            .setLastLoginTs((int) (System.currentTimeMillis() / 1000))
            .setLeader(leaderCard)
            .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
            .build();

        PuzzleStart response = PuzzleStart.newBuilder()
            .setStoredData(sdBuilder.build())
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

        String uniqId = "";
        int score = 0;
        int clearType = 3; // 3 stars fallback
        int stageId = 1001;
        
        try {
            // Priority 1: Protobuf
            byte[] body = getBody(req);
            if (body.length > 0) {
                com.emu.tqqserver.proto.pkg_proto.RequestPuzzleClear protoReq = com.emu.tqqserver.proto.pkg_proto.RequestPuzzleClear.parseFrom(body);
                uniqId = protoReq.getPuzzleUniqId();
                score = protoReq.getScore();
                clearType = protoReq.getClearType();
            }
        } catch (Exception e) {
            log.warn("Failed to parse RequestPuzzleClear protobuf, fallback to form: {}", e.getMessage());
            com.fasterxml.jackson.databind.JsonNode reqBody = getJsonBody(req);
            uniqId = reqBody.has("uniqId") ? reqBody.get("uniqId").asText() : reqBody.path("puzzle_uniq_id").asText("");
            score = reqBody.path("score").asInt();
            clearType = reqBody.path("clear_type").asInt(3);
        }

        PuzzleService.PuzzleSession pSession = puzzleService.getActiveSession(userId);
        if (pSession != null && (uniqId == null || uniqId.isEmpty() || uniqId.equals(pSession.puid))) {
            stageId = pSession.stageId;
        }

        List<Goods> rewards = puzzleService.clearPuzzle(userId, stageId, score, clearType);
        
        // Clear session
        puzzleService.clearSession(userId);

        com.emu.tqqserver.game.card.CardService cardService = new com.emu.tqqserver.game.card.CardService();
        List<com.emu.tqqserver.game.user.CardEntity> cards = cardService.getUserCards(userId);
        
        List<com.emu.tqqserver.proto.pkg_proto.PuzzleCard> puzzleCards = new java.util.ArrayList<>();
        for (int i = 0; i < Math.min(5, cards.size()); i++) {
            com.emu.tqqserver.game.user.CardEntity c = cards.get(i);
            int propertyId = c.getCardId() * 10 + 1;
            com.emu.tqqserver.proto.pkg_puser.Card puserCard = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                .setId(c.getId())
                .setUid(userId.intValue())
                .setCardId(c.getCardId())
                .setCardPropertyId(propertyId)
                .setCardPropertyId2(propertyId)
                .setExp(c.getExp())
                .setLevel(c.getLevel())
                .setLevelAwake(0)
                .setActiveSkillLevel(Math.max(1, c.getActiveSkillLevel()))
                .setPassiveSkillLevel1(Math.max(1, c.getActiveSkillLevel()))
                .setPassiveSkillLevel2(Math.max(1, c.getActiveSkillLevel()))
                .setPassiveSkillLevel3(Math.max(1, c.getActiveSkillLevel()))
                .setLimitbreakRank(c.getLimitbreakRank())
                .setAwakePriority(0)
                .build();

            puzzleCards.add(com.emu.tqqserver.proto.pkg_proto.PuzzleCard.newBuilder()
                .setCard(puserCard)
                .setBaseExp(50)
                .build());
        }

        // Clear the PUID in StoredData so client knows puzzle ended
        com.emu.tqqserver.proto.pkg_proto.StoredData.Builder sdBuilder = storedDataService.build(user).toBuilder();
        sdBuilder.clearPuzzle();
        sdBuilder.addClear("puzzle");

        com.emu.tqqserver.proto.pkg_puser.Card leaderCard = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
            .setId(1823880390)
            .setUid(10003)
            .setCardId(10651)
            .setCardPropertyId(106511)
            .setCardPropertyId2(106511)
            .setLevel(50)
            .setLevelAwake(0)
            .setActiveSkillLevel(5)
            .setPassiveSkillLevel1(5)
            .setPassiveSkillLevel2(5)
            .setPassiveSkillLevel3(5)
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

        PuzzleResult response = PuzzleResult.newBuilder()
            .setStoredData(sdBuilder.build())
            .setNewRecord(true)
            .setScore(score)
            .addAllStageClearReward(rewards)
            .addAllUnit(puzzleCards)
            .setPlayerLevel(user.getRank() > 0 ? user.getRank() : 1)
            .setPlayerExp(user.getExp())
            .setHelper(helper)
            .putRelationshipDearpoint(1, 10)
            .putRelationshipDearpoint(2, 10)
            .putRelationshipDearpoint(3, 10)
            .putRelationshipDearpoint(4, 10)
            .putRelationshipDearpoint(5, 10)
            .build();
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }

    /** POST /puzzle/fail */
    @Route("/puzzle/fail")
    public void fail(ChannelHandlerContext ctx, FullHttpRequest req) {
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

    /** POST /puzzle/retire */
    @Route("/puzzle/retire")
    public void retire(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("puzzle/retire");
        UserEntity user = requireUser(req);
        
        // Clear session on retire
        puzzleService.clearSession(user.getUserId());
        
        com.emu.tqqserver.proto.pkg_proto.StoredData.Builder sdBuilder = storedDataService.build(user).toBuilder();
        sdBuilder.clearPuzzle();
        
        com.emu.tqqserver.proto.pkg_proto.Nocontent response = com.emu.tqqserver.proto.pkg_proto.Nocontent.newBuilder()
            .setStoredData(sdBuilder.build())
            .build();
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
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

            com.emu.tqqserver.proto.pkg_puser.Card leaderCard = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                .setId(1823880390) // Default card id for now
                .setUid((int)u.getUserId())
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

        // Add dummy helper 1
        responseBuilder.addHelpers(com.emu.tqqserver.proto.pkg_proto.ListUser.newBuilder()
            .setUid(1002)
            .setLevel(100)
            .setName("Nakano Miku")
            .setComment("Matcha soda")
            .setLastLoginTs((int)(System.currentTimeMillis() / 1000) - 3600)
            .setFriend("none")
            .setLeader(com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                .setId(1002)
                .setUid(1002)
                .setCardId(10472)
                .setCardPropertyId(104721)
                .setCardPropertyId2(104721)
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
                .build())
            .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
            .build());
            
        // Add dummy helper 2
        // responseBuilder.addHelpers(com.emu.tqqserver.proto.pkg_proto.ListUser.newBuilder()
        //     .setUid(1003)
        //     .setLevel(80)
        //     .setName("Uesugi Fuutarou")
        //     .setComment("Study!")
        //     .setLastLoginTs((int)(System.currentTimeMillis() / 1000) - 7200)
        //     .setFriend("none")
        //     .setLeader(com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
        //         .setId(1003)
        //         .setUid(1003)
        //         .setCardId(20013)
        //         .setCardPropertyId(200131)
        //         .setLevel(40)
        //         .setLevelAwake(40)
        //         .setLimitbreakRank(2)
        //         .setAwakePriority(1)
        //         .setActiveSkillLevel(3)
        //         .setPassiveSkillLevel1(3)
        //         .setKirameki(0)
        //         .setTokimeki(0)
        //         .build())
        //     .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
        //     .build());

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
