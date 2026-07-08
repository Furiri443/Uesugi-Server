package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "cooking_recipe.json")
public class CookingRecipeDef extends BaseDef {
    private int id;

    @SerializedName("icon_resource_id")
    private int iconResourceId;

    private String name;

    private String description;

    private int rarity;

    @SerializedName("wait_time")
    private int waitTime;

    @SerializedName("shorten_price")
    private int shortenPrice;

    @SerializedName("cooking_proficiency_level_id")
    private int cookingProficiencyLevelId;

    @SerializedName("cooking_visiting_member_probability_id")
    private int cookingVisitingMemberProbabilityId;

    @SerializedName("cooking_rank_reward_id")
    private int cookingRankRewardId;

    @SerializedName("cooking_rank_achievement_reward_id")
    private int cookingRankAchievementRewardId;

    @SerializedName("cooking_rank_probability_id")
    private int cookingRankProbabilityId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public int getIconResourceId() {
        return iconResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRarity() {
        return rarity;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getShortenPrice() {
        return shortenPrice;
    }

    public int getCookingProficiencyLevelId() {
        return cookingProficiencyLevelId;
    }

    public int getCookingVisitingMemberProbabilityId() {
        return cookingVisitingMemberProbabilityId;
    }

    public int getCookingRankRewardId() {
        return cookingRankRewardId;
    }

    public int getCookingRankAchievementRewardId() {
        return cookingRankAchievementRewardId;
    }

    public int getCookingRankProbabilityId() {
        return cookingRankProbabilityId;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    @Override
    public int getId() {
        return id;
    }
}