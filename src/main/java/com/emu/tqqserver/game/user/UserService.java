package com.emu.tqqserver.game.user;

import com.emu.tqqserver.game.user.UserDao;
import com.emu.tqqserver.game.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.emu.tqqserver.db.DatabaseManager;


/**
 * User account management service.
 * Handles business logic, delegates data access to UserDao.
 */
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserDao userDao = new UserDao();
    private final UnitDao unitDao = new UnitDao();
    private final MemberDao memberDao = new MemberDao();

    /** Find existing user by userId, or create a placeholder if not found */
    public UserEntity findOrCreate(long userId, String platformType) {
        UserEntity user = userDao.findById(userId);
        if (user != null) {
            return user;
        }
        return userDao.create(platformType, null, null);
    }

    /** Find or create a guest user by device_id */
    public UserEntity findOrCreateGuest(String deviceId) {
        UserEntity user = userDao.findByDeviceId(deviceId);
        if (user != null) {
            return user;
        }
        
        user = userDao.create("guest", null, deviceId);
        initializeUserData(user.getUserId());
        return user;
    }

    public void updateDailyRewardReceivedAt(long userId, int timestamp) {
        userDao.updateField(userId, "daily_reward_received_at", timestamp);
    }

    private void initializeUserData(long userId) {
        userDao.initializeStamina(userId);
        ensureDefaultCards(userId);
        ensureDefaultUnit(userId);
        memberDao.initializeMembers(userId);
    }

    public void addHomeBackground(long userId, int bgId) {
        userDao.addHomeBackground(userId, bgId);
    }

    public void addClothes(long userId, int clothesId) {
        userDao.addClothes(userId, clothesId);
    }

    public List<Integer> getHomeBackgrounds(long userId) {
        return userDao.getHomeBackgrounds(userId);
    }

    public List<Integer> getClothes(long userId) {
        return userDao.getClothes(userId);
    }

    public void addFuncTutorial(long userId, int tutorialId) {
        userDao.addFuncTutorial(userId, tutorialId);
    }

    public List<Integer> getFuncTutorials(long userId) {
        return userDao.getFuncTutorials(userId);
    }

    public void ensureDefaultCards(long userId) {
        java.util.List<Integer> defaultCardsList = com.emu.tqqserver.game.GameContext.getInstance().getConfig().getGameDefaults().getDefaultCards();
        int[] defaultCards = defaultCardsList.stream().mapToInt(i -> i).toArray();
        userDao.ensureDefaultCards(userId, defaultCards);
    }

    public void ensureDefaultUnit(long userId) {
        java.util.List<Integer> defaultUnitCards = com.emu.tqqserver.game.GameContext.getInstance().getConfig().getGameDefaults().getDefaultUnitCards();
        List<CardEntity> userCards = getUserCards(userId);
        unitDao.createDefaultUnit(userId, defaultUnitCards, userCards);
    }

    public List<CardEntity> getUserCards(long userId) {
        return userDao.getUserCards(userId);
    }

    public List<UserEntity> getAllUsers() {
        return userDao.getAllUsers();
    }

    public UserEntity findById(long userId) {
        return userDao.findById(userId);
    }

    public void updateBackground(long userId, int bgId) {
        userDao.updateField(userId, "profile_background_id", bgId);
    }

    public void updatePlayerTitle(long userId, int titleId, int targetId) {
        userDao.updatePlayerTitle(userId, titleId, targetId);
    }

    public void updateNickname(long userId, String nickname) {
        userDao.updateField(userId, "nickname", nickname);
    }

    public void updateTutorialStep(long userId, int step) {
        userDao.updateField(userId, "tutorial_step", step);
    }

    public void addJewel(long userId, int amount) {
        userDao.incrementField(userId, "jewel", amount);
    }

    public void addPayJewel(long userId, int amount) {
        userDao.incrementField(userId, "pay_jewel", amount);
    }

    public void addCoin(long userId, int amount) {
        userDao.incrementField(userId, "coin", amount);
    }

    public void setLevel(long userId, int level) {
        userDao.updateField(userId, "rank", level);
    }

    public void unlockChapter(long userId, int chapterId) {
        userDao.unlockChapter(userId, chapterId);
    }

    public java.util.List<Integer> getUnlockedChapters(long userId) {
        return userDao.getUnlockedChapters(userId);
    }

    public void unlockChapterGroup(long userId, int chapterGroupId, long expiresAt) {
        userDao.unlockChapterGroup(userId, chapterGroupId, expiresAt);
    }

    public java.util.Map<Integer, Long> getChapterExpires(long userId) {
        return userDao.getChapterExpires(userId);
    }

    public boolean deductCurrency(long userId, int coinAmount, int jewelAmount) {
        String sql = "UPDATE users SET coin = coin - ?, jewel = jewel - ? WHERE user_id = ? AND coin >= ? AND jewel >= ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, coinAmount);
            ps.setInt(2, jewelAmount);
            ps.setLong(3, userId);
            ps.setInt(4, coinAmount);
            ps.setInt(5, jewelAmount);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to deduct currency", e);
            return false;
        }
    }

    public boolean deductJewels(long userId, int freeJewel, int paidJewel) {
        String sql = "UPDATE users SET jewel = jewel - ?, pay_jewel = pay_jewel - ? WHERE user_id = ? AND jewel >= ? AND pay_jewel >= ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, freeJewel);
            ps.setInt(2, paidJewel);
            ps.setLong(3, userId);
            ps.setInt(4, freeJewel);
            ps.setInt(5, paidJewel);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to deduct jewels", e);
            return false;
        }
    }

    public void addItem(long userId, int itemId, int quantity) {
        String sql = "INSERT INTO user_items (user_id, item_id, quantity) VALUES (?, ?, ?) ON CONFLICT(user_id, item_id) DO UPDATE SET quantity = quantity + ?, updated_at = strftime('%s','now')";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, itemId);
            ps.setInt(3, quantity);
            ps.setInt(4, quantity);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to add item", e);
        }
    }

    public boolean deductItem(long userId, int itemId, int quantity) {
        String sql = "UPDATE user_items SET quantity = quantity - ?, updated_at = strftime('%s','now') WHERE user_id = ? AND item_id = ? AND quantity >= ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setLong(2, userId);
            ps.setInt(3, itemId);
            ps.setInt(4, quantity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to deduct item", e);
            return false;
        }
    }

    public List<com.emu.tqqserver.proto.pkg_puser.Item> getItems(long userId) {
        List<com.emu.tqqserver.proto.pkg_puser.Item> items = new java.util.ArrayList<>();
        String sql = "SELECT item_id, quantity FROM user_items WHERE user_id = ? AND quantity > 0";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(com.emu.tqqserver.proto.pkg_puser.Item.newBuilder()
                        .setUid((int) userId)
                        .setItemId(rs.getInt("item_id"))
                        .setQuantity(rs.getInt("quantity"))
                        .build());
            }
        } catch (SQLException e) {
            log.error("Failed to get items", e);
        }
        return items;
    }
    
    public void deleteUser(long userId) {
        try (java.sql.Connection conn = com.emu.tqqserver.db.DatabaseManager.getInstance().getConnection()) {
            String[] tables = {"user_units", "user_members", "user_cards", "user_items", "user_stamina", "user_blocks", "user_friends", "user_presents", "user_stories", "user_home_actors", "user_stages", "user_home_backgrounds", "user_clothes", "user_functutorials", "users"};
            for(String t : tables) {
                try(java.sql.PreparedStatement ps = conn.prepareStatement("DELETE FROM " + t + " WHERE user_id = ?")) {
                    ps.setLong(1, userId);
                    ps.executeUpdate();
                } catch (Exception ex) {
                    // Ignore missing table errors
                }
            }
        } catch(Exception e) {
            log.error("Failed to delete user", e);
        }
    }
}
