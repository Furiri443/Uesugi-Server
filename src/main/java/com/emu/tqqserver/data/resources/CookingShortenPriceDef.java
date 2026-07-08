package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "cooking_shorten_price.json")
public class CookingShortenPriceDef extends BaseDef {
    private int id;

    @SerializedName("min_range")
    private int minRange;

    @SerializedName("max_range")
    private int maxRange;

    @SerializedName("shorten_price")
    private int shortenPrice;

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getShortenPrice() {
        return shortenPrice;
    }

    @Override
    public int getId() {
        return id;
    }
}