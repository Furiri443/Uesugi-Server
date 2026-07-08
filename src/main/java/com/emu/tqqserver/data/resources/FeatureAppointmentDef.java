package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_appointment.json")
public class FeatureAppointmentDef extends BaseDef {
    private int id;

    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("switching_time")
    private int switchingTime;

    private int distance;

    public int getFeatureId() {
        return featureId;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getSwitchingTime() {
        return switchingTime;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public int getId() {
        return id;
    }
}