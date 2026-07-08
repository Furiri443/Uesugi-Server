package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "lottery_reward.json")
public class LotteryRewardDef extends BaseDef {
    private int id;

    @SerializedName("lottery_id")
    private int lotteryId;

    @SerializedName("real_incentive_type")
    private String realIncentiveType;

    private String name;

    private String description;

    @SerializedName("detail_description")
    private String detailDescription;

    @SerializedName("num_winners")
    private int numWinners;

    @SerializedName("application_need_ticket")
    private int applicationNeedTicket;

    private int type;

    @SerializedName("reward_id")
    private int rewardId;

    public int getLotteryId() {
        return lotteryId;
    }

    public String getRealIncentiveType() {
        return realIncentiveType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public int getNumWinners() {
        return numWinners;
    }

    public int getApplicationNeedTicket() {
        return applicationNeedTicket;
    }

    public int getType() {
        return type;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return id;
    }
}