package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "app_icon.json")
public class AppIconDef extends BaseDef {
    private int id;

    private int priority;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("open_flg")
    private int openFlg;

    @SerializedName("publish_start_date")
    private int publishStartDate;

    @SerializedName("publish_end_date")
    private long publishEndDate;

    public int getPriority() {
        return priority;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getOpenFlg() {
        return openFlg;
    }

    public int getPublishStartDate() {
        return publishStartDate;
    }

    public long getPublishEndDate() {
        return publishEndDate;
    }

    @Override
    public int getId() {
        return id;
    }
}