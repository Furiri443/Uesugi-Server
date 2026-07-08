package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "season_ranking_reward.json")
public class SeasonRankingRewardDef extends BaseDef {
    private int id;

    @SerializedName("season_id")
    private int seasonId;

    private int upper;

    private int lower;

    private String special;

    @SerializedName("reward_id")
    private int rewardId;

    public int getSeasonId() {
        return seasonId;
    }

    public int getUpper() {
        return upper;
    }

    public int getLower() {
        return lower;
    }

    public String getSpecial() {
        return special;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return id;
    }
}