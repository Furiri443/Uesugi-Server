package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "quest.json")
public class QuestDef extends BaseDef {
    private int id;

    @SerializedName("name_text_id")
    private int nameTextId;

    @SerializedName("description_text_id")
    private int descriptionTextId;

    private int type;

    @SerializedName("list_type")
    private int listType;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("model_id")
    private int modelId;

    @SerializedName("bg_resource_id")
    private int bgResourceId;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("clothes_id")
    private int clothesId;

    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("character_id")
    private int characterId;

    public int getNameTextId() {
        return nameTextId;
    }

    public int getDescriptionTextId() {
        return descriptionTextId;
    }

    public int getType() {
        return type;
    }

    public int getListType() {
        return listType;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getModelId() {
        return modelId;
    }

    public int getBgResourceId() {
        return bgResourceId;
    }

    public long getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getClothesId() {
        return clothesId;
    }

    public int getFeatureId() {
        return featureId;
    }

    public int getCharacterId() {
        return characterId;
    }

    @Override
    public int getId() {
        return id;
    }
}