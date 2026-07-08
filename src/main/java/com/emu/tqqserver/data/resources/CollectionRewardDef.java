package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "collection_reward.json")
public class CollectionRewardDef extends BaseDef {
    private int id;

    private int category;

    @SerializedName("start_value")
    private int startValue;

    @SerializedName("end_value")
    private int endValue;

    @SerializedName("step_value")
    private int stepValue;

    @SerializedName("reward_id")
    private int rewardId;

    public int getCategory() {
        return category;
    }

    public int getStartValue() {
        return startValue;
    }

    public int getEndValue() {
        return endValue;
    }

    public int getStepValue() {
        return stepValue;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return id;
    }
}