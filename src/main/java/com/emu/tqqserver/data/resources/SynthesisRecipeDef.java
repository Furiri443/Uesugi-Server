package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "synthesis_recipe.json")
public class SynthesisRecipeDef extends BaseDef {
    private int id;

    @SerializedName("group_id")
    private int groupId;

    private int type;

    @SerializedName("target_id")
    private int targetId;

    @SerializedName("tab_id")
    private int tabId;

    private int count;

    private int leaf;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public int getGroupId() {
        return groupId;
    }

    public int getType() {
        return type;
    }

    public int getTargetId() {
        return targetId;
    }

    public int getTabId() {
        return tabId;
    }

    public int getCount() {
        return count;
    }

    public int getLeaf() {
        return leaf;
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