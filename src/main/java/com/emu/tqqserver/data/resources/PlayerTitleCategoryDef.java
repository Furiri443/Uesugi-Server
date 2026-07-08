package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "player_title_category.json")
public class PlayerTitleCategoryDef extends BaseDef {
    private int category;

    private String name;

    @SerializedName("banner_type")
    private int bannerType;

    @SerializedName("icon_type")
    private int iconType;

    public int getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getBannerType() {
        return bannerType;
    }

    public int getIconType() {
        return iconType;
    }

    @Override
    public int getId() {
        return 0;
    }
}