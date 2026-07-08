package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "lottery.json")
public class LotteryDef extends BaseDef {
    private int id;

    private int priority;

    private String title;

    @SerializedName("item_id")
    private int itemId;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("news_id")
    private int newsId;

    private String term;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("application_end_date")
    private int applicationEndDate;

    @SerializedName("lottery_result_date")
    private int lotteryResultDate;

    @SerializedName("close_date")
    private int closeDate;

    public int getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public int getItemId() {
        return itemId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getNewsId() {
        return newsId;
    }

    public String getTerm() {
        return term;
    }

    public int getOpenDate() {
        return openDate;
    }

    public int getApplicationEndDate() {
        return applicationEndDate;
    }

    public int getLotteryResultDate() {
        return lotteryResultDate;
    }

    public int getCloseDate() {
        return closeDate;
    }

    @Override
    public int getId() {
        return id;
    }
}