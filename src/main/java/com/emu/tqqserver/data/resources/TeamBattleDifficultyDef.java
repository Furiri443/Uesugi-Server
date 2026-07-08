package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_difficulty.json")
public class TeamBattleDifficultyDef extends BaseDef {
    private int id;

    private int difficulty;

    @SerializedName("limit_time")
    private int limitTime;

    @SerializedName("treasure_gold_weight")
    private int treasureGoldWeight;

    @SerializedName("treasure_silver_weight")
    private int treasureSilverWeight;

    @SerializedName("treasure_bronze_weight")
    private int treasureBronzeWeight;

    @SerializedName("scroll_time")
    private int scrollTime;

    @SerializedName("scroll_step")
    private int scrollStep;

    @SerializedName("additional_time")
    private int additionalTime;

    @SerializedName("bonus_treasure")
    private int bonusTreasure;

    public int getDifficulty() {
        return difficulty;
    }

    public int getLimitTime() {
        return limitTime;
    }

    public int getTreasureGoldWeight() {
        return treasureGoldWeight;
    }

    public int getTreasureSilverWeight() {
        return treasureSilverWeight;
    }

    public int getTreasureBronzeWeight() {
        return treasureBronzeWeight;
    }

    public int getScrollTime() {
        return scrollTime;
    }

    public int getScrollStep() {
        return scrollStep;
    }

    public int getAdditionalTime() {
        return additionalTime;
    }

    public int getBonusTreasure() {
        return bonusTreasure;
    }

    @Override
    public int getId() {
        return id;
    }
}