package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "home_background.json")
public class HomeBackgroundDef extends BaseDef {
    private int id;

    private String label;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("day_resource_id")
    private int dayResourceId;

    @SerializedName("thumbnail_resource_id")
    private int thumbnailResourceId;

    @SerializedName("is_default")
    private int isDefault;

    @SerializedName("night_resource_id")
    private int nightResourceId;

    public String getLabel() {
        return label;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getDayResourceId() {
        return dayResourceId;
    }

    public int getThumbnailResourceId() {
        return thumbnailResourceId;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public int getNightResourceId() {
        return nightResourceId;
    }

    @Override
    public int getId() {
        return id;
    }
}