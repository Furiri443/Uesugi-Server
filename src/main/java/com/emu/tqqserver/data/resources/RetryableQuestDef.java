package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "retryable_quest.json")
public class RetryableQuestDef extends BaseDef {
    private int id;

    @SerializedName("name_text_id")
    private int nameTextId;

    @SerializedName("description_text_id")
    private int descriptionTextId;

    @SerializedName("list_type")
    private int listType;

    @SerializedName("max_current_reset")
    private int maxCurrentReset;

    @SerializedName("max_other_reset")
    private int maxOtherReset;

    @SerializedName("current_reset_price")
    private String currentResetPrice;

    @SerializedName("other_reset_price")
    private String otherResetPrice;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public int getNameTextId() {
        return nameTextId;
    }

    public int getDescriptionTextId() {
        return descriptionTextId;
    }

    public int getListType() {
        return listType;
    }

    public int getMaxCurrentReset() {
        return maxCurrentReset;
    }

    public int getMaxOtherReset() {
        return maxOtherReset;
    }

    public String getCurrentResetPrice() {
        return currentResetPrice;
    }

    public String getOtherResetPrice() {
        return otherResetPrice;
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