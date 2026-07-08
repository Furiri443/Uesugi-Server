package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card_rarity.json")
public class CardRarityDef extends BaseDef {
    private int rarity;

    @SerializedName("sell_leaf")
    private int sellLeaf;

    @SerializedName("sell_item_id")
    private int sellItemId;

    @SerializedName("sell_item_quantity")
    private int sellItemQuantity;

    public int getRarity() {
        return rarity;
    }

    public int getSellLeaf() {
        return sellLeaf;
    }

    public int getSellItemId() {
        return sellItemId;
    }

    public int getSellItemQuantity() {
        return sellItemQuantity;
    }

    @Override
    public int getId() {
        return 0;
    }
}