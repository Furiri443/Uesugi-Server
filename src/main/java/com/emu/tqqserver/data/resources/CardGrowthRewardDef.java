package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card_growth_reward.json")
public class CardGrowthRewardDef extends BaseDef {
    @SerializedName("group_id")
    private int groupId;

    @SerializedName("seq_id")
    private int seqId;

    @SerializedName("card_lv")
    private int cardLv;

    @SerializedName("awakening_type")
    private int awakeningType;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("publish_date")
    private int publishDate;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public int getGroupId() {
        return groupId;
    }

    public int getSeqId() {
        return seqId;
    }

    public int getCardLv() {
        return cardLv;
    }

    public int getAwakeningType() {
        return awakeningType;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getPublishDate() {
        return publishDate;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    @Override
    public int getId() {
        return 0;
    }
}