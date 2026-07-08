package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "retryable_chapter.json")
public class RetryableChapterDef extends BaseDef {
    private int id;

    @SerializedName("name_text_id")
    private int nameTextId;

    private String description;

    private String detail;

    @SerializedName("model_id")
    private int modelId;

    @SerializedName("show_reward")
    private int showReward;

    @SerializedName("top_resource_id")
    private int topResourceId;

    @SerializedName("bg_resource_id")
    private int bgResourceId;

    @SerializedName("bg_model_resource_id")
    private int bgModelResourceId;

    @SerializedName("icon_resource_id")
    private int iconResourceId;

    @SerializedName("bar_color_text_id")
    private int barColorTextId;

    @SerializedName("position_tmpl_id")
    private int positionTmplId;

    @SerializedName("quest_id")
    private int questId;

    @SerializedName("fail_back")
    private int failBack;

    @SerializedName("clear_reward_id")
    private int clearRewardId;

    private int weekday;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public int getNameTextId() {
        return nameTextId;
    }

    public String getDescription() {
        return description;
    }

    public String getDetail() {
        return detail;
    }

    public int getModelId() {
        return modelId;
    }

    public int getShowReward() {
        return showReward;
    }

    public int getTopResourceId() {
        return topResourceId;
    }

    public int getBgResourceId() {
        return bgResourceId;
    }

    public int getBgModelResourceId() {
        return bgModelResourceId;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public int getBarColorTextId() {
        return barColorTextId;
    }

    public int getPositionTmplId() {
        return positionTmplId;
    }

    public int getQuestId() {
        return questId;
    }

    public int getFailBack() {
        return failBack;
    }

    public int getClearRewardId() {
        return clearRewardId;
    }

    public int getWeekday() {
        return weekday;
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