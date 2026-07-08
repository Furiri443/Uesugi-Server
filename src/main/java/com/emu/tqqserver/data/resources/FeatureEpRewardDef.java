package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_ep_reward.json")
public class FeatureEpRewardDef extends BaseDef {
    private int id;

    @SerializedName("feature_id")
    private int featureId;

    private int ep;

    @SerializedName("reward_id")
    private int rewardId;

    private int type;

    @SerializedName("loop_ep")
    private int loopEp;

    private int revival;

    private int category;

    public int getFeatureId() {
        return featureId;
    }

    public int getEp() {
        return ep;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getType() {
        return type;
    }

    public int getLoopEp() {
        return loopEp;
    }

    public int getRevival() {
        return revival;
    }

    public int getCategory() {
        return category;
    }

    @Override
    public int getId() {
        return id;
    }
}