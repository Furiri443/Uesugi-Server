package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_selection.json")
public class FeatureSelectionDef extends BaseDef {
    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("target_id")
    private int targetId;

    public int getFeatureId() {
        return featureId;
    }

    public int getTargetId() {
        return targetId;
    }

    @Override
    public int getId() {
        return 0;
    }
}