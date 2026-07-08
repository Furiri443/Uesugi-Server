package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "cooking_proficiency_level.json")
public class CookingProficiencyLevelDef extends BaseDef {
    private int id;

    private int level;

    @SerializedName("cooking_count")
    private int cookingCount;

    public int getLevel() {
        return level;
    }

    public int getCookingCount() {
        return cookingCount;
    }

    @Override
    public int getId() {
        return id;
    }
}