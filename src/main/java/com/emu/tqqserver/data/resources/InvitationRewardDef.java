package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "invitation_reward.json")
public class InvitationRewardDef extends BaseDef {
    private int id;

    @SerializedName("invitation_id")
    private int invitationId;

    @SerializedName("required_invitation_count")
    private int requiredInvitationCount;

    @SerializedName("reward_id")
    private int rewardId;

    public int getInvitationId() {
        return invitationId;
    }

    public int getRequiredInvitationCount() {
        return requiredInvitationCount;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return id;
    }
}