package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "gacha_challenge.json")
public class GachaChallengeDef extends BaseDef {
    private int id;

    @SerializedName("gacha_id")
    private int gachaId;

    public int getGachaId() {
        return gachaId;
    }

    @Override
    public int getId() {
        return id;
    }
}