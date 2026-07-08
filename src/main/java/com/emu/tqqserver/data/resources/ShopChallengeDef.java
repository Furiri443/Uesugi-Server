package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "shop_challenge.json")
public class ShopChallengeDef extends BaseDef {
    @SerializedName("shop_id")
    private int shopId;

    private int priority;

    @SerializedName("challenge_group_id")
    private int challengeGroupId;

    @SerializedName("limit_day")
    private int limitDay;

    public int getShopId() {
        return shopId;
    }

    public int getPriority() {
        return priority;
    }

    public int getChallengeGroupId() {
        return challengeGroupId;
    }

    public int getLimitDay() {
        return limitDay;
    }

    @Override
    public int getId() {
        return 0;
    }
}