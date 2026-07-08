package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "cooking_visiting_member_probability.json")
public class CookingVisitingMemberProbabilityDef extends BaseDef {
    private int id;

    private int rank;

    @SerializedName("member_id")
    private int memberId;

    private int probability;

    @SerializedName("cooking_visiting_member_cloth_id")
    private int cookingVisitingMemberClothId;

    public int getRank() {
        return rank;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getProbability() {
        return probability;
    }

    public int getCookingVisitingMemberClothId() {
        return cookingVisitingMemberClothId;
    }

    @Override
    public int getId() {
        return id;
    }
}