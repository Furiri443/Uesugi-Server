package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "login_bonus.json")
public class LoginBonusDef extends BaseDef {
    private int id;

    @SerializedName("seq_id")
    private int seqId;

    private int type;

    @SerializedName("title_text_id")
    private int titleTextId;

    @SerializedName("disable_period")
    private int disablePeriod;

    @SerializedName("banner_resource_id")
    private int bannerResourceId;

    @SerializedName("reaction_scene_id")
    private int reactionSceneId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    private int interval;

    public int getSeqId() {
        return seqId;
    }

    public int getType() {
        return type;
    }

    public int getTitleTextId() {
        return titleTextId;
    }

    public int getDisablePeriod() {
        return disablePeriod;
    }

    public int getBannerResourceId() {
        return bannerResourceId;
    }

    public int getReactionSceneId() {
        return reactionSceneId;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getInterval() {
        return interval;
    }

    @Override
    public int getId() {
        return id;
    }
}