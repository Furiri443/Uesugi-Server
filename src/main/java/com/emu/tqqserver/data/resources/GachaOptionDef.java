package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "gacha_option.json")
public class GachaOptionDef extends BaseDef {
    private int id;

    private int count;

    @SerializedName("single_price")
    private int singlePrice;

    @SerializedName("paid_single_price")
    private int paidSinglePrice;

    @SerializedName("discount_single")
    private int discountSingle;

    @SerializedName("reward_label_single")
    private String rewardLabelSingle;

    @SerializedName("lump_num")
    private int lumpNum;

    @SerializedName("lump_num_ticket")
    private int lumpNumTicket;

    @SerializedName("lump_price")
    private int lumpPrice;

    @SerializedName("paid_lump_price")
    private int paidLumpPrice;

    @SerializedName("lump_bonus_lot")
    private int lumpBonusLot;

    @SerializedName("reward_id_lump")
    private int rewardIdLump;

    @SerializedName("reward_label_lump")
    private String rewardLabelLump;

    @SerializedName("lump_bonus_lot_group_id")
    private String lumpBonusLotGroupId;

    @SerializedName("step_description")
    private String stepDescription;

    @SerializedName("step_rewards_info")
    private String stepRewardsInfo;

    @SerializedName("firest_reward_single_reset_date")
    private int firestRewardSingleResetDate;

    @SerializedName("first_reward_id_lump")
    private String firstRewardIdLump;

    @SerializedName("firest_reward_lump_reset_date")
    private int firestRewardLumpResetDate;

    @SerializedName("paid_single_reset_date")
    private int paidSingleResetDate;

    @SerializedName("paid_lump_reset_date")
    private int paidLumpResetDate;

    @SerializedName("reward_id_single")
    private int rewardIdSingle;

    public int getCount() {
        return count;
    }

    public int getSinglePrice() {
        return singlePrice;
    }

    public int getPaidSinglePrice() {
        return paidSinglePrice;
    }

    public int getDiscountSingle() {
        return discountSingle;
    }

    public String getRewardLabelSingle() {
        return rewardLabelSingle;
    }

    public int getLumpNum() {
        return lumpNum;
    }

    public int getLumpNumTicket() {
        return lumpNumTicket;
    }

    public int getLumpPrice() {
        return lumpPrice;
    }

    public int getPaidLumpPrice() {
        return paidLumpPrice;
    }

    public int getLumpBonusLot() {
        return lumpBonusLot;
    }

    public int getRewardIdLump() {
        return rewardIdLump;
    }

    public String getRewardLabelLump() {
        return rewardLabelLump;
    }

    public String getLumpBonusLotGroupId() {
        return lumpBonusLotGroupId;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public String getStepRewardsInfo() {
        return stepRewardsInfo;
    }

    public int getFirestRewardSingleResetDate() {
        return firestRewardSingleResetDate;
    }

    public String getFirstRewardIdLump() {
        return firstRewardIdLump;
    }

    public int getFirestRewardLumpResetDate() {
        return firestRewardLumpResetDate;
    }

    public int getPaidSingleResetDate() {
        return paidSingleResetDate;
    }

    public int getPaidLumpResetDate() {
        return paidLumpResetDate;
    }

    public int getRewardIdSingle() {
        return rewardIdSingle;
    }

    @Override
    public int getId() {
        return id;
    }
}