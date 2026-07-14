package com.emu.tqqserver.server;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Server configuration.
 *
 * <p>Priority:
 * 1. config.json
 * 2. Environment variables (GOTOPAZU_*)
 * 3. CLI args
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerConfig {

    private static final Logger log = LoggerFactory.getLogger(ServerConfig.class);

    private String host            = "0.0.0.0";
    private int    port            = GotopazuServer.DEFAULT_PORT;
    private String dbPath          = "gotopazu.db";
    private String dbType          = "sqlite"; // sqlite or mysql
    private String dbHost          = "127.0.0.1";
    private int    dbPort          = 3306;
    private String dbName          = "gotopazu";
    private String dbUser          = "root";
    private String dbPass          = "";

    private String cdnDir          = "assets_cdn";
    private String resourceListDir = "gotopazu";
    private String sqliteTmpDir    = "./data/tmp";
    private String assetUrlPrefix  = "assets-cancer";

    // Public constructor for Jackson
    public ServerConfig() {}

    public static ServerConfig fromArgs(String[] args) {
        ServerConfig cfg = new ServerConfig();
        
        // 1. Try to load from config.json
        File configFile = new File("config.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);

        if (configFile.exists() && configFile.isFile()) {
            try {
                cfg = mapper.readValue(configFile, ServerConfig.class);
                log.info("Loaded configuration from config.json");
                // Rewrite file to ensure any missing fields are generated with defaults
                mapper.writeValue(configFile, cfg);
            } catch (Exception e) {
                log.error("Failed to parse config.json, using defaults", e);
            }
        } else {
            log.info("No config.json found, creating one with defaults");
            try {
                mapper.writeValue(configFile, cfg);
            } catch (Exception e) {
                log.error("Failed to create config.json", e);
            }
        }

        // 2. Env overrides
        String v;
        if ((v = System.getenv("GOTOPAZU_HOST"))         != null) cfg.host            = v;
        if ((v = System.getenv("GOTOPAZU_PORT"))         != null) cfg.port            = Integer.parseInt(v);
        if ((v = System.getenv("GOTOPAZU_DB_TYPE"))      != null) cfg.dbType          = v;
        if ((v = System.getenv("GOTOPAZU_DB_PATH"))      != null) cfg.dbPath          = v;
        if ((v = System.getenv("GOTOPAZU_DB_HOST"))      != null) cfg.dbHost          = v;
        if ((v = System.getenv("GOTOPAZU_DB_PORT"))      != null) cfg.dbPort          = Integer.parseInt(v);
        if ((v = System.getenv("GOTOPAZU_DB_NAME"))      != null) cfg.dbName          = v;
        if ((v = System.getenv("GOTOPAZU_DB_USER"))      != null) cfg.dbUser          = v;
        if ((v = System.getenv("GOTOPAZU_DB_PASS"))      != null) cfg.dbPass          = v;
        if ((v = System.getenv("GOTOPAZU_CDN_DIR"))      != null) cfg.cdnDir          = v;
        if ((v = System.getenv("GOTOPAZU_RESOURCE_DIR")) != null) cfg.resourceListDir = v;
        if ((v = System.getenv("GOTOPAZU_SQLITE_TMP"))   != null) cfg.sqliteTmpDir    = v;
        if ((v = System.getenv("GOTOPAZU_ASSET_PREFIX")) != null) cfg.assetUrlPrefix  = v;

        // 3. CLI args (highest priority)
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--host"         -> cfg.host            = args[++i];
                case "--port",
                     "--api-port", "--ws-port", "--http-port" // legacy aliases
                    -> cfg.port = Integer.parseInt(args[++i]);
                case "--db"           -> cfg.dbPath          = args[++i];
                case "--cdn-dir"      -> cfg.cdnDir          = args[++i];
                case "--resource-dir" -> cfg.resourceListDir = args[++i];
            }
        }
        
        return cfg;
    }

    // Getters and Setters for Jackson and application use
    public String getHost()            { return host; }
    public void setHost(String host)   { this.host = host; }
    
    public int    getPort()            { return port; }
    public void setPort(int port)      { this.port = port; }
    
    public String getDbPath()          { return dbPath; }
    public void setDbPath(String dbPath) { this.dbPath = dbPath; }
    
    public String getDbType() { return dbType; }
    public void setDbType(String dbType) { this.dbType = dbType; }

    public String getDbHost() { return dbHost; }
    public void setDbHost(String dbHost) { this.dbHost = dbHost; }

    public int getDbPort() { return dbPort; }
    public void setDbPort(int dbPort) { this.dbPort = dbPort; }

    public String getDbName() { return dbName; }
    public void setDbName(String dbName) { this.dbName = dbName; }

    public String getDbUser() { return dbUser; }
    public void setDbUser(String dbUser) { this.dbUser = dbUser; }

    public String getDbPass() { return dbPass; }
    public void setDbPass(String dbPass) { this.dbPass = dbPass; }

    public String getCdnDir() { return cdnDir; }
    public void setCdnDir(String cdnDir) { this.cdnDir = cdnDir; }
    
    public String getResourceListDir() { return resourceListDir; }
    public void setResourceListDir(String resourceListDir) { this.resourceListDir = resourceListDir; }

    public String getSqliteTmpDir() { return sqliteTmpDir; }
    public void setSqliteTmpDir(String sqliteTmpDir) { this.sqliteTmpDir = sqliteTmpDir; }

    public String getAssetUrlPrefix() { return assetUrlPrefix; }
    public void setAssetUrlPrefix(String assetUrlPrefix) { this.assetUrlPrefix = assetUrlPrefix; }

    // Legacy getters
    public int getApiPort()  { return port; }
    public int getWsPort()   { return port; }
    public int getHttpPort() { return port; }
    public int getCdnPort()  { return port; }

    private GameDefaultsConfig gameDefaults = new GameDefaultsConfig();

    public GameDefaultsConfig getGameDefaults() {
        return gameDefaults;
    }

    public void setGameDefaults(GameDefaultsConfig gameDefaults) {
        this.gameDefaults = gameDefaults;
    }

    public static class GameDefaultsConfig {
        private String defaultNickname = "プレイヤー";
        private String defaultComment = "ごとぱずサーバー";
        private String defaultLastname = "上杉";
        private String defaultFirstname = "風太郎";
        private String defaultLastnameKana = "ウエスギ";
        private String defaultFirstnameKana = "フータロー";
        private int startingJewel = 3000;
        private int defaultAp = 140;
        private int defaultBackgroundId = 10001;
        private int defaultPlayerTitleId = 50301003;
        private java.util.List<Integer> defaultCards = java.util.List.of(10001, 10002, 10003, 10004, 10005, 99901, 99902, 99903, 99904, 99905);
        private java.util.List<Integer> defaultUnitCards = java.util.List.of(99901, 99902, 99903, 99904, 99905);

        public String getDefaultNickname() { return defaultNickname; }
        public void setDefaultNickname(String defaultNickname) { this.defaultNickname = defaultNickname; }

        public String getDefaultComment() { return defaultComment; }
        public void setDefaultComment(String defaultComment) { this.defaultComment = defaultComment; }

        public String getDefaultLastname() { return defaultLastname; }
        public void setDefaultLastname(String defaultLastname) { this.defaultLastname = defaultLastname; }

        public String getDefaultFirstname() { return defaultFirstname; }
        public void setDefaultFirstname(String defaultFirstname) { this.defaultFirstname = defaultFirstname; }

        public String getDefaultLastnameKana() { return defaultLastnameKana; }
        public void setDefaultLastnameKana(String defaultLastnameKana) { this.defaultLastnameKana = defaultLastnameKana; }

        public String getDefaultFirstnameKana() { return defaultFirstnameKana; }
        public void setDefaultFirstnameKana(String defaultFirstnameKana) { this.defaultFirstnameKana = defaultFirstnameKana; }

        public int getStartingJewel() { return startingJewel; }
        public void setStartingJewel(int startingJewel) { this.startingJewel = startingJewel; }

        public int getDefaultAp() { return defaultAp; }
        public void setDefaultAp(int defaultAp) { this.defaultAp = defaultAp; }

        public int getDefaultBackgroundId() { return defaultBackgroundId; }
        public void setDefaultBackgroundId(int defaultBackgroundId) { this.defaultBackgroundId = defaultBackgroundId; }

        public int getDefaultPlayerTitleId() { return defaultPlayerTitleId; }
        public void setDefaultPlayerTitleId(int defaultPlayerTitleId) { this.defaultPlayerTitleId = defaultPlayerTitleId; }

        public java.util.List<Integer> getDefaultCards() { return defaultCards; }
        public void setDefaultCards(java.util.List<Integer> defaultCards) { this.defaultCards = defaultCards; }

        public java.util.List<Integer> getDefaultUnitCards() { return defaultUnitCards; }
        public void setDefaultUnitCards(java.util.List<Integer> defaultUnitCards) { this.defaultUnitCards = defaultUnitCards; }
    }
}
