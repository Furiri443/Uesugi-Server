package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "shop_sharing.json")
public class ShopSharingDef extends BaseDef {
    private int id;

    private int type;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private int closeDate;

    public int getType() {
        return type;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getOpenDate() {
        return openDate;
    }

    public int getCloseDate() {
        return closeDate;
    }

    @Override
    public int getId() {
        return id;
    }
}