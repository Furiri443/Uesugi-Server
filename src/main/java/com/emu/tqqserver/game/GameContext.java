package com.emu.tqqserver.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameContext {
    private static final Logger log = LoggerFactory.getLogger(GameContext.class);

    private static GameContext instance;
    private com.emu.tqqserver.server.ServerConfig config;

    public static synchronized GameContext getInstance() {
        if (instance == null) {
            instance = new GameContext();
        }
        return instance;
    }

    private GameContext() {
        // Initialize modules here
    }

    public com.emu.tqqserver.server.ServerConfig getConfig() {
        return config;
    }

    public void setConfig(com.emu.tqqserver.server.ServerConfig config) {
        this.config = config;
    }

    // TODO: Add fields and getters for modules like UserModule, PuzzleModule, etc.
}
