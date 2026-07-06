package com.emu.tqqserver.game.home;

import com.emu.tqqserver.db.DatabaseManager;
import com.emu.tqqserver.proto.pkg_puser.HomeActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeService {
    private static final Logger log = LoggerFactory.getLogger(HomeService.class);

    public void updateHomeBackground(long userId, int backgroundId) {
        String sql = "UPDATE users SET home_background_id = ? WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, backgroundId);
            ps.setLong(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to update home background", e);
        }
    }

    public List<HomeActor> getHomeActors(long userId) {
        List<HomeActor> actors = new ArrayList<>();
        String sql = "SELECT character_id, model_kind_id, clothes_id, position FROM user_home_actors WHERE user_id = ? ORDER BY character_id ASC";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HomeActor actor = HomeActor.newBuilder()
                            .setUid((int) userId)
                            .setCharacterId(rs.getInt("character_id"))
                            .setModelKindId(rs.getInt("model_kind_id"))
                            .setClothesId(rs.getInt("clothes_id"))
                            .setPosition(rs.getInt("position"))
                            .build();
                    actors.add(actor);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get home actors", e);
        }
        
        if (actors.isEmpty()) {
            int[] defaultClothes = {0, 11, 142, 153, 144, 145};
            // Default quintuplets
            for (int i = 1; i <= 5; i++) {
                actors.add(HomeActor.newBuilder()
                        .setUid((int) userId)
                        .setCharacterId(i)
                        .setModelKindId(1)
                        .setClothesId(defaultClothes[i])
                        .setPosition(i - 1)
                        .build());
            }
        }
        
        return actors;
    }

    public void saveHomeActors(long userId, List<HomeActor> actors) {
        String sql = "INSERT OR REPLACE INTO user_home_actors (user_id, character_id, model_kind_id, clothes_id, position) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (HomeActor actor : actors) {
                ps.setLong(1, userId);
                ps.setInt(2, actor.getCharacterId());
                ps.setInt(3, actor.getModelKindId());
                ps.setInt(4, actor.getClothesId());
                ps.setInt(5, actor.getPosition());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            log.error("Failed to save home actors", e);
        }
    }
}
