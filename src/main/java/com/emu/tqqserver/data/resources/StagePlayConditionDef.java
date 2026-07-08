package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "stage_play_condition.json")
public class StagePlayConditionDef extends BaseDef {
    private int id;

    private int type;

    @SerializedName("target_id")
    private int targetId;

    @SerializedName("target_value")
    private int targetValue;

    public int getType() {
        return type;
    }

    public int getTargetId() {
        return targetId;
    }

    public int getTargetValue() {
        return targetValue;
    }

    @Override
    public int getId() {
        return id;
    }
}