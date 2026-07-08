package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "work.json")
public class WorkDef extends BaseDef {
    private int id;

    private int type;

    private int level;

    private String title;

    @SerializedName("condition_type1")
    private int conditionType1;

    @SerializedName("condition_id_1")
    private int conditionId1;

    @SerializedName("result_base_weight2")
    private int resultBaseWeight2;

    @SerializedName("result_base_weight3")
    private int resultBaseWeight3;

    @SerializedName("need_time")
    private int needTime;

    private int leaf;

    @SerializedName("shorten_price")
    private int shortenPrice;

    @SerializedName("reward_id_1")
    private int rewardId1;

    @SerializedName("reward_id_2")
    private int rewardId2;

    @SerializedName("reward_id_3")
    private int rewardId3;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("condition_type2")
    private int conditionType2;

    @SerializedName("condition_id_2")
    private int conditionId2;

    public int getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public String getTitle() {
        return title;
    }

    public int getConditionType1() {
        return conditionType1;
    }

    public int getConditionId1() {
        return conditionId1;
    }

    public int getResultBaseWeight2() {
        return resultBaseWeight2;
    }

    public int getResultBaseWeight3() {
        return resultBaseWeight3;
    }

    public int getNeedTime() {
        return needTime;
    }

    public int getLeaf() {
        return leaf;
    }

    public int getShortenPrice() {
        return shortenPrice;
    }

    public int getRewardId1() {
        return rewardId1;
    }

    public int getRewardId2() {
        return rewardId2;
    }

    public int getRewardId3() {
        return rewardId3;
    }

    public long getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getConditionType2() {
        return conditionType2;
    }

    public int getConditionId2() {
        return conditionId2;
    }

    @Override
    public int getId() {
        return id;
    }
}