package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_ep_reward.json")
public class TeamBattleEpRewardDef extends BaseDef {
    private int id;

    @SerializedName("team_battle_event_id")
    private int teamBattleEventId;

    private int ep;

    @SerializedName("reward_id")
    private int rewardId;

    private int type;

    @SerializedName("loop_ep")
    private int loopEp;

    public int getTeamBattleEventId() {
        return teamBattleEventId;
    }

    public int getEp() {
        return ep;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getType() {
        return type;
    }

    public int getLoopEp() {
        return loopEp;
    }

    @Override
    public int getId() {
        return id;
    }
}