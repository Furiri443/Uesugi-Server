package com.emu.tqqserver.game.user;

public class CardEntity {
    private long id;
    private int cardId;
    private int level;
    private int exp;
    private int skillLevel;
    private int awakenLevel;

    public CardEntity(long id, int cardId, int level, int exp, int skillLevel, int awakenLevel) {
        this.id = id;
        this.cardId = cardId;
        this.level = level;
        this.exp = exp;
        this.skillLevel = skillLevel;
        this.awakenLevel = awakenLevel;
    }

    public long getId() { return id; }
    public int getCardId() { return cardId; }
    public int getLevel() { return level; }
    public int getExp() { return exp; }
    public int getSkillLevel() { return skillLevel; }
    public int getAwakenLevel() { return awakenLevel; }
}
