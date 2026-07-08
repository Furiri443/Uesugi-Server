package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "home_member_picture.json")
public class HomeMemberPictureDef extends BaseDef {
    private int id;

    private String label;

    @SerializedName("target_type")
    private int targetType;

    @SerializedName("target_id")
    private String targetId;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("thumbnail_resource_id")
    private int thumbnailResourceId;

    @SerializedName("emotion_resource_ids")
    private String emotionResourceIds;

    public String getLabel() {
        return label;
    }

    public int getTargetType() {
        return targetType;
    }

    public String getTargetId() {
        return targetId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getThumbnailResourceId() {
        return thumbnailResourceId;
    }

    public String getEmotionResourceIds() {
        return emotionResourceIds;
    }

    @Override
    public int getId() {
        return id;
    }
}