package com.emu.tqqserver.game.user;

public class CardEntity {
    private long id;
    private int cardId;
    private int level;
    private int exp;
    private int activeSkillLevel;
    private int activeSkillExp;
    private int limitbreakRank;

    public CardEntity(long id, int cardId, int level, int exp, int activeSkillLevel, int activeSkillExp, int limitbreakRank) {
        this.id = id;
        this.cardId = cardId;
        this.level = level;
        this.exp = exp;
        this.activeSkillLevel = activeSkillLevel;
        this.activeSkillExp = activeSkillExp;
        this.limitbreakRank = limitbreakRank;
    }

    public long getId() { return id; }
    public int getCardId() { return cardId; }
    public int getLevel() { return level; }
    public int getExp() { return exp; }
    public int getActiveSkillLevel() { return activeSkillLevel; }
    public int getActiveSkillExp() { return activeSkillExp; }
    public int getLimitbreakRank() { return limitbreakRank; }
}
