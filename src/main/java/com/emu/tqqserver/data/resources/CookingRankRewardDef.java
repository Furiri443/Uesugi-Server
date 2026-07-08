package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "cooking_rank_reward.json")
public class CookingRankRewardDef extends BaseDef {
    private int id;

    private int rank;

    @SerializedName("reward_id")
    private int rewardId;

    public int getRank() {
        return rank;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return id;
    }
}