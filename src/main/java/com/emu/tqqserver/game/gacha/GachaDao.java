package com.emu.tqqserver.game.gacha;

import com.emu.tqqserver.db.DatabaseManager;
import com.emu.tqqserver.db.dao.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GachaDao extends BaseDao {
    private static final Logger log = LoggerFactory.getLogger(GachaDao.class);

    public void logGacha(long userId, int gachaId, int cardId, int rarity) {
        String sql = "INSERT INTO gacha_log (user_id, gacha_id, card_id, rarity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, gachaId);
            ps.setInt(3, cardId);
            ps.setInt(4, rarity);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to insert gacha log for user {} card {}", userId, cardId, e);
        }
    }

    public com.emu.tqqserver.proto.pkg_pmisc.GachaHistory getGachaHistory(long userId, int gachaId) {
        String sql = "SELECT total_cnt, free_roll_used, limit_cnt FROM user_gacha_history WHERE user_id = ? AND gacha_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, gachaId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return com.emu.tqqserver.proto.pkg_pmisc.GachaHistory.newBuilder()
                        .setUid((int) userId)
                        .setGachaId(gachaId)
                        .setTotalCnt(rs.getInt("total_cnt"))
                        .setSingleCnt(rs.getInt("free_roll_used")) // Store free_roll_used in single_cnt for DB tracking (transient)
                        .setLimitCnt(rs.getInt("limit_cnt"))
                        .build();
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get gacha history for user {}", userId, e);
        }
        return com.emu.tqqserver.proto.pkg_pmisc.GachaHistory.newBuilder()
            .setUid((int) userId)
            .setGachaId(gachaId)
            .setTotalCnt(0)
            .setLimitCnt(0)
            .build();
    }

    public int getPendingChoiceCount(long userId, int gachaId) {
        String sql = "SELECT pending_choice_cnt FROM user_gacha_history WHERE user_id = ? AND gacha_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, gachaId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            // Ignore if column doesn't exist
        }
        return 0;
    }

    public void incrementPendingChoiceCount(long userId, int gachaId) {
        String sql = "UPDATE user_gacha_history SET pending_choice_cnt = pending_choice_cnt + 1 WHERE user_id = ? AND gacha_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, gachaId);
            ps.executeUpdate();
        } catch (SQLException e) {}
    }

    public void decrementPendingChoiceCount(long userId, int gachaId) {
        String sql = "UPDATE user_gacha_history SET pending_choice_cnt = MAX(0, pending_choice_cnt - 1) WHERE user_id = ? AND gacha_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, gachaId);
            ps.executeUpdate();
        } catch (SQLException e) {}
    }

    public void incrementRollCount(long userId, int gachaId, int draws) {
        String sql = "INSERT INTO user_gacha_history (user_id, gacha_id, total_cnt, limit_cnt, pending_choice_cnt) VALUES (?, ?, ?, ?, 0) " +
                     "ON CONFLICT(user_id, gacha_id) DO UPDATE SET total_cnt = total_cnt + ?, limit_cnt = limit_cnt + ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, gachaId);
            ps.setInt(3, draws);
            ps.setInt(4, draws);
            ps.setInt(5, draws);
            ps.setInt(6, draws);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to update gacha history for user {}", userId, e);
        }
    }

    public void resetLimitCount(long userId, int gachaId) {
        String sql = "UPDATE user_gacha_history SET limit_cnt = 0 WHERE user_id = ? AND gacha_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, gachaId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to reset limit_cnt for user {}", userId, e);
        }
    }

    public void markFreeRollUsed(long userId, int gachaId) {
        String sql = "INSERT INTO user_gacha_history (user_id, gacha_id, free_roll_used) VALUES (?, ?, 1) " +
                     "ON CONFLICT(user_id, gacha_id) DO UPDATE SET free_roll_used = 1";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, gachaId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to mark free roll used for user {}", userId, e);
        }
    }

    public java.util.List<com.emu.tqqserver.proto.pkg_pmisc.GachaHistory> getAllHistory(long userId) {
        java.util.List<com.emu.tqqserver.proto.pkg_pmisc.GachaHistory> list = new java.util.ArrayList<>();
        String sql = "SELECT gacha_id, total_cnt, limit_cnt FROM user_gacha_history WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(com.emu.tqqserver.proto.pkg_pmisc.GachaHistory.newBuilder()
                        .setUid((int) userId)
                        .setGachaId(rs.getInt("gacha_id"))
                        .setTotalCnt(rs.getInt("total_cnt"))
                        .setLimitCnt(rs.getInt("limit_cnt"))
                        .build());
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get all gacha history for user {}", userId, e);
        }
        return list;
    }
}
