package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "vip_continue_reward.json")
public class VipContinueRewardDef extends BaseDef {
    private int rank;

    private int count;

    @SerializedName("reward_id")
    private int rewardId;

    public int getRank() {
        return rank;
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