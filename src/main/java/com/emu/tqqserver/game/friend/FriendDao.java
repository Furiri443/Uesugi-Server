package com.emu.tqqserver.game.friend;

import com.emu.tqqserver.db.dao.BaseDao;


import com.emu.tqqserver.db.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendDao extends BaseDao {
    private static final Logger log = LoggerFactory.getLogger(FriendDao.class);

    public List<Long> getFriends(long userId) {
        return queryList("SELECT friend_id FROM user_friends WHERE user_id = ? AND status = 1", userId);
    }

    public List<Long> getRequestsSent(long userId) {
        return queryList("SELECT friend_id FROM user_friends WHERE user_id = ? AND status = 0", userId);
    }

    public List<Long> getRequestsReceived(long userId) {
        return queryList("SELECT user_id FROM user_friends WHERE friend_id = ? AND status = 0", userId);
    }

    public void sendRequest(long userId, long targetId) {
        executeRelationUpdate("INSERT OR REPLACE INTO user_friends (user_id, friend_id, status) VALUES (?, ?, 0)", userId, targetId);
    }

    public void cancelRequest(long userId, long targetId) {
        executeRelationUpdate("DELETE FROM user_friends WHERE user_id = ? AND friend_id = ? AND status = 0", userId, targetId);
    }

    public void approveRequest(long userId, long targetId) {
        executeRelationUpdate("UPDATE user_friends SET status = 1 WHERE user_id = ? AND friend_id = ?", targetId, userId);
        executeRelationUpdate("INSERT OR REPLACE INTO user_friends (user_id, friend_id, status) VALUES (?, ?, 1)", userId, targetId);
    }

    public void rejectRequest(long userId, long targetId) {
        executeRelationUpdate("DELETE FROM user_friends WHERE user_id = ? AND friend_id = ? AND status = 0", targetId, userId);
    }

    public void deleteFriend(long userId, long targetId) {
        String sql = "DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?";
        executeRelationUpdate(sql, userId, targetId);
        executeRelationUpdate(sql, targetId, userId);
    }

    public List<Long> getBlockedUsers(long userId) {
        return queryList("SELECT blocked_user_id FROM user_blocks WHERE user_id = ?", userId);
    }

    public void blockUser(long userId, long targetId) {
        deleteFriend(userId, targetId);
        executeRelationUpdate("INSERT OR REPLACE INTO user_blocks (user_id, blocked_user_id) VALUES (?, ?)", userId, targetId);
    }

    public void unblockUser(long userId, long targetId) {
        executeRelationUpdate("DELETE FROM user_blocks WHERE user_id = ? AND blocked_user_id = ?", userId, targetId);
    }

    public boolean isBlocked(long userId, long targetId) {
        String sql = "SELECT 1 FROM user_blocks WHERE user_id = ? AND blocked_user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, targetId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            log.error("Failed to check block status between {} and {}", userId, targetId, e);
        }
        return false;
    }

    private List<Long> queryList(String sql, long id) {
        List<Long> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to query relation list with sql: {}", sql, e);
        }
        return list;
    }

    private void executeRelationUpdate(String sql, long id1, long id2) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sql)) {
            ps.setLong(1, id1);
            ps.setLong(2, id2);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed executing relation sql: {}", sql, e);
        }
    }
}
