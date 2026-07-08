package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_nakayoshi_total_level_reward.json")
public class FeatureNakayoshiTotalLevelRewardDef extends BaseDef {
    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("total_level")
    private int totalLevel;

    @SerializedName("reward_id")
    private int rewardId;

    public int getFeatureId() {
        return featureId;
    }

    public int getTotalLevel() {
        return totalLevel;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return 0;
    }
}