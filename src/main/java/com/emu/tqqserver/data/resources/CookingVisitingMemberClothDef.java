package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "cooking_visiting_member_cloth.json")
public class CookingVisitingMemberClothDef extends BaseDef {
    private int id;

    @SerializedName("cloth_id")
    private int clothId;

    private int weight;

    @SerializedName("reward_id")
    private int rewardId;

    public int getClothId() {
        return clothId;
    }

    public int getWeight() {
        return weight;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return id;
    }
}