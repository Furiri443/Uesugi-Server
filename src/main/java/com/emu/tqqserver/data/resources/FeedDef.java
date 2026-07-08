package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feed.json")
public class FeedDef extends BaseDef {
    private int id;

    @SerializedName("message_")
    private String message;

    private int order;

    @SerializedName("min_level")
    private int minLevel;

    @SerializedName("max_level")
    private int maxLevel;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("close_time")
    private int closeTime;

    private int target;

    public String getMessage() {
        return message;
    }

    public int getOrder() {
        return order;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public int getTarget() {
        return target;
    }

    @Override
    public int getId() {
        return id;
    }
}