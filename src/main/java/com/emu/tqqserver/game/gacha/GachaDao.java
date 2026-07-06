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
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, gachaId);
            ps.setInt(3, cardId);
            ps.setInt(4, rarity);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to insert gacha log for user {} card {}", userId, cardId, e);
        }
    }
}
