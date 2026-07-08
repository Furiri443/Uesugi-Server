package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "func_introduction.json")
public class FuncIntroductionDef extends BaseDef {
    @SerializedName("group_id")
    private int groupId;

    private int seq;

    private String title;

    private String description;

    @SerializedName("resource_id")
    private int resourceId;

    private int priority;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public int getGroupId() {
        return groupId;
    }

    public int getSeq() {
        return seq;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getPriority() {
        return priority;
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