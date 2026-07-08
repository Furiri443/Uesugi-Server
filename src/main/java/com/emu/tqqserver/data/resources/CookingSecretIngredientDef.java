package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "cooking_secret_ingredient.json")
public class CookingSecretIngredientDef extends BaseDef {
    @SerializedName("item_id")
    private int itemId;

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("additional_visiting_probability")
    private int additionalVisitingProbability;

    @SerializedName("campaign_additional_visiting_probability")
    private int campaignAdditionalVisitingProbability;

    @SerializedName("campaign_open_date")
    private long campaignOpenDate;

    @SerializedName("campaign_close_date")
    private long campaignCloseDate;

    public int getItemId() {
        return itemId;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getAdditionalVisitingProbability() {
        return additionalVisitingProbability;
    }

    public int getCampaignAdditionalVisitingProbability() {
        return campaignAdditionalVisitingProbability;
    }

    public long getCampaignOpenDate() {
        return campaignOpenDate;
    }

    public long getCampaignCloseDate() {
        return campaignCloseDate;
    }

    @Override
    public int getId() {
        return 0;
    }
}