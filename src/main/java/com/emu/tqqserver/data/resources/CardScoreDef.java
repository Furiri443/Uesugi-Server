package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card_score.json")
public class CardScoreDef extends BaseDef {
    private int id;

    @SerializedName("min_value")
    private int minValue;

    @SerializedName("max_value")
    private int maxValue;

    private float coefficient;

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public float getCoefficient() {
        return coefficient;
    }

    @Override
    public int getId() {
        return id;
    }
}