package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "count_gacha_reward.json")
public class CountGachaRewardDef extends BaseDef {
    @SerializedName("gacha_id")
    private int gachaId;

    private int count;

    @SerializedName("reward_id")
    private int rewardId;

    public int getGachaId() {
        return gachaId;
    }

    public int getCount() {
        return count;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return 0;
    }
}