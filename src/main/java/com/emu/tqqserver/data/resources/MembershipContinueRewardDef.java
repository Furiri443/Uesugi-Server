package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "membership_continue_reward.json")
public class MembershipContinueRewardDef extends BaseDef {
    @SerializedName("member_id")
    private int memberId;

    private int count;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("is_loop")
    private int isLoop;

    public int getMemberId() {
        return memberId;
    }

    public int getCount() {
        return count;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getIsLoop() {
        return isLoop;
    }

    @Override
    public int getId() {
        return 0;
    }
}