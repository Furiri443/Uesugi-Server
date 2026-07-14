package com.emu.tqqserver.game.user;

import com.emu.tqqserver.db.DatabaseManager;
import com.emu.tqqserver.db.dao.BaseDao;
import com.emu.tqqserver.proto.pkg_puser.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDao extends BaseDao {
    private static final Logger log = LoggerFactory.getLogger(MemberDao.class);

    /** Initialize members for a new user */
    public void initializeMembers(long userId) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, 
                     "INSERT OR IGNORE INTO user_members (user_id, member_id, dear_level, likability) VALUES (?, ?, 1, 1)")) {
            for (int i = 1; i <= 5; i++) {
                ps.setLong(1, userId);
                ps.setInt(2, i);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            log.error("Failed to initialize members for user {}", userId, e);
        }
    }

    /** Save or update a member's affection */
    public void updateMember(long userId, int memberId, int dearLevel, int likability) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, 
                     "UPDATE user_members SET dear_level = ?, likability = ? WHERE user_id = ? AND member_id = ?")) {
            ps.setInt(1, dearLevel);
            ps.setInt(2, likability);
            ps.setLong(3, userId);
            ps.setInt(4, memberId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to update member {} for user {}", memberId, userId, e);
        }
    }

    /** Get all members for a user as Protobuf Builder objects */
    public List<Member> getMembers(long userId) {
        List<Member> members = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, "SELECT * FROM user_members WHERE user_id = ? ORDER BY member_id ASC")) {

            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Member member = Member.newBuilder()
                            .setUid((int) userId)
                            .setMemberId(rs.getInt("member_id"))
                            .setDearlevel(rs.getInt("dear_level"))
                            .build();
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get members for user {}", userId, e);
        }
        return members;
    }
}
