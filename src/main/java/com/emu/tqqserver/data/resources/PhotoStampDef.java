package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "photo_stamp.json")
public class PhotoStampDef extends BaseDef {
    private int id;

    private String label;

    private int priority;

    @SerializedName("tab_num")
    private int tabNum;

    @SerializedName("tab_name")
    private String tabName;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("thumbnail_resource_id")
    private int thumbnailResourceId;

    @SerializedName("is_default")
    private int isDefault;

    public String getLabel() {
        return label;
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

    public int getResourceId() {
        return resourceId;
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