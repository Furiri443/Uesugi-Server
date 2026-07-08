package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "stage_ex_conditions.json")
public class StageExConditionsDef extends BaseDef {
    @SerializedName("stage_ex_id")
    private int stageExId;

    @SerializedName("stage_id")
    private int stageId;

    public int getStageExId() {
        return stageExId;
    }

    public int getStageId() {
        return stageId;
    }

    @Override
    public int getId() {
        return 0;
    }
}