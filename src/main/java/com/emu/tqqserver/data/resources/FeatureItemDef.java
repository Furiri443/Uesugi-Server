package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_item.json")
public class FeatureItemDef extends BaseDef {
    private int id;

    @SerializedName("sort_id")
    private int sortId;

    private String name;

    private String description;

    private int value;

    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("resource_id")
    private int resourceId;

    private int type;

    public int getSortId() {
        return sortId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public int getFeatureId() {
        return featureId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getType() {
        return type;
    }

    @Override
    public int getId() {
        return id;
    }
}