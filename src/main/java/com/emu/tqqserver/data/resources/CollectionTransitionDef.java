package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "collection_transition.json")
public class CollectionTransitionDef extends BaseDef {
    private int id;

    private String name;

    @SerializedName("thumbnail_resource_id")
    private int thumbnailResourceId;

    @SerializedName("effect_id")
    private int effectId;

    @SerializedName("is_default")
    private int isDefault;

    public String getName() {
        return name;
    }

    public int getThumbnailResourceId() {
        return thumbnailResourceId;
    }

    public int getEffectId() {
        return effectId;
    }

    public int getIsDefault() {
        return isDefault;
    }

    @Override
    public int getId() {
        return id;
    }
}