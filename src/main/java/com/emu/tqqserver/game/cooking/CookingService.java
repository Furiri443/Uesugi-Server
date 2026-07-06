package com.emu.tqqserver.game.cooking;

import com.emu.tqqserver.game.user.UserService;


import com.emu.tqqserver.db.DatabaseManager;
import com.emu.tqqserver.proto.pkg_proto.CookingTray;
import com.emu.tqqserver.proto.pkg_proto.CookingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CookingService {

    private static final Logger log = LoggerFactory.getLogger(CookingService.class);
    private final UserService userService = new UserService();

    public List<CookingTray> getTrays(long userId) {
        List<CookingTray> list = new ArrayList<>();
        long now = System.currentTimeMillis() / 1000;

        for (int index = 1; index <= 4; index++) {
            list.add(getTrayStatus(userId, index, now));
        }

        return list;
    }

    public CookingTray getTrayStatus(long userId, int trayId, long now) {
        String sql = "SELECT * FROM user_cooking WHERE user_id = ? AND tray_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, trayId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int recipeId = rs.getInt("recipe_id");
                    long finishAt = rs.getLong("finish_at");
                    int status = (now >= finishAt) ? 2 : 1; // 1 = cooking, 2 = cooked

                    com.emu.tqqserver.proto.pkg_puser.CookingTray inner =
                        com.emu.tqqserver.proto.pkg_puser.CookingTray.newBuilder()
                            .setUid((int) userId)
                            .setIndex(trayId)
                            .setUniqueId("cooking-" + userId + "-" + trayId)
                            .setCookingRecipeId(recipeId)
                            .setStatus(status)
                            .setReadiedAt((int) finishAt)
                            .build();

                    com.emu.tqqserver.proto.pkg_proto.CookingHelper helper = com.emu.tqqserver.proto.pkg_proto.CookingHelper.newBuilder()
                        .setUser(com.emu.tqqserver.proto.pkg_proto.ListUser.newBuilder()
                            .setUid(45831564)
                            .setName("Helper")
                            .setLevel(100)
                            .setPlayerTitleId(50301003)
                            .build())
                        .build();

                    return com.emu.tqqserver.proto.pkg_proto.CookingTray.newBuilder()
                        .setTray(inner)
                        .setRankGauge(10)
                        .setCanHelp(true)
                        .addHelpers(helper)
                        .build();
                }
            }
        } catch (SQLException e) {
            log.error("Failed to query tray {} for user {}", trayId, userId, e);
        }

        // Return empty tray
        com.emu.tqqserver.proto.pkg_puser.CookingTray inner =
            com.emu.tqqserver.proto.pkg_puser.CookingTray.newBuilder()
                .setUid((int) userId)
                .setIndex(trayId)
                .setUniqueId("cooking-" + userId + "-" + trayId)
                .setCookingRecipeId(0)
                .setStatus(0) // available
                .setReadiedAt(0)
                .build();

        return com.emu.tqqserver.proto.pkg_proto.CookingTray.newBuilder()
            .setTray(inner)
            .setRankGauge(0)
            .setCanHelp(false)
            .build();
    }

    public void startCooking(long userId, int trayId, int recipeId, int durationSeconds) {
        long now = System.currentTimeMillis() / 1000;
        long finishAt = now + durationSeconds;

        String sql = "INSERT OR REPLACE INTO user_cooking (user_id, tray_id, recipe_id, start_at, finish_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, trayId);
            ps.setInt(3, recipeId);
            ps.setLong(4, now);
            ps.setLong(5, finishAt);
            ps.executeUpdate();
            log.info("User {} started cooking recipe {} on tray {} for {} seconds", userId, recipeId, trayId, durationSeconds);
        } catch (SQLException e) {
            log.error("Failed to start cooking for user {}", userId, e);
        }
    }

    public boolean clearCooking(long userId, int trayId) {
        String selectSql = "SELECT * FROM user_cooking WHERE user_id = ? AND tray_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            try (PreparedStatement psSel = conn.prepareStatement(selectSql)) {
                psSel.setLong(1, userId);
                psSel.setInt(2, trayId);
                try (ResultSet rs = psSel.executeQuery()) {
                    if (rs.next()) {
                        long finishAt = rs.getLong("finish_at");
                        long now = System.currentTimeMillis() / 1000;
                        if (now >= finishAt) {
                            // Delete cooking row
                            String deleteSql = "DELETE FROM user_cooking WHERE user_id = ? AND tray_id = ?";
                            try (PreparedStatement psDel = conn.prepareStatement(deleteSql)) {
                                psDel.setLong(1, userId);
                                psDel.setInt(2, trayId);
                                psDel.executeUpdate();
                            }
                            // Give rewards
                            userService.addCoin(userId, 500);
                            userService.addJewel(userId, 10);
                            log.info("Cleared tray {} for user {}. Rewarded 500 coins and 10 jewels.", trayId, userId);
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Failed to clear cooking for user {}", userId, e);
        }
        return false;
    }
}
