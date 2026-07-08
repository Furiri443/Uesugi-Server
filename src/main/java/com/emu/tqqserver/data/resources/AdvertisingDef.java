package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "advertising.json")
public class AdvertisingDef extends BaseDef {
    private int id;

    @SerializedName("ad_type")
    private int adType;

    @SerializedName("view_count_limit")
    private int viewCountLimit;

    @SerializedName("reset_type")
    private int resetType;

    @SerializedName("reward_count")
    private int rewardCount;

    private String comment;

    @SerializedName("start_date")
    private int startDate;

    @SerializedName("end_date")
    private long endDate;

    @SerializedName("target_id")
    private int targetId;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("view_interval")
    private int viewInterval;

    public int getAdType() {
        return adType;
    }

    public int getViewCountLimit() {
        return viewCountLimit;
    }

    public int getResetType() {
        return resetType;
    }

    public int getRewardCount() {
        return rewardCount;
    }

    public String getComment() {
        return comment;
    }

    public int getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public int getTargetId() {
        return targetId;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getViewInterval() {
        return viewInterval;
    }

    @Override
    public int getId() {
        return id;
    }
}