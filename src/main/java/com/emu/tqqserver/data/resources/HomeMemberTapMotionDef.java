package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "home_member_tap_motion.json")
public class HomeMemberTapMotionDef extends BaseDef {
    private int id;

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("clothes_id")
    private int clothesId;

    private String name;

    @SerializedName("thumbnail_resource_id")
    private int thumbnailResourceId;

    @SerializedName("clothes_casual")
    private int clothesCasual;

    @SerializedName("clothes_kimono")
    private int clothesKimono;

    @SerializedName("clothes_dress")
    private int clothesDress;

    @SerializedName("is_default")
    private int isDefault;

    private int type;

    public int getMemberId() {
        return memberId;
    }

    public int getClothesId() {
        return clothesId;
    }

    public String getName() {
        return name;
    }

    public int getThumbnailResourceId() {
        return thumbnailResourceId;
    }

    public int getClothesCasual() {
        return clothesCasual;
    }

    public int getClothesKimono() {
        return clothesKimono;
    }

    public int getClothesDress() {
        return clothesDress;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public int getType() {
        return type;
    }

    @Override
    public int getId() {
        return id;
    }
}