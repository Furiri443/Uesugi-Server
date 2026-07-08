package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "retryable_stage.json")
public class RetryableStageDef extends BaseDef {
    private int id;

    @SerializedName("group_id")
    private int groupId;

    @SerializedName("seq_id")
    private int seqId;

    private String information;

    @SerializedName("model_id")
    private int modelId;

    @SerializedName("can_continue")
    private int canContinue;

    @SerializedName("clear_score")
    private int clearScore;

    public int getGroupId() {
        return groupId;
    }

    public int getSeqId() {
        return seqId;
    }

    public String getInformation() {
        return information;
    }

    public int getModelId() {
        return modelId;
    }

    public int getCanContinue() {
        return canContinue;
    }

    public int getClearScore() {
        return clearScore;
    }

    @Override
    public int getId() {
        return id;
    }
}