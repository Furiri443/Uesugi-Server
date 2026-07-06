package com.emu.tqqserver.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * SQLite database manager using HikariCP connection pooling.
 *
 * <p>Schema mirrors the game's player data model inferred from:
 * <ul>
 *   <li>Assets.Scripts.Build.BuildInfomation/ApiInfomation (class name suggests API data structure)</li>
 *   <li>SerializedTypes.xml - exposed serialized class names</li>
 *   <li>Namespace: Assets.Scripts.Application.* (Unity scene objects)</li>
 * </ul>
 */
public class DatabaseManager {

    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
    private static final DatabaseManager INSTANCE = new DatabaseManager();

    private HikariDataSource dataSource;

    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        return INSTANCE;
    }

    public void initialize(String dbPath) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:" + dbPath);
        config.setDriverClassName("org.sqlite.JDBC");
        config.setMaximumPoolSize(4); // SQLite is single-writer
        config.setConnectionTimeout(30000);
        
        // Ensure pragmas are executed on every new connection
        config.setConnectionInitSql("PRAGMA journal_mode=WAL; PRAGMA synchronous=NORMAL; PRAGMA foreign_keys=ON; PRAGMA busy_timeout=5000;");

        // These properties might be ignored by some driver versions if passed this way, but we keep them just in case
        config.addDataSourceProperty("journal_mode", "WAL");
        config.addDataSourceProperty("synchronous", "NORMAL");
        config.addDataSourceProperty("foreign_keys", "ON");

        dataSource = new HikariDataSource(config);
        log.info("Database initialized: {}", dbPath);
        createSchema();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    // -----------------------------------------------------------------------
    // Schema DDL
    // -----------------------------------------------------------------------

    private void createSchema() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(SQL_CREATE_USERS);
            // Ensure auto-increment for users table starts at 10000 (so first inserted user is 10001)
            stmt.executeUpdate("INSERT OR IGNORE INTO users (user_id, platform_type, platform_id, device_id) VALUES (10000, 'dummy', 'dummy', 'dummy')");
            stmt.executeUpdate("DELETE FROM users WHERE user_id = 10000");

            stmt.executeUpdate(SQL_CREATE_USER_ITEMS);
            stmt.executeUpdate(SQL_CREATE_USER_CARDS);
            stmt.executeUpdate(SQL_CREATE_USER_STAGES);
            stmt.executeUpdate(SQL_CREATE_USER_STORIES);
            stmt.executeUpdate(SQL_CREATE_USER_STAMINA);
            stmt.executeUpdate(SQL_CREATE_GACHA_LOG);
            stmt.executeUpdate(SQL_CREATE_USER_PRESENTS);
            stmt.executeUpdate(SQL_CREATE_USER_COOKING);
            stmt.executeUpdate(SQL_CREATE_USER_FRIENDS);
            stmt.executeUpdate(SQL_CREATE_USER_BLOCKS);

            // Migration to add profile options to users if they don't exist
            try { stmt.executeUpdate("ALTER TABLE users ADD COLUMN profile_background_id INTEGER NOT NULL DEFAULT 0"); } catch (SQLException ignored) {}
            try { stmt.executeUpdate("ALTER TABLE users ADD COLUMN player_title_id INTEGER NOT NULL DEFAULT 0"); } catch (SQLException ignored) {}
            try { stmt.executeUpdate("ALTER TABLE users ADD COLUMN player_title_target_id INTEGER NOT NULL DEFAULT 0"); } catch (SQLException ignored) {}
            try { stmt.executeUpdate("ALTER TABLE users ADD COLUMN pay_jewel INTEGER NOT NULL DEFAULT 0"); } catch (SQLException ignored) {}

            log.info("Database schema created/verified");
        } catch (SQLException e) {
            throw new RuntimeException("Schema creation failed", e);
        }
    }

    // ── Users ────────────────────────────────────────────────────────────────
    private static final String SQL_CREATE_USERS = """
        CREATE TABLE IF NOT EXISTS users (
            user_id                  INTEGER PRIMARY KEY AUTOINCREMENT,
            platform_type            TEXT    NOT NULL DEFAULT 'guest',
            platform_id              TEXT    UNIQUE,
            device_id                TEXT,
            nickname                 TEXT    NOT NULL DEFAULT 'プレイヤー',
            rank                     INTEGER NOT NULL DEFAULT 1,
            exp                      INTEGER NOT NULL DEFAULT 0,
            jewel                    INTEGER NOT NULL DEFAULT 3000,
            pay_jewel                INTEGER NOT NULL DEFAULT 0,
            coin                     INTEGER NOT NULL DEFAULT 0,
            tutorial_step            INTEGER NOT NULL DEFAULT 0,
            profile_background_id    INTEGER NOT NULL DEFAULT 0,
            home_background_id       INTEGER NOT NULL DEFAULT 10001,
            player_title_id          INTEGER NOT NULL DEFAULT 0,
            player_title_target_id   INTEGER NOT NULL DEFAULT 0,
            last_login_at            INTEGER,
            daily_reward_received_at INTEGER NOT NULL DEFAULT 0,
            created_at               INTEGER NOT NULL DEFAULT (strftime('%s','now'))
        )
        """;

    // ── Items (consumables, materials) ───────────────────────────────────────
    private static final String SQL_CREATE_USER_ITEMS = """
        CREATE TABLE IF NOT EXISTS user_items (
            id         INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id    INTEGER NOT NULL REFERENCES users(user_id),
            item_id    INTEGER NOT NULL,
            quantity   INTEGER NOT NULL DEFAULT 0,
            updated_at INTEGER NOT NULL DEFAULT (strftime('%s','now')),
            UNIQUE(user_id, item_id)
        )
        """;

    // ── Cards (gacha pulls / characters) ─────────────────────────────────────
    private static final String SQL_CREATE_USER_CARDS = """
        CREATE TABLE IF NOT EXISTS user_cards (
            id           INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id      INTEGER NOT NULL REFERENCES users(user_id),
            card_id      INTEGER NOT NULL,
            level        INTEGER NOT NULL DEFAULT 1,
            exp          INTEGER NOT NULL DEFAULT 0,
            awaken_level INTEGER NOT NULL DEFAULT 0,
            skill_level  INTEGER NOT NULL DEFAULT 1,
            is_new       INTEGER NOT NULL DEFAULT 1,
            obtained_at  INTEGER NOT NULL DEFAULT (strftime('%s','now'))
        )
        """;

    // ── Stage clear progress ──────────────────────────────────────────────────
    private static final String SQL_CREATE_USER_STAGES = """
        CREATE TABLE IF NOT EXISTS user_stages (
            id         INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id    INTEGER NOT NULL REFERENCES users(user_id),
            stage_id   INTEGER NOT NULL,
            stars      INTEGER NOT NULL DEFAULT 0,
            best_score INTEGER NOT NULL DEFAULT 0,
            clear_count INTEGER NOT NULL DEFAULT 0,
            cleared_at INTEGER,
            UNIQUE(user_id, stage_id)
        )
        """;

    // ── Story read progress ───────────────────────────────────────────────────
    private static final String SQL_CREATE_USER_STORIES = """
        CREATE TABLE IF NOT EXISTS user_stories (
            id         INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id    INTEGER NOT NULL REFERENCES users(user_id),
            story_id   INTEGER NOT NULL,
            is_read    INTEGER NOT NULL DEFAULT 0,
            read_at    INTEGER,
            UNIQUE(user_id, story_id)
        )
        """;

    // ── Stamina ───────────────────────────────────────────────────────────────
    private static final String SQL_CREATE_USER_STAMINA = """
        CREATE TABLE IF NOT EXISTS user_stamina (
            user_id          INTEGER PRIMARY KEY REFERENCES users(user_id),
            current_stamina  INTEGER NOT NULL DEFAULT 60,
            max_stamina      INTEGER NOT NULL DEFAULT 60,
            last_recover_at  INTEGER NOT NULL DEFAULT (strftime('%s','now'))
        )
        """;

    // ── Gacha log ─────────────────────────────────────────────────────────────
    private static final String SQL_CREATE_GACHA_LOG = """
        CREATE TABLE IF NOT EXISTS gacha_log (
            id         INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id    INTEGER NOT NULL REFERENCES users(user_id),
            gacha_id   INTEGER NOT NULL,
            card_id    INTEGER NOT NULL,
            rarity     INTEGER NOT NULL,
            pulled_at  INTEGER NOT NULL DEFAULT (strftime('%s','now'))
        )
        """;

    // ── Presents/Inbox ────────────────────────────────────────────────────────
    private static final String SQL_CREATE_USER_PRESENTS = """
        CREATE TABLE IF NOT EXISTS user_presents (
            present_id      INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id         INTEGER NOT NULL REFERENCES users(user_id),
            category        INTEGER NOT NULL,
            target_id       INTEGER NOT NULL,
            quantity        INTEGER NOT NULL,
            text            TEXT,
            received        INTEGER NOT NULL DEFAULT 0,
            created_at      INTEGER NOT NULL DEFAULT (strftime('%s','now'))
        )
        """;

    // ── Cooking ───────────────────────────────────────────────────────────────
    private static final String SQL_CREATE_USER_COOKING = """
        CREATE TABLE IF NOT EXISTS user_cooking (
            user_id         INTEGER NOT NULL REFERENCES users(user_id),
            tray_id         INTEGER NOT NULL,
            recipe_id       INTEGER NOT NULL,
            start_at        INTEGER NOT NULL,
            finish_at       INTEGER NOT NULL,
            PRIMARY KEY (user_id, tray_id)
        )
        """;

    // ── Friends ───────────────────────────────────────────────────────────────
    private static final String SQL_CREATE_USER_FRIENDS = """
        CREATE TABLE IF NOT EXISTS user_home_actors (
            user_id       INTEGER NOT NULL,
            character_id  INTEGER NOT NULL,
            model_kind_id INTEGER NOT NULL DEFAULT 1,
            clothes_id    INTEGER NOT NULL DEFAULT 0,
            position      INTEGER NOT NULL,
            PRIMARY KEY (user_id, character_id),
            FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
        );

        CREATE TABLE IF NOT EXISTS user_friends (
            user_id         INTEGER NOT NULL REFERENCES users(user_id),
            friend_id       INTEGER NOT NULL REFERENCES users(user_id),
            status          INTEGER NOT NULL DEFAULT 0,
            created_at      INTEGER NOT NULL DEFAULT (strftime('%s','now')),
            PRIMARY KEY (user_id, friend_id)
        )
        """;

    // ── Blocks ────────────────────────────────────────────────────────────────
    private static final String SQL_CREATE_USER_BLOCKS = """
        CREATE TABLE IF NOT EXISTS user_blocks (
            user_id         INTEGER NOT NULL REFERENCES users(user_id),
            blocked_user_id INTEGER NOT NULL REFERENCES users(user_id),
            created_at      INTEGER NOT NULL DEFAULT (strftime('%s','now')),
            PRIMARY KEY (user_id, blocked_user_id)
        )
        """;
}
