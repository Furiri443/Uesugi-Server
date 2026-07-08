package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "promotion.json")
public class PromotionDef extends BaseDef {
    private int id;

    private String ios;

    private String android;

    private String pc;

    private int coin;

    public String getIos() {
        return ios;
    }

    public String getAndroid() {
        return android;
    }

    public String getPc() {
        return pc;
    }

    public int getCoin() {
        return coin;
    }

    @Override
    public int getId() {
        return id;
    }
}