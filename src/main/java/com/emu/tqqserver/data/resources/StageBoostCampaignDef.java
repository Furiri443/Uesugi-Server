package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "stage_boost_campaign.json")
public class StageBoostCampaignDef extends BaseDef {
    private int id;

    @SerializedName("stage_type")
    private int stageType;

    @SerializedName("stage_id")
    private int stageId;

    private int type;

    private String title;

    @SerializedName("effect_label")
    private String effectLabel;

    @SerializedName("effect_value")
    private float effectValue;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private int closeDate;

    public int getStageType() {
        return stageType;
    }

    public int getStageId() {
        return stageId;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getEffectLabel() {
        return effectLabel;
    }

    public float getEffectValue() {
        return effectValue;
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