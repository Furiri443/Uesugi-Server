package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "shop_dearpoint.json")
public class ShopDearpointDef extends BaseDef {
    private int id;

    @SerializedName("sort_id")
    private int sortId;

    private int pay;

    private String name;

    private String description;

    @SerializedName("reward_id")
    private int rewardId;

    private int price;

    private int stock;

    @SerializedName("unlock_leaf_price")
    private int unlockLeafPrice;

    @SerializedName("unlock_leaf_add_price")
    private int unlockLeafAddPrice;

    @SerializedName("unlock_max_count")
    private int unlockMaxCount;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public int getSortId() {
        return sortId;
    }

    public int getPay() {
        return pay;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getUnlockLeafPrice() {
        return unlockLeafPrice;
    }

    public int getUnlockLeafAddPrice() {
        return unlockLeafAddPrice;
    }

    public int getUnlockMaxCount() {
        return unlockMaxCount;
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