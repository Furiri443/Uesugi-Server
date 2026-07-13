package com.emu.tqqserver.game.puzzle.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.PuzzleResult;
import com.emu.tqqserver.proto.pkg_proto.Goods;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import com.emu.tqqserver.game.puzzle.PuzzleService;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;

import java.util.List;

@Route("/puzzle/clear")
public class PuzzleClearHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final PuzzleService puzzleService = new PuzzleService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
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
        stageId = pSession != null ? pSession.stageId : 1001;

        boolean isNewRecord = false;
        try {
            java.sql.Connection conn = com.emu.tqqserver.db.DatabaseManager.getInstance().getConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement("SELECT best_score FROM user_stages WHERE user_id = ? AND stage_id = ?");
            ps.setLong(1, user.getUserId());
            ps.setInt(2, stageId);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int oldBest = rs.getInt("best_score");
                if (score > oldBest) isNewRecord = true;
            } else {
                isNewRecord = true;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {}

        List<Goods> rewards = puzzleService.clearPuzzle(userId, stageId, score, clearType);
        
        // Fallback removed to prevent client NullReferenceException on empty reward_id
        
        // Reload user to get updated exp/level from clearPuzzle
        user = userService.findById(userId);
        int playerLevel = user.getRank();
        int playerExp = user.getExp();
        // Clear session
        puzzleService.clearSession(userId);

        com.emu.tqqserver.game.card.CardService cardService = new com.emu.tqqserver.game.card.CardService();
        List<com.emu.tqqserver.game.user.CardEntity> cards = cardService.getUserCards(userId);
        
        com.emu.tqqserver.game.user.UnitDao unitDao = new com.emu.tqqserver.game.user.UnitDao();
        List<com.emu.tqqserver.proto.pkg_puser.Unit> units = unitDao.getUnits(userId);
        com.emu.tqqserver.proto.pkg_puser.Unit activeUnit = null;
        for (com.emu.tqqserver.proto.pkg_puser.Unit u : units) {
            if (u.getIdx() == 1) {
                activeUnit = u;
                break;
            }
        }
        if (activeUnit == null && !units.isEmpty()) activeUnit = units.get(0);
        
        List<Long> activeCardIds = new java.util.ArrayList<>();
        if (activeUnit != null) {
            activeCardIds.add(activeUnit.getCardId1());
            activeCardIds.add(activeUnit.getCardId2());
            activeCardIds.add(activeUnit.getCardId3());
            activeCardIds.add(activeUnit.getCardId4());
            activeCardIds.add(activeUnit.getCardId5());
        }

        int cardExpReward = 50;
        com.emu.tqqserver.data.resources.StageDef mainStage = com.emu.tqqserver.data.GameData.getStageDataTable().get(stageId);
        if (mainStage != null) {
            cardExpReward = mainStage.getCardExp();
        } else {
            com.emu.tqqserver.data.resources.EncoreStageDef encoreStage = com.emu.tqqserver.data.GameData.getEncoreStageDataTable().get(stageId);
            if (encoreStage != null) {
                cardExpReward = 0;
            }
        }
        int ap = (pSession != null && pSession.ap > 0) ? pSession.ap : 1;
        cardExpReward *= ap;

        List<com.emu.tqqserver.proto.pkg_proto.PuzzleCard> puzzleCards = new java.util.ArrayList<>();
        for (Long cardId : activeCardIds) {
            if (cardId <= 0) continue;
            com.emu.tqqserver.game.user.CardEntity c = null;
            for (com.emu.tqqserver.game.user.CardEntity entity : cards) {
                if (entity.getId() == cardId) {
                    c = entity;
                    break;
                }
            }
            if (c == null) continue;
            
            int newExp = c.getExp() + cardExpReward;
            int newLevel = c.getLevel();
            com.emu.tqqserver.data.resources.CardDef cardDef = com.emu.tqqserver.data.GameData.getCardDataTable().get(c.getCardId());
            if (cardDef != null) {
                int levelGrowthId = cardDef.getLevelGrowthId();
                for (com.emu.tqqserver.data.resources.LevelGrowthDef g : com.emu.tqqserver.data.GameData.getLevelGrowthDataTable().values()) {
                    if (g.getId() == levelGrowthId && newExp >= g.getValue() && g.getLevel() > newLevel) {
                        newLevel = g.getLevel();
                    }
                }
            }
            try {
                java.sql.Connection conn = com.emu.tqqserver.db.DatabaseManager.getInstance().getConnection();
                java.sql.PreparedStatement ps = conn.prepareStatement("UPDATE user_cards SET exp = ?, level = ? WHERE id = ?");
                ps.setInt(1, newExp);
                ps.setInt(2, newLevel);
                ps.setLong(3, c.getId());
                ps.executeUpdate();
                ps.close();
                conn.close();
            } catch (Exception e) {
                log.error("Failed to update card exp", e);
            }

            int propertyId = c.getCardId() * 10 + 1;
            com.emu.tqqserver.proto.pkg_puser.Card puserCard = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                .setId(c.getId())
                .setUid(userId.intValue())
                .setCardId(c.getCardId())
                .setCardPropertyId(propertyId)
                .setCardPropertyId2(propertyId)
                .setExp(c.getExp()) // Send old exp as per client design so it can animate the exp bar
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
                .setBaseExp(cardExpReward)
                .setRelationshipExp(cardExpReward)
                .build());
        }
        
        log.info("puzzleCards size: " + puzzleCards.size());

        com.emu.tqqserver.proto.pkg_proto.StoredData.Builder sdBuilder = storedDataService.build(user).toBuilder();
        if (pSession != null) {
            sdBuilder.setPuzzle(com.emu.tqqserver.proto.pkg_puser.Puzzle.newBuilder()
                .setPuid(pSession.puid)
                .setStageId(stageId)
                .setStatus(1)
                .build());
        }

        com.emu.tqqserver.proto.pkg_puser.Card leaderCard = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
            .setId(1823880390)
            .setUid(10003)
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
            .setUid(10003)
            .setLevel(75)
            .setName("三玖ちゃん")
            .setComment("助っ人です！よろしくね")
            .setPlayerTitleId(50701001)
            .setPlayerTitleTargetId(1)
            .setLastLoginTs((int) (System.currentTimeMillis() / 1000))
            .setFriend("none")
            .setLeader(leaderCard)
            .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
            .build();

        com.emu.tqqserver.proto.pkg_proto.EventPuzzleResult eventResult = com.emu.tqqserver.proto.pkg_proto.EventPuzzleResult.newBuilder()
            .setTotalEp(0)
            .setRank(0)
            .build();

        int currentSeasonId = 1;
        for (com.emu.tqqserver.data.resources.SeasonDef def : com.emu.tqqserver.data.GameData.getSeasonDataTable().values()) {
            if (def.getId() > currentSeasonId) currentSeasonId = def.getId();
        }

        int bondsScore = 0;
        String sqlBonds = "SELECT SUM(likability) AS total FROM user_members WHERE user_id = ?";
        try (java.sql.Connection conn = com.emu.tqqserver.db.DatabaseManager.getInstance().getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sqlBonds)) {
            ps.setLong(1, userId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bondsScore = rs.getInt("total");
                }
            }
        } catch (java.sql.SQLException e) {}

        com.emu.tqqserver.proto.pkg_proto.BondsSeasonRanking bondsSeasonRanking = com.emu.tqqserver.proto.pkg_proto.BondsSeasonRanking.newBuilder()
            .setSeasonId(currentSeasonId)
            .setScore(bondsScore)
            .setRank(bondsScore > 0 ? 1000 : 0)
            .build();

        PuzzleResult response = PuzzleResult.newBuilder()
            .setStoredData(sdBuilder.build())
            .setNewRecord(isNewRecord)
            .setScore(score)
            .addAllStageClearReward(rewards)
            .addAllStageFirstClearReward(new java.util.ArrayList<>())
            .addAllStageRankSReward(new java.util.ArrayList<>())
            .addAllStageDropReward(new java.util.ArrayList<>())
            .addAllChapterPerfectReward(new java.util.ArrayList<>())
            .addAllChapterCompleteReward(new java.util.ArrayList<>())
            .addAllUnit(puzzleCards)
            .addAllPuzzleTreasure(new java.util.ArrayList<>())
            .addAllAppliedStageBoostCampaign(new java.util.ArrayList<>())
            .addAllAppliedCampaign(new java.util.ArrayList<>())
            .setPlayerLevel(user.getRank() > 0 ? user.getRank() : 1)
            .setPlayerExp(user.getExp())
            .setHelper(helper)
            .setBondsSeasonRanking(bondsSeasonRanking)
            .build();
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }
}
