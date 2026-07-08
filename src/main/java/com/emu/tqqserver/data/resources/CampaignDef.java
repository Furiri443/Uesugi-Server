package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "campaign.json")
public class CampaignDef extends BaseDef {
    private int id;

    private int type;

    @SerializedName("target_user_type")
    private int targetUserType;

    private String title;

    @SerializedName("effect_label")
    private String effectLabel;

    @SerializedName("effect_value")
    private float effectValue;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public int getType() {
        return type;
    }

    public int getTargetUserType() {
        return targetUserType;
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

    public long getCloseDate() {
        return closeDate;
    }

    @Override
    public int getId() {
        return id;
    }
}