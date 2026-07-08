package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "home_member_clothes.json")
public class HomeMemberClothesDef extends BaseDef {
    private int id;

    @SerializedName("member_id")
    private int memberId;

    private String name;

    @SerializedName("thumbnail_resource_id")
    private int thumbnailResourceId;

    @SerializedName("is_default")
    private int isDefault;

    @SerializedName("card_id")
    private int cardId;

    private int type;

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public int getThumbnailResourceId() {
        return thumbnailResourceId;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public int getCardId() {
        return cardId;
    }

    public int getType() {
        return type;
    }

    @Override
    public int getId() {
        return id;
    }
}