package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "cooking_recipe_material.json")
public class CookingRecipeMaterialDef extends BaseDef {
    @SerializedName("cooking_recipe_id")
    private int cookingRecipeId;

    @SerializedName("item_id")
    private int itemId;

    private int quantity;

    public int getCookingRecipeId() {
        return cookingRecipeId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int getId() {
        return 0;
    }
}