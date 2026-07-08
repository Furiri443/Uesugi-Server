package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_nakayoshi_level_condition.json")
public class FeatureNakayoshiLevelConditionDef extends BaseDef {
    @SerializedName("feature_id")
    private int featureId;

    private int level;

    @SerializedName("required_total_level")
    private int requiredTotalLevel;

    public int getFeatureId() {
        return featureId;
    }

    public int getLevel() {
        return level;
    }

    public int getRequiredTotalLevel() {
        return requiredTotalLevel;
    }

    @Override
    public int getId() {
        return 0;
    }
}