package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "home_message.json")
public class HomeMessageDef extends BaseDef {
    private int id;

    private int type;

    @SerializedName("target_id")
    private int targetId;

    @SerializedName("message_text_id")
    private int messageTextId;

    @SerializedName("cond_type")
    private int condType;

    @SerializedName("cond_start_time")
    private int condStartTime;

    @SerializedName("cond_end_time")
    private int condEndTime;

    @SerializedName("cond_start_date")
    private int condStartDate;

    @SerializedName("cond_end_date")
    private long condEndDate;

    @SerializedName("resource_id")
    private String resourceId;

    private int weight;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public int getType() {
        return type;
    }

    public int getTargetId() {
        return targetId;
    }

    public int getMessageTextId() {
        return messageTextId;
    }

    public int getCondType() {
        return condType;
    }

    public int getCondStartTime() {
        return condStartTime;
    }

    public int getCondEndTime() {
        return condEndTime;
    }

    public int getCondStartDate() {
        return condStartDate;
    }

    public long getCondEndDate() {
        return condEndDate;
    }

    public String getResourceId() {
        return resourceId;
    }

    public int getWeight() {
        return weight;
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