package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "synthesis_recipe_material.json")
public class SynthesisRecipeMaterialDef extends BaseDef {
    private int id;

    @SerializedName("synthesis_recipe_id")
    private int synthesisRecipeId;

    private int type;

    @SerializedName("target_id")
    private int targetId;

    private int count;

    public int getSynthesisRecipeId() {
        return synthesisRecipeId;
    }

    public int getType() {
        return type;
    }

    public int getTargetId() {
        return targetId;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int getId() {
        return id;
    }
}