package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "encore_fever.json")
public class EncoreFeverDef extends BaseDef {
    private int id;

    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("open_time")
    private int openTime;

    @SerializedName("close_time")
    private int closeTime;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private int closeDate;

    public int getFeatureId() {
        return featureId;
    }

    public int getOpenTime() {
        return openTime;
    }

    public int getCloseTime() {
        return closeTime;
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