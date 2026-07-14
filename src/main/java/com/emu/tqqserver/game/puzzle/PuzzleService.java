package com.emu.tqqserver.game.puzzle;

import com.emu.tqqserver.game.user.UserService;


import com.emu.tqqserver.db.DatabaseManager;
import com.emu.tqqserver.proto.pkg_proto.Goods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PuzzleService {

    private static final Logger log = LoggerFactory.getLogger(PuzzleService.class);
    private final UserService userService = new UserService();
    
    // In-memory session tracking for Puzzle
    private static final java.util.Map<Long, PuzzleSession> activeSessions = new java.util.concurrent.ConcurrentHashMap<>();

    public static class PuzzleSession {
        public String puid;
        public int stageId;
        public long startTime;
        public int ap;

        public PuzzleSession(String puid, int stageId, int ap) {
            this.puid = puid;
            this.stageId = stageId;
            this.startTime = System.currentTimeMillis() / 1000;
            this.ap = ap;
        }
    }

    public PuzzleSession startPuzzle(long userId, int stageId, int ap) {
        // Initialize stamina row if it doesn't exist
        initializeStamina(userId);

        int cost = ap * 10;
        // Deduct AP (Stamina)
        String sql = "UPDATE user_stamina SET current_stamina = MAX(0, current_stamina - ?) WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setInt(1, cost);
            ps.setLong(2, userId);
            ps.executeUpdate();
            
            // Create session
            String puid = java.util.UUID.randomUUID().toString();
            PuzzleSession session = new PuzzleSession(puid, stageId, ap);
            activeSessions.put(userId, session);
            
            log.info("User {} started stage {}. Deducted {} AP. PUID: {}", userId, stageId, cost, puid);
            return session;
        } catch (SQLException e) {
            log.error("Failed to deduct stamina for user {}", userId, e);
            return null;
        }
    }

    public PuzzleSession getActiveSession(long userId) {
        return activeSessions.get(userId);
    }

    public void clearSession(long userId) {
        activeSessions.remove(userId);
    }

    public List<Goods> clearPuzzle(long userId, int stageId, int score, int clearType) {
        List<Goods> rewards = new ArrayList<>();

        boolean isFirstClear = isFirstClear(userId, stageId);
        boolean isFirstRankS = false;
        
        // Resolve Stage Data
        int expReward = 10;
        int cardExpReward = 50;
        int sScore = 120000, bScore = 70000;
        int clearRewardId = 0, firstClearRewardId = 0, rankSRewardId = 0, drop1 = 0, drop2 = 0, drop3 = 0;

        com.emu.tqqserver.data.resources.StageDef mainStage = com.emu.tqqserver.data.GameData.getStageDataTable().get(stageId);
        if (mainStage != null) {
            expReward = mainStage.getExp(); cardExpReward = mainStage.getCardExp();
            sScore = mainStage.getRankScoreS(); bScore = mainStage.getRankScoreB();
            clearRewardId = mainStage.getClearRewardId(); firstClearRewardId = mainStage.getFirstclearRewardId();
            rankSRewardId = mainStage.getRankSRewardId(); drop1 = mainStage.getDropReward1Id();
            drop2 = mainStage.getDropReward2Id(); drop3 = mainStage.getDropReward3Id();
        } else {
            com.emu.tqqserver.data.resources.EncoreStageDef encoreStage = com.emu.tqqserver.data.GameData.getEncoreStageDataTable().get(stageId);
            if (encoreStage != null) {
                expReward = encoreStage.getExp(); cardExpReward = 0;
                sScore = encoreStage.getRankScoreS(); bScore = encoreStage.getRankScoreB();
            }
        }

        // Determine rank and stars based on master data
        int stars = 1;
        if (score >= sScore) {
            stars = 3;
            if (!hasRankS(userId, stageId)) {
                isFirstRankS = true;
            }
        } else if (score >= bScore) {
            stars = 2;
        }

        // If clearType > 0, it might override stars from client
        if (clearType > 0) {
            stars = clearType;
        }

        // Apply AP multiplier if applicable
        PuzzleSession session = getActiveSession(userId);
        int ap = (session != null && session.ap > 0) ? session.ap : 1;
        expReward *= ap;
        cardExpReward *= ap;

        // 1. Record stage clear progress
        recordStageClear(userId, stageId, score, stars);

        if (expReward > 0) addPlayerExp(userId, expReward);

        // Calculate Rewards
        addRewardFromGroupId(userId, clearRewardId, ap, rewards);
        if (isFirstClear) addRewardFromGroupId(userId, firstClearRewardId, 1, rewards);
        if (isFirstRankS) addRewardFromGroupId(userId, rankSRewardId, 1, rewards);
        
        // Random Drops (simplified logic)
        addRewardFromGroupId(userId, drop1, ap, rewards);

        log.info("User {} cleared stage {} with score {} ({} stars). AP: {}, Exp: {}", userId, stageId, score, stars, ap, expReward);
        return rewards;
    }

    private void addRewardFromGroupId(long userId, int rewardGroupId, int multiplier, List<Goods> outRewards) {
        if (rewardGroupId <= 0) return;
        List<com.emu.tqqserver.data.resources.RewardDef> defs = com.emu.tqqserver.data.GameData.getRewards(rewardGroupId);
        for (com.emu.tqqserver.data.resources.RewardDef r : defs) {
            int quantity = r.getQuantity() * multiplier;
            int type = r.getType();
            int targetId = r.getRewardId();
            
            outRewards.add(Goods.newBuilder()
                .setCategory(type)
                .setTargetId(targetId)
                .setQuantity(quantity)
                .setRewardId(r.getId())
                .setRewardSeqId(r.getSeqId())
                .build());

            if (type == 1 && targetId == 1001) {
                userService.addCoin(userId, quantity);
            } else if (type == 3 && targetId == 9000050) {
                userService.addJewel(userId, quantity);
            }
        }
    }

    private boolean isFirstClear(long userId, int stageId) {
        String sql = "SELECT clear_count FROM user_stages WHERE user_id = ? AND stage_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, stageId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("clear_count") == 0;
                }
            }
        } catch (SQLException e) {}
        return true;
    }

    private boolean hasRankS(long userId, int stageId) {
        String sql = "SELECT stars FROM user_stages WHERE user_id = ? AND stage_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, stageId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stars") >= 3;
                }
            }
        } catch (SQLException e) {}
        return false;
    }

    private void initializeStamina(long userId) {
        String insertSql = "INSERT OR IGNORE INTO user_stamina (user_id, current_stamina, max_stamina, last_recover_at) VALUES (?, 60, 60, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, insertSql)) {
            ps.setLong(1, userId);
            ps.setLong(2, System.currentTimeMillis() / 1000);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to initialize stamina for user {}", userId, e);
        }
    }

    private void recordStageClear(long userId, int stageId, int score, int stars) {
        String sql = "INSERT INTO user_stages (user_id, stage_id, stars, best_score, clear_count, cleared_at) VALUES (?, ?, ?, ?, 1, ?) " +
                     "ON CONFLICT(user_id, stage_id) DO UPDATE SET " +
                     "best_score = MAX(best_score, EXCLUDED.best_score), " +
                     "stars = MAX(stars, EXCLUDED.stars), " +
                     "clear_count = clear_count + 1, " +
                     "cleared_at = EXCLUDED.cleared_at";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, stageId);
            ps.setInt(3, stars);
            ps.setInt(4, score);
            ps.setLong(5, System.currentTimeMillis() / 1000);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to record stage clear for user {}", userId, e);
        }
    }

    private void addPlayerExp(long userId, int expGained) {
        String selectSql = "SELECT rank, exp FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            int currentRank = 1;
            int currentExp = 0;

            try (PreparedStatement psSel = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, selectSql)) {
                psSel.setLong(1, userId);
                try (ResultSet rs = psSel.executeQuery()) {
                    if (rs.next()) {
                        currentRank = rs.getInt("rank");
                        currentExp = rs.getInt("exp");
                    }
                }
            }

            int newExp = currentExp + expGained;
            int newRank = 1;
            int cumulativeExp = 0;

            com.emu.tqqserver.data.resources.PlayerDef playerDef = com.emu.tqqserver.data.GameData.getPlayerDataTable().get(newRank);
            while (playerDef != null && newExp >= cumulativeExp + playerDef.getExp()) {
                if (playerDef.getExp() <= 0) {
                    break;
                }
                cumulativeExp += playerDef.getExp();
                newRank++;
                playerDef = com.emu.tqqserver.data.GameData.getPlayerDataTable().get(newRank);
            }

            String updateSql = "UPDATE users SET rank = ?, exp = ? WHERE user_id = ?";
            try (PreparedStatement psUpd = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, updateSql)) {
                psUpd.setInt(1, newRank);
                psUpd.setInt(2, newExp);
                psUpd.setLong(3, userId);
                psUpd.executeUpdate();
            }

            if (newRank > currentRank) {
                log.info("User {} ranked up! New rank: {}", userId, newRank);
                // Recover full stamina on level up
                String recoverSql = "UPDATE user_stamina SET current_stamina = max_stamina WHERE user_id = ?";
                try (PreparedStatement psRec = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, recoverSql)) {
                    psRec.setLong(1, userId);
                    psRec.executeUpdate();
                }
            }

        } catch (SQLException e) {
            log.error("Failed to add player experience for user {}", userId, e);
        }
    }
}
