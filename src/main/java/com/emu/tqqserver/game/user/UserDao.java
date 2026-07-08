package com.emu.tqqserver.game.user;

import com.emu.tqqserver.db.dao.BaseDao;


import com.emu.tqqserver.db.DatabaseManager;
import com.emu.tqqserver.game.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    public void addFuncTutorial(long userId, int tutorialId) {
        String sql = "INSERT OR IGNORE INTO user_functutorials (user_id, tutorial_id) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, tutorialId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to add func tutorial", e);
        }
    }

    public java.util.List<Integer> getFuncTutorials(long userId) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        String sql = "SELECT tutorial_id FROM user_functutorials WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("tutorial_id"));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get func tutorials", e);
        }
        return list;
    }

    public void addHomeBackground(long userId, int bgId) {
        String sql = "INSERT OR IGNORE INTO user_home_backgrounds (user_id, bg_id) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, bgId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to add home background", e);
        }
    }

    public java.util.List<Integer> getHomeBackgrounds(long userId) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        String sql = "SELECT bg_id FROM user_home_backgrounds WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("bg_id"));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get home backgrounds", e);
        }
        return list;
    }

    public void addClothes(long userId, int clothesId) {
        String sql = "INSERT OR IGNORE INTO user_clothes (user_id, clothes_id) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, clothesId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to add clothes", e);
        }
    }

    public java.util.List<Integer> getClothes(long userId) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        String sql = "SELECT clothes_id FROM user_clothes WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("clothes_id"));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get clothes", e);
        }
        return list;
    }

    public UserEntity findById(long userId) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE user_id = ?")) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs, false);
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("User findById failed", e);
        }
    }

    public UserEntity findByDeviceId(String deviceId) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE device_id = ? AND platform_type = 'guest'")) {
            ps.setString(1, deviceId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs, false);
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("User findByDeviceId failed", e);
        }
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(mapRow(rs, false));
            }
        } catch (SQLException e) {
            throw new RuntimeException("User getAllUsers failed", e);
        }
        return users;
    }

    public void saveUser(UserEntity user) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT OR REPLACE INTO users (user_id, nickname, comment, rank, exp, jewel, pay_jewel, coin, tutorial_step, profile_background_id, home_background_id, player_title_id, player_title_target_id, daily_reward_received_at, created_at, updated_at) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, COALESCE((SELECT created_at FROM users WHERE user_id = ?), strftime('%s','now')), strftime('%s','now'))")) {
            ps.setLong(1, user.getUserId());
            ps.setString(2, user.getNickname());
            ps.setString(3, user.getComment());
            ps.setInt(4, user.getRank());
            ps.setInt(5, user.getExp());
            ps.setInt(6, user.getJewel());
            ps.setInt(7, user.getPayJewel());
            ps.setInt(8, user.getCoin());
            ps.setInt(9, user.getTutorialStep());
            ps.setInt(10, user.getProfileBackgroundId());
            ps.setInt(11, user.getHomeBackgroundId());
            ps.setInt(12, user.getPlayerTitleId());
            ps.setInt(13, user.getPlayerTitleTargetId());
            ps.setInt(14, user.getDailyRewardReceivedAt());
            ps.setLong(15, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("User save failed", e);
        }
    }

    public UserEntity create(String platformType, String platformId, String deviceId) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO users (platform_type, platform_id, device_id) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, platformType);
            ps.setString(2, platformId);
            ps.setString(3, deviceId);
            ps.executeUpdate();
            
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                long newId = keys.getLong(1);
                UserEntity user = new UserEntity();
                user.setUserId(newId);
                user.setPlatformType(platformType);
                user.setPlatformId(platformId);
                user.setDeviceId(deviceId);

                com.emu.tqqserver.server.ServerConfig.GameDefaultsConfig defaults = 
                    com.emu.tqqserver.game.GameContext.getInstance().getConfig().getGameDefaults();
                
                user.setNickname(defaults.getDefaultNickname());
                user.setJewel(defaults.getStartingJewel());
                user.setCoin(100000);
                user.setNewUser(true);
                return user;
            }
            throw new RuntimeException("Failed to retrieve generated key for new user");
        } catch (SQLException e) {
            throw new RuntimeException("User creation failed", e);
        }
    }

    public void updateField(long userId, String field, Object value) {
        String sql = "UPDATE users SET " + field + " = ? WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, value);
            ps.setLong(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("User update " + field + " failed", e);
        }
    }

    public void incrementField(long userId, String field, int amount) {
        String sql = "UPDATE users SET " + field + " = " + field + " + ? WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, amount);
            ps.setLong(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("User increment " + field + " failed", e);
        }
    }
    
    public void updatePlayerTitle(long userId, int titleId, int targetId) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE users SET player_title_id = ?, player_title_target_id = ? WHERE user_id = ?")) {
            ps.setInt(1, titleId);
            ps.setInt(2, targetId);
            ps.setLong(3, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("updatePlayerTitle failed", e);
        }
    }

    public void updateOptions(long userId, int bgm, int se, int voice, int protectCardR6, int protectCardR5, int protectCardFirst) {
        String sql = "UPDATE users SET option_bgm = ?, option_se = ?, option_voice = ?, option_protect_card_r6 = ?, option_protect_card_r5 = ?, option_protect_card_first = ? WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bgm);
            ps.setInt(2, se);
            ps.setInt(3, voice);
            ps.setInt(4, protectCardR6);
            ps.setInt(5, protectCardR5);
            ps.setInt(6, protectCardFirst);
            ps.setLong(7, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("updateOptions failed", e);
        }
    }

    public void initializeStamina(long userId) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT OR IGNORE INTO user_stamina (user_id) VALUES (?)")) {
            ps.setLong(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("initializeStamina failed", e);
        }
    }

    public void giveItem(long userId, int itemId, int qty) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO user_items (user_id, item_id, quantity) VALUES (?, ?, ?) " +
                             "ON CONFLICT(user_id, item_id) DO UPDATE SET quantity = quantity + ?")) {
            ps.setLong(1, userId);
            ps.setInt(2, itemId);
            ps.setInt(3, qty);
            ps.setInt(4, qty);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("giveItem failed", e);
        }
    }

    public void ensureDefaultCards(long userId, int[] cardIds) {
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            String sql = "INSERT OR IGNORE INTO user_cards (user_id, card_id, level, exp, awaken_level, skill_level, is_new) VALUES (?, ?, 1, ?, 0, 1, 0)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int cardId : cardIds) {
                    int exp = 1; // Default fallback
                    com.emu.tqqserver.data.resources.CardDef cardDef = com.emu.tqqserver.data.GameData.getCardDataTable().get(cardId);
                    if (cardDef != null) {
                        int levelGrowthId = cardDef.getLevelGrowthId();
                        for (com.emu.tqqserver.data.resources.LevelGrowthDef growth : com.emu.tqqserver.data.GameData.getLevelGrowthDataTable().values()) {
                            if (growth.getId() == levelGrowthId && growth.getLevel() == 1) {
                                exp = growth.getValue() + 1;
                                break;
                            }
                        }
                    }
                    ps.setLong(1, userId);
                    ps.setInt(2, cardId);
                    ps.setInt(3, exp);
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException e) {
                log.error("ensureDefaultCards failed during batch execution", e);
            }
        } catch (SQLException e) {
            log.error("ensureDefaultCards failed", e);
        }
    }

    public void updateCardExpAndLevel(long cardId, int newExp, int newLevel) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE user_cards SET exp = ?, level = ? WHERE id = ?")) {
            ps.setInt(1, newExp);
            ps.setInt(2, newLevel);
            ps.setLong(3, cardId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("updateCardExpAndLevel failed", e);
        }
    }

    public void deleteCards(List<Long> cardIds) {
        if (cardIds.isEmpty()) return;
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < cardIds.size(); i++) {
            placeholders.append("?");
            if (i < cardIds.size() - 1) placeholders.append(",");
        }
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM user_cards WHERE id IN (" + placeholders + ")")) {
            for (int i = 0; i < cardIds.size(); i++) {
                ps.setLong(i + 1, cardIds.get(i));
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteCards failed", e);
        }
    }

    public List<CardEntity> getUserCards(long userId) {
        List<CardEntity> cards = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, card_id, level, exp, skill_level, awaken_level FROM user_cards WHERE user_id = ?")) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cards.add(new CardEntity(
                        rs.getLong("id"),
                        rs.getInt("card_id"),
                        rs.getInt("level"),
                        rs.getInt("exp"),
                        rs.getInt("skill_level"),
                        0, // activeSkillExp
                        rs.getInt("awaken_level") // wait, limitbreakRank? Awaken level is something else.
                    ));
                }
            }
        } catch (SQLException e) {
            log.error("getUserCards failed", e);
        }
        return cards;
    }

    private UserEntity mapRow(ResultSet rs, boolean isNew) throws SQLException {
        UserEntity u = new UserEntity();
        u.setUserId(rs.getLong("user_id"));
        u.setPlatformType(rs.getString("platform_type"));
        u.setPlatformId(rs.getString("platform_id"));
        u.setDeviceId(rs.getString("device_id"));
        u.setNickname(rs.getString("nickname"));
        u.setRank(rs.getInt("rank"));
        u.setExp(rs.getInt("exp"));
        u.setJewel(rs.getInt("jewel"));
        u.setPayJewel(rs.getInt("pay_jewel"));
        u.setCoin(rs.getInt("coin"));
        u.setTutorialStep(rs.getInt("tutorial_step"));
        u.setProfileBackgroundId(rs.getInt("profile_background_id"));
        u.setHomeBackgroundId(rs.getInt("home_background_id"));
        u.setPlayerTitleId(rs.getInt("player_title_id"));
        u.setPlayerTitleTargetId(rs.getInt("player_title_target_id"));
        u.setDailyRewardReceivedAt(rs.getInt("daily_reward_received_at"));
        try { u.setOptionBgm(rs.getInt("option_bgm")); } catch (SQLException ignored) {}
        try { u.setOptionSe(rs.getInt("option_se")); } catch (SQLException ignored) {}
        try { u.setOptionVoice(rs.getInt("option_voice")); } catch (SQLException ignored) {}
        try { u.setOptionProtectCardR6(rs.getInt("option_protect_card_r6")); } catch (SQLException ignored) {}
        try { u.setOptionProtectCardR5(rs.getInt("option_protect_card_r5")); } catch (SQLException ignored) {}
        try { u.setOptionProtectCardFirst(rs.getInt("option_protect_card_first")); } catch (SQLException ignored) {}
        u.setNewUser(isNew);
        return u;
    }
}