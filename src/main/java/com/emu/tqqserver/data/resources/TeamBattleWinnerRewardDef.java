package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_winner_reward.json")
public class TeamBattleWinnerRewardDef extends BaseDef {
    private int id;

    @SerializedName("team_battle_event_id")
    private int teamBattleEventId;

    @SerializedName("reward_id")
    private int rewardId;

    public int getTeamBattleEventId() {
        return teamBattleEventId;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return id;
    }
}