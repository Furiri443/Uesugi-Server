package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "season.json")
public class SeasonDef extends BaseDef {
    private int id;

    private String label;

    private String abbreviation;

    @SerializedName("banner_resource_id")
    private int bannerResourceId;

    @SerializedName("premium_confession_step_price")
    private int premiumConfessionStepPrice;

    @SerializedName("bride_ranking")
    private int brideRanking;

    @SerializedName("confession_story_id")
    private int confessionStoryId;

    @SerializedName("unlock_item_id")
    private int unlockItemId;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("end_check_date")
    private long endCheckDate;

    @SerializedName("start_display")
    private long startDisplay;

    @SerializedName("end_display")
    private long endDisplay;

    @SerializedName("shop_limit_date")
    private long shopLimitDate;

    @SerializedName("unlock_date")
    private long unlockDate;

    @SerializedName("premium_love_step_price")
    private int premiumLoveStepPrice;

    public String getLabel() {
        return label;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getBannerResourceId() {
        return bannerResourceId;
    }

    public int getPremiumConfessionStepPrice() {
        return premiumConfessionStepPrice;
    }

    public int getBrideRanking() {
        return brideRanking;
    }

    public int getConfessionStoryId() {
        return confessionStoryId;
    }

    public int getUnlockItemId() {
        return unlockItemId;
    }

    public long getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public long getEndCheckDate() {
        return endCheckDate;
    }

    public long getStartDisplay() {
        return startDisplay;
    }

    public long getEndDisplay() {
        return endDisplay;
    }

    public long getShopLimitDate() {
        return shopLimitDate;
    }

    public long getUnlockDate() {
        return unlockDate;
    }

    public int getPremiumLoveStepPrice() {
        return premiumLoveStepPrice;
    }

    @Override
    public int getId() {
        return id;
    }
}