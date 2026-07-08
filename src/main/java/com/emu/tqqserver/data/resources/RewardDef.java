package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "reward.json")
public class RewardDef extends BaseDef {
    @SerializedName("seq_id")
    private int seqId;

    @SerializedName("name_text_id")
    private int nameTextId;

    private int quantity;

    private int weight;

    @SerializedName("reward_card_property_id")
    private int rewardCardPropertyId;

    private int id;

    private int type;

    @SerializedName("reward_id")
    private int rewardId;

    private int vip;

    public int getSeqId() {
        return seqId;
    }

    public int getNameTextId() {
        return nameTextId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getWeight() {
        return weight;
    }

    public int getRewardCardPropertyId() {
        return rewardCardPropertyId;
    }

    public int getType() {
        return type;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getVip() {
        return vip;
    }

    @Override
    public int getId() {
        return id;
    }
}