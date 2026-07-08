package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "level_growth.json")
public class LevelGrowthDef extends BaseDef {
    private int id;

    private int level;

    private int value;

    public int getLevel() {
        return level;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int getId() {
        return id;
    }
}