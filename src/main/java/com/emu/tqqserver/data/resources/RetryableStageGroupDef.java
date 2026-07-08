package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "retryable_stage_group.json")
public class RetryableStageGroupDef extends BaseDef {
    private int id;

    private String name;

    @SerializedName("chapter_id")
    private int chapterId;

    private int exp;

    @SerializedName("card_exp")
    private int cardExp;

    @SerializedName("clear_reward_id")
    private int clearRewardId;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("close_date")
    private long closeDate;

    public String getName() {
        return name;
    }

    public int getChapterId() {
        return chapterId;
    }

    public int getExp() {
        return exp;
    }

    public int getCardExp() {
        return cardExp;
    }

    public int getClearRewardId() {
        return clearRewardId;
    }

    public long getOpenDate() {
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