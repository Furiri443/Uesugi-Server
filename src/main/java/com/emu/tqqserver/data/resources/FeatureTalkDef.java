package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_talk.json")
public class FeatureTalkDef extends BaseDef {
    private int id;

    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("message_")
    private String message;

    @SerializedName("chapter_id")
    private int chapterId;

    public int getFeatureId() {
        return featureId;
    }

    public String getMessage() {
        return message;
    }

    public int getChapterId() {
        return chapterId;
    }

    @Override
    public int getId() {
        return id;
    }
}