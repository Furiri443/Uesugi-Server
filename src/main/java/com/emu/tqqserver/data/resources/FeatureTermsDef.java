package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_terms.json")
public class FeatureTermsDef extends BaseDef {
    @SerializedName("feature_id")
    private int featureId;

    private String overview;

    private String description;

    public int getFeatureId() {
        return featureId;
    }

    public String getOverview() {
        return overview;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getId() {
        return 0;
    }
}