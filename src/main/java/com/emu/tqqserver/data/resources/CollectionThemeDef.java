package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "collection_theme.json")
public class CollectionThemeDef extends BaseDef {
    private int id;

    private String name;

    @SerializedName("is_default")
    private int isDefault;

    public String getName() {
        return name;
    }

    public int getIsDefault() {
        return isDefault;
    }

    @Override
    public int getId() {
        return id;
    }
}