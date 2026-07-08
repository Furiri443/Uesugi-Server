package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "player_title_banner.json")
public class PlayerTitleBannerDef extends BaseDef {
    private int id;

    @SerializedName("bg_color")
    private String bgColor;

    @SerializedName("bg_decoration_color")
    private String bgDecorationColor;

    @SerializedName("name_text_color")
    private String nameTextColor;

    @SerializedName("name_outline_color")
    private String nameOutlineColor;

    private int icon;

    @SerializedName("resource_id")
    private int resourceId;

    public String getBgColor() {
        return bgColor;
    }

    public String getBgDecorationColor() {
        return bgDecorationColor;
    }

    public String getNameTextColor() {
        return nameTextColor;
    }

    public String getNameOutlineColor() {
        return nameOutlineColor;
    }

    public int getIcon() {
        return icon;
    }

    public int getResourceId() {
        return resourceId;
    }

    @Override
    public int getId() {
        return id;
    }
}