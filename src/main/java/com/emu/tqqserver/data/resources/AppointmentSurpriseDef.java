package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "appointment_surprise.json")
public class AppointmentSurpriseDef extends BaseDef {
    @SerializedName("member_id")
    private int memberId;

    private int level;

    @SerializedName("reward_id")
    private int rewardId;

    public int getMemberId() {
        return memberId;
    }

    public int getLevel() {
        return level;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return 0;
    }
}