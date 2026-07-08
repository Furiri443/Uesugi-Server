package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "ep_fever.json")
public class EpFeverDef extends BaseDef {
    private int id;

    @SerializedName("feature_id")
    private int featureId;

    private int level;

    private int rating;

    private int boost;

    private int duration;

    public int getFeatureId() {
        return featureId;
    }

    public int getLevel() {
        return level;
    }

    public int getRating() {
        return rating;
    }

    public int getBoost() {
        return boost;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int getId() {
        return id;
    }
}