package com.emu.tqqserver.game.user;

import com.emu.tqqserver.db.DatabaseManager;
import com.emu.tqqserver.db.dao.BaseDao;
import com.emu.tqqserver.proto.pkg_puser.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnitDao extends BaseDao {
    private static final Logger log = LoggerFactory.getLogger(UnitDao.class);

    public void createDefaultUnit(long userId, List<Integer> defaultUnitCards, List<CardEntity> userCards) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, 
                     "INSERT INTO user_units (user_id, idx, unit_name, member1, member2, member3, member4, member5, card1, card2, card3, card4, card5) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            long[] cardIds = new long[5];
            for (int i = 0; i < 5; i++) {
                int templateId = i < defaultUnitCards.size() ? defaultUnitCards.get(i) : 0;
                cardIds[i] = userCards.stream().filter(c -> c.getCardId() == templateId).map(CardEntity::getId).findFirst().orElse(0L);
            }

            ps.setLong(1, userId);
            ps.setInt(2, 1);
            ps.setString(3, "Team 1");
            ps.setInt(4, 1);
            ps.setInt(5, 2);
            ps.setInt(6, 3);
            ps.setInt(7, 4);
            ps.setInt(8, 5);
            ps.setLong(9, cardIds[0]);
            ps.setLong(10, cardIds[1]);
            ps.setLong(11, cardIds[2]);
            ps.setLong(12, cardIds[3]);
            ps.setLong(13, cardIds[4]);

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to create default unit for user {}", userId, e);
        }
    }

    /** Save or update a unit */
    public void saveUnit(long userId, int idx, String name, int[] members, long[] cards) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, 
                     "INSERT OR REPLACE INTO user_units (user_id, idx, unit_name, member1, member2, member3, member4, member5, card1, card2, card3, card4, card5) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            ps.setLong(1, userId);
            ps.setInt(2, idx);
            ps.setString(3, name);
            for (int i = 0; i < 5; i++) ps.setInt(4 + i, members[i]);
            for (int i = 0; i < 5; i++) ps.setLong(9 + i, cards[i]);

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to save unit {} for user {}", idx, userId, e);
        }
    }

    /** Get all units for a user as Protobuf Builder objects */
    public List<Unit> getUnits(long userId) {
        List<Unit> units = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, "SELECT * FROM user_units WHERE user_id = ? ORDER BY idx ASC")) {

            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Unit unit = Unit.newBuilder()
                            .setUid((int) userId)
                            .setIdx(rs.getInt("idx"))
                            .setUnitName(rs.getString("unit_name"))
                            .setMemberId1(rs.getInt("member1"))
                            .setMemberId2(rs.getInt("member2"))
                            .setMemberId3(rs.getInt("member3"))
                            .setMemberId4(rs.getInt("member4"))
                            .setMemberId5(rs.getInt("member5"))
                            .setCardId1(rs.getLong("card1"))
                            .setCardId2(rs.getLong("card2"))
                            .setCardId3(rs.getLong("card3"))
                            .setCardId4(rs.getLong("card4"))
                            .setCardId5(rs.getLong("card5"))
                            .build();
                    units.add(unit);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get units for user {}", userId, e);
        }
        return units;
    }
}
