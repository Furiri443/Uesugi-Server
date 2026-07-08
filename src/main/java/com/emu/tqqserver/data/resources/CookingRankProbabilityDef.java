package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "cooking_rank_probability.json")
public class CookingRankProbabilityDef extends BaseDef {
    private int id;

    private int weight1;

    private int weight2;

    private int weight3;

    private int weight4;

    private int weight5;

    @SerializedName("rank_gauge")
    private int rankGauge;

    private int proficiency;

    public int getWeight1() {
        return weight1;
    }

    public int getWeight2() {
        return weight2;
    }

    public int getWeight3() {
        return weight3;
    }

    public int getWeight4() {
        return weight4;
    }

    public int getWeight5() {
        return weight5;
    }

    public int getRankGauge() {
        return rankGauge;
    }

    public int getProficiency() {
        return proficiency;
    }

    @Override
    public int getId() {
        return id;
    }
}