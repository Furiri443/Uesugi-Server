package com.emu.tqqserver.server;

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
public class ServerConfig {

    private static final Logger log = LoggerFactory.getLogger(ServerConfig.class);

    private String host            = "0.0.0.0";
    private int    port            = GotopazuServer.DEFAULT_PORT;
    private String dbPath          = "gotopazu.db";
    private String cdnDir          = "assets_cdn";
    private String resourceListDir = "gotopazu";

    // Public constructor for Jackson
    public ServerConfig() {}

    public static ServerConfig fromArgs(String[] args) {
        ServerConfig cfg = new ServerConfig();
        
        // 1. Try to load from config.json
        File configFile = new File("config.json");
        if (configFile.exists() && configFile.isFile()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                cfg = mapper.readValue(configFile, ServerConfig.class);
                log.info("Loaded configuration from config.json");
            } catch (Exception e) {
                log.error("Failed to parse config.json, using defaults", e);
            }
        } else {
            log.info("No config.json found, using defaults");
        }

        // 2. Env overrides
        String v;
        if ((v = System.getenv("GOTOPAZU_HOST"))         != null) cfg.host            = v;
        if ((v = System.getenv("GOTOPAZU_PORT"))         != null) cfg.port            = Integer.parseInt(v);
        if ((v = System.getenv("GOTOPAZU_DB_PATH"))      != null) cfg.dbPath          = v;
        if ((v = System.getenv("GOTOPAZU_CDN_DIR"))      != null) cfg.cdnDir          = v;
        if ((v = System.getenv("GOTOPAZU_RESOURCE_DIR")) != null) cfg.resourceListDir = v;

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
    
    public String getCdnDir()          { return cdnDir; }
    public void setCdnDir(String cdnDir) { this.cdnDir = cdnDir; }
    
    public String getResourceListDir() { return resourceListDir; }
    public void setResourceListDir(String resourceListDir) { this.resourceListDir = resourceListDir; }

    // Legacy getters
    public int getApiPort()  { return port; }
    public int getWsPort()   { return port; }
    public int getHttpPort() { return port; }
    public int getCdnPort()  { return port; }
}
