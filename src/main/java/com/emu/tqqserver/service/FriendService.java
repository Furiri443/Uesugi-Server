package com.emu.tqqserver.service;

import com.emu.tqqserver.db.dao.FriendDao;
import com.emu.tqqserver.model.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FriendService {

    private static final Logger log = LoggerFactory.getLogger(FriendService.class);
    private final UserService userService = new UserService();
    private final FriendDao friendDao = new FriendDao();

    public List<UserEntity> getFriends(long userId) {
        return loadUsers(friendDao.getFriends(userId));
    }

    public List<UserEntity> getRequestsSent(long userId) {
        return loadUsers(friendDao.getRequestsSent(userId));
    }

    public List<UserEntity> getRequestsReceived(long userId) {
        return loadUsers(friendDao.getRequestsReceived(userId));
    }

    public void sendRequest(long userId, long targetId) {
        friendDao.sendRequest(userId, targetId);
    }

    public void cancelRequest(long userId, long targetId) {
        friendDao.cancelRequest(userId, targetId);
    }

    public void approveRequest(long userId, long targetId) {
        friendDao.approveRequest(userId, targetId);
    }

    public void rejectRequest(long userId, long targetId) {
        friendDao.rejectRequest(userId, targetId);
    }

    public void deleteFriend(long userId, long targetId) {
        friendDao.deleteFriend(userId, targetId);
    }

    // ── Block methods ────────────────────────────────────────────────────────

    public List<UserEntity> getBlockedUsers(long userId) {
        return loadUsers(friendDao.getBlockedUsers(userId));
    }

    public void blockUser(long userId, long targetId) {
        friendDao.blockUser(userId, targetId);
    }

    public void unblockUser(long userId, long targetId) {
        friendDao.unblockUser(userId, targetId);
    }

    public boolean isBlocked(long userId, long targetId) {
        return friendDao.isBlocked(userId, targetId);
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private List<UserEntity> loadUsers(List<Long> uids) {
        List<UserEntity> list = new ArrayList<>();
        for (Long uid : uids) {
            UserEntity entity = userService.findById(uid);
            if (entity != null) {
                list.add(entity);
            }
        }
        return list;
    }
}
