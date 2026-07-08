package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_tutorial.json")
public class FeatureTutorialDef extends BaseDef {
    @SerializedName("feature_id")
    private int featureId;

    private int seq;

    private String cmd;

    private String val1;

    private String val2;

    public int getFeatureId() {
        return featureId;
    }

    public int getSeq() {
        return seq;
    }

    public String getCmd() {
        return cmd;
    }

    public String getVal1() {
        return val1;
    }

    public String getVal2() {
        return val2;
    }

    @Override
    public int getId() {
        return 0;
    }
}