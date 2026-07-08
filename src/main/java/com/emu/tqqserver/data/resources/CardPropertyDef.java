package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card_property.json")
public class CardPropertyDef extends BaseDef {
    private int id;

    @SerializedName("card_id")
    private int cardId;

    @SerializedName("awakening_type")
    private int awakeningType;

    @SerializedName("score_attribute")
    private int scoreAttribute;

    @SerializedName("score_attribute2")
    private int scoreAttribute2;

    @SerializedName("score_red")
    private int scoreRed;

    @SerializedName("score_green")
    private int scoreGreen;

    @SerializedName("score_blue")
    private int scoreBlue;

    @SerializedName("score_yellow")
    private int scoreYellow;

    @SerializedName("score_purple")
    private int scorePurple;

    @SerializedName("passive_skill_id")
    private int passiveSkillId;

    @SerializedName("passive_skill_growth_id")
    private int passiveSkillGrowthId;

    @SerializedName("active_skill_id")
    private int activeSkillId;

    public int getCardId() {
        return cardId;
    }

    public int getAwakeningType() {
        return awakeningType;
    }

    public int getScoreAttribute() {
        return scoreAttribute;
    }

    public int getScoreAttribute2() {
        return scoreAttribute2;
    }

    public int getScoreRed() {
        return scoreRed;
    }

    public int getScoreGreen() {
        return scoreGreen;
    }

    public int getScoreBlue() {
        return scoreBlue;
    }

    public int getScoreYellow() {
        return scoreYellow;
    }

    public int getScorePurple() {
        return scorePurple;
    }

    public int getPassiveSkillId() {
        return passiveSkillId;
    }

    public int getPassiveSkillGrowthId() {
        return passiveSkillGrowthId;
    }

    public int getActiveSkillId() {
        return activeSkillId;
    }

    @Override
    public int getId() {
        return id;
    }
}