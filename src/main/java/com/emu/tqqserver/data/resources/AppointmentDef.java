package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "appointment.json")
public class AppointmentDef extends BaseDef {
    @SerializedName("wait_group")
    private int waitGroup;

    @SerializedName("late_group")
    private int lateGroup;

    @SerializedName("reward1_id")
    private int reward1Id;

    @SerializedName("reward2_id")
    private int reward2Id;

    public int getWaitGroup() {
        return waitGroup;
    }

    public int getLateGroup() {
        return lateGroup;
    }

    public int getReward1Id() {
        return reward1Id;
    }

    public int getReward2Id() {
        return reward2Id;
    }

    @Override
    public int getId() {
        return 0;
    }
}