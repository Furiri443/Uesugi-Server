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

    public boolean startPuzzle(long userId, int stageId) {
        // Initialize stamina row if it doesn't exist
        initializeStamina(userId);

        // Deduct 10 AP
        String sql = "UPDATE user_stamina SET current_stamina = MAX(0, current_stamina - 10) WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.executeUpdate();
            log.info("User {} started stage {}. Deducted 10 AP.", userId, stageId);
            return true;
        } catch (SQLException e) {
            log.error("Failed to deduct stamina for user {}", userId, e);
            return false;
        }
    }

    public List<Goods> clearPuzzle(long userId, int stageId, int score, int stars) {
        List<Goods> rewards = new ArrayList<>();

        // 1. Record stage clear progress
        recordStageClear(userId, stageId, score, stars);

        // 2. Add rewards (e.g. 500 coins and 20 exp)
        userService.addCoin(userId, 500);
        rewards.add(Goods.newBuilder().setCategory(1).setTargetId(0).setQuantity(500).build());

        // Add 5 jewels as drop reward
        userService.addJewel(userId, 5);
        rewards.add(Goods.newBuilder().setCategory(2).setTargetId(0).setQuantity(5).build());

        // Add 25 Exp to player and check rank up
        addPlayerExp(userId, 25);

        log.info("User {} cleared stage {} with score {}. Awarded 500 coins, 5 jewels, 25 exp.", userId, stageId, score);
        return rewards;
    }

    private void initializeStamina(long userId) {
        String insertSql = "INSERT OR IGNORE INTO user_stamina (user_id, current_stamina, max_stamina, last_recover_at) VALUES (?, 60, 60, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql)) {
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
             PreparedStatement ps = conn.prepareStatement(sql)) {
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

            try (PreparedStatement psSel = conn.prepareStatement(selectSql)) {
                psSel.setLong(1, userId);
                try (ResultSet rs = psSel.executeQuery()) {
                    if (rs.next()) {
                        currentRank = rs.getInt("rank");
                        currentExp = rs.getInt("exp");
                    }
                }
            }

            int newExp = currentExp + expGained;
            // Level up threshold: level * 100 exp
            int threshold = currentRank * 100;
            int newRank = currentRank;

            while (newExp >= threshold) {
                newExp -= threshold;
                newRank++;
                threshold = newRank * 100;
            }

            String updateSql = "UPDATE users SET rank = ?, exp = ? WHERE user_id = ?";
            try (PreparedStatement psUpd = conn.prepareStatement(updateSql)) {
                psUpd.setInt(1, newRank);
                psUpd.setInt(2, newExp);
                psUpd.setLong(3, userId);
                psUpd.executeUpdate();
            }

            if (newRank > currentRank) {
                log.info("User {} ranked up! New rank: {}", userId, newRank);
                // Recover full stamina on level up
                String recoverSql = "UPDATE user_stamina SET current_stamina = max_stamina WHERE user_id = ?";
                try (PreparedStatement psRec = conn.prepareStatement(recoverSql)) {
                    psRec.setLong(1, userId);
                    psRec.executeUpdate();
                }
            }

        } catch (SQLException e) {
            log.error("Failed to add player experience for user {}", userId, e);
        }
    }
}
