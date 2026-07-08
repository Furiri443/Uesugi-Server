package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "item.json")
public class ItemDef extends BaseDef {
    private int id;

    @SerializedName("sort_id")
    private int sortId;

    @SerializedName("name_text_id")
    private int nameTextId;

    @SerializedName("description_text_id")
    private int descriptionTextId;

    private int type;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("disp_flag")
    private int dispFlag;

    @SerializedName("sellable_date")
    private long sellableDate;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("target_id")
    private int targetId;

    @SerializedName("real_incentive")
    private int realIncentive;

    private int value;

    private int price;

    private int expire;

    @SerializedName("feature_id")
    private int featureId;

    private int duration;

    @SerializedName("season_id")
    private int seasonId;

    public int getSortId() {
        return sortId;
    }

    public int getNameTextId() {
        return nameTextId;
    }

    public int getDescriptionTextId() {
        return descriptionTextId;
    }

    public int getType() {
        return type;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getDispFlag() {
        return dispFlag;
    }

    public long getSellableDate() {
        return sellableDate;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getTargetId() {
        return targetId;
    }

    public int getRealIncentive() {
        return realIncentive;
    }

    public int getValue() {
        return value;
    }

    public int getPrice() {
        return price;
    }

    public int getExpire() {
        return expire;
    }

    public int getFeatureId() {
        return featureId;
    }

    public int getDuration() {
        return duration;
    }

    public int getSeasonId() {
        return seasonId;
    }

    @Override
    public int getId() {
        return id;
    }
}