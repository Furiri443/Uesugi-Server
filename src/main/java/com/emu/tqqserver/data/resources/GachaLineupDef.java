package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "gacha_lineup.json")
public class GachaLineupDef extends BaseDef {
    private int id;

    @SerializedName("lineup_id")
    private int lineupId;

    @SerializedName("group_id")
    private int groupId;

    @SerializedName("card_id")
    private int cardId;

    private int weight;

    @SerializedName("bonus_weight")
    private int bonusWeight;

    private int level;

    @SerializedName("skill_level")
    private int skillLevel;

    private int priority;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("bonus_start_date")
    private int bonusStartDate;

    @SerializedName("bonus_end_date")
    private long bonusEndDate;

    public int getLineupId() {
        return lineupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getCardId() {
        return cardId;
    }

    public int getWeight() {
        return weight;
    }

    public int getBonusWeight() {
        return bonusWeight;
    }

    public int getLevel() {
        return level;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getPriority() {
        return priority;
    }

    public int getOpenDate() {
        return openDate;
    }

    public int getBonusStartDate() {
        return bonusStartDate;
    }

    public long getBonusEndDate() {
        return bonusEndDate;
    }

    @Override
    public int getId() {
        return id;
    }
}