package com.emu.tqqserver.game.present;

import com.emu.tqqserver.game.user.UserService;


import com.emu.tqqserver.db.DatabaseManager;
import com.emu.tqqserver.proto.pkg_pmisc.Present;
import com.emu.tqqserver.proto.pkg_proto.Goods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PresentService {

    private static final Logger log = LoggerFactory.getLogger(PresentService.class);
    private final UserService userService = new UserService();

    public List<Present> getActivePresents(long userId) {
        List<Present> list = new ArrayList<>();
        String sql = "SELECT * FROM user_presents WHERE user_id = ? AND received = 0";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get active presents for user: {}", userId, e);
        }
        return list;
    }

    public void addPresent(long userId, int category, int targetId, int quantity, String text) {
        String sql = "INSERT INTO user_presents (user_id, category, target_id, quantity, text, received) VALUES (?, ?, ?, ?, ?, 0)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, category);
            ps.setInt(3, targetId);
            ps.setInt(4, quantity);
            ps.setString(5, text);
            ps.executeUpdate();
            log.info("Added present to user {}: category={}, targetId={}, quantity={}", userId, category, targetId, quantity);
        } catch (SQLException e) {
            log.error("Failed to add present to user: {}", userId, e);
        }
    }

    public List<Goods> claimPresents(long userId, List<Integer> presentIds) {
        List<Goods> goodsClaimed = new ArrayList<>();
        if (presentIds == null || presentIds.isEmpty()) {
            return goodsClaimed;
        }

        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try {
                for (int presentId : presentIds) {
                    // Check if present exists and is unclaimed
                    String selectSql = "SELECT * FROM user_presents WHERE present_id = ? AND user_id = ? AND received = 0";
                    try (PreparedStatement psSel = conn.prepareStatement(selectSql)) {
                        psSel.setInt(1, presentId);
                        psSel.setLong(2, userId);
                        try (ResultSet rs = psSel.executeQuery()) {
                            if (rs.next()) {
                                int category = rs.getInt("category");
                                int targetId = rs.getInt("target_id");
                                int quantity = rs.getInt("quantity");

                                // Credit rewards
                                creditReward(conn, userId, category, targetId, quantity);

                                // Update present received status
                                String updateSql = "UPDATE user_presents SET received = 1 WHERE present_id = ?";
                                try (PreparedStatement psUpd = conn.prepareStatement(updateSql)) {
                                    psUpd.setInt(1, presentId);
                                    psUpd.executeUpdate();
                                }

                                // Add to claimed goods list
                                Goods goods = Goods.newBuilder()
                                    .setCategory(category)
                                    .setTargetId(targetId)
                                    .setQuantity(quantity)
                                    .build();
                                goodsClaimed.add(goods);
                            }
                        }
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error("Failed to claim presents for user: {}", userId, e);
        }

        return goodsClaimed;
    }

    private void creditReward(Connection conn, long userId, int category, int targetId, int quantity) throws SQLException {
        if (category == 1) {
            // Coins
            String sql = "UPDATE users SET coin = coin + ? WHERE user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, quantity);
                ps.setLong(2, userId);
                ps.executeUpdate();
            }
        } else if (category == 2) {
            // Jewels/Ruby
            String sql = "UPDATE users SET jewel = jewel + ? WHERE user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, quantity);
                ps.setLong(2, userId);
                ps.executeUpdate();
            }
        } else if (category == 3) {
            // Cards
            String sql = "INSERT INTO user_cards (user_id, card_id) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, userId);
                ps.setInt(2, targetId);
                ps.executeUpdate();
            }
        } else if (category == 4) {
            // Items
            String sql = "INSERT INTO user_items (user_id, item_id, quantity) VALUES (?, ?, ?) " +
                         "ON CONFLICT(user_id, item_id) DO UPDATE SET quantity = quantity + EXCLUDED.quantity";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, userId);
                ps.setInt(2, targetId);
                ps.setInt(3, quantity);
                ps.executeUpdate();
            }
        }
    }

    private Present mapRow(ResultSet rs) throws SQLException {
        return Present.newBuilder()
            .setId(rs.getInt("present_id"))
            .setUid((int) rs.getLong("user_id"))
            .setStatus(0)
            .setRewardType(rs.getInt("category"))
            .setRewardId(rs.getInt("target_id"))
            .setQuantity(rs.getInt("quantity"))
            .setMessage(rs.getString("text") != null ? rs.getString("text") : "お祝い品")
            .setReason("運営プレゼント")
            .setExpireDate((int) (System.currentTimeMillis() / 1000 + 86400 * 30)) // 30 days default
            .build();
    }
}
