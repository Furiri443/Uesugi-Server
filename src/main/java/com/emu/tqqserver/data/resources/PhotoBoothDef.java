package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "photo_booth.json")
public class PhotoBoothDef extends BaseDef {
    private int id;

    private String label;

    private int type;

    private int priority;

    @SerializedName("tab_num")
    private int tabNum;

    @SerializedName("tab_name")
    private String tabName;

    @SerializedName("success_duration")
    private int successDuration;

    @SerializedName("failure_duration")
    private int failureDuration;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("resource_name")
    private String resourceName;

    @SerializedName("thumbnail_resource_id")
    private int thumbnailResourceId;

    @SerializedName("is_default")
    private int isDefault;

    public String getLabel() {
        return label;
    }

    public int getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    public int getTabNum() {
        return tabNum;
    }

    public String getTabName() {
        return tabName;
    }

    public int getSuccessDuration() {
        return successDuration;
    }

    public int getFailureDuration() {
        return failureDuration;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public int getThumbnailResourceId() {
        return thumbnailResourceId;
    }

    public int getIsDefault() {
        return isDefault;
    }

    @Override
    public int getId() {
        return id;
    }
}