package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_coupon.json")
public class FeatureCouponDef extends BaseDef {
    private int id;

    @SerializedName("feature_id")
    private int featureId;

    private String name;

    private String description;

    @SerializedName("daily_limit")
    private int dailyLimit;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("thumbnail_resource_id")
    private int thumbnailResourceId;

    public int getFeatureId() {
        return featureId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getThumbnailResourceId() {
        return thumbnailResourceId;
    }

    @Override
    public int getId() {
        return id;
    }
}