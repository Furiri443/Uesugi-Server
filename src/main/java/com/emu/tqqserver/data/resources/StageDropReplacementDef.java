package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "stage_drop_replacement.json")
public class StageDropReplacementDef extends BaseDef {
    private int id;

    @SerializedName("stage_id")
    private int stageId;

    private int target;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private int closeDate;

    public int getStageId() {
        return stageId;
    }

    public int getTarget() {
        return target;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getOpenDate() {
        return openDate;
    }

    public int getCloseDate() {
        return closeDate;
    }

    @Override
    public int getId() {
        return id;
    }
}