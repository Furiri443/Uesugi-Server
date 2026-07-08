package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "pool.json")
public class PoolDef extends BaseDef {
    private int id;

    @SerializedName("pool_id")
    private int poolId;

    private int type;

    @SerializedName("target_id")
    private int targetId;

    @SerializedName("reward_card_property_id")
    private int rewardCardPropertyId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("reward_id")
    private int rewardId;

    public int getPoolId() {
        return poolId;
    }

    public int getType() {
        return type;
    }

    public int getTargetId() {
        return targetId;
    }

    public int getRewardCardPropertyId() {
        return rewardCardPropertyId;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return id;
    }
}