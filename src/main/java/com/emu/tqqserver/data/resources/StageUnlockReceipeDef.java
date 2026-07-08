package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "stage_unlock_receipe.json")
public class StageUnlockReceipeDef extends BaseDef {
    private int id;

    private int material1;

    private int value1;

    @SerializedName("master_type")
    private int masterType;

    public int getMaterial1() {
        return material1;
    }

    public int getValue1() {
        return value1;
    }

    public int getMasterType() {
        return masterType;
    }

    @Override
    public int getId() {
        return id;
    }
}