package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "encore.json")
public class EncoreDef extends BaseDef {
    private int id;

    @SerializedName("feature_id")
    private int featureId;

    private int type;

    @SerializedName("name_text_id")
    private int nameTextId;

    @SerializedName("appearance_time")
    private int appearanceTime;

    @SerializedName("firstclear_reward_id")
    private int firstclearRewardId;

    @SerializedName("rank_s_reward_id")
    private int rankSRewardId;

    @SerializedName("clear_reward_id")
    private int clearRewardId;

    @SerializedName("drop_reward1_id")
    private int dropReward1Id;

    @SerializedName("drop_reward2_id")
    private int dropReward2Id;

    private int exp;

    @SerializedName("card_exp")
    private int cardExp;

    public int getFeatureId() {
        return featureId;
    }

    public int getType() {
        return type;
    }

    public int getNameTextId() {
        return nameTextId;
    }

    public int getAppearanceTime() {
        return appearanceTime;
    }

    public int getFirstclearRewardId() {
        return firstclearRewardId;
    }

    public int getRankSRewardId() {
        return rankSRewardId;
    }

    public int getClearRewardId() {
        return clearRewardId;
    }

    public int getDropReward1Id() {
        return dropReward1Id;
    }

    public int getDropReward2Id() {
        return dropReward2Id;
    }

    public int getExp() {
        return exp;
    }

    public int getCardExp() {
        return cardExp;
    }

    @Override
    public int getId() {
        return id;
    }
}