package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_period.json")
public class FeaturePeriodDef extends BaseDef {
    private int id;

    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("top_resource_id")
    private int topResourceId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private int closeDate;

    public int getFeatureId() {
        return featureId;
    }

    public int getTopResourceId() {
        return topResourceId;
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