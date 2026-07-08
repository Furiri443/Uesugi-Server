package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_member_ranking_reward.json")
public class TeamBattleMemberRankingRewardDef extends BaseDef {
    private int id;

    @SerializedName("team_battle_event_id")
    private int teamBattleEventId;

    private int upper;

    private int lower;

    @SerializedName("reward_id")
    private int rewardId;

    public int getTeamBattleEventId() {
        return teamBattleEventId;
    }

    public int getUpper() {
        return upper;
    }

    public int getLower() {
        return lower;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return id;
    }
}