package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card_picture_release.json")
public class CardPictureReleaseDef extends BaseDef {
    private int id;

    private int awakening;

    @SerializedName("limit_state1")
    private int limitState1;

    public int getAwakening() {
        return awakening;
    }

    public int getLimitState1() {
        return limitState1;
    }

    @Override
    public int getId() {
        return id;
    }
}