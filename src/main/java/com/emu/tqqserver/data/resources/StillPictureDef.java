package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "still_picture.json")
public class StillPictureDef extends BaseDef {
    private int id;

    private String label;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("thumbnail_resource_id")
    private int thumbnailResourceId;

    public String getLabel() {
        return label;
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