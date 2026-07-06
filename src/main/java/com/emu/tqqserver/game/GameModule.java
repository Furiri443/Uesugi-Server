package com.emu.tqqserver.game;

public abstract class GameModule {
    private final GameContext context;

    public GameModule(GameContext context) {
        this.context = context;
    }

    public GameContext getContext() {
        return context;
    }
}
