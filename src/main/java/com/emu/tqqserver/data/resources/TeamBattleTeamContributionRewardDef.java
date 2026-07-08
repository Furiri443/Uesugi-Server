package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_team_contribution_reward.json")
public class TeamBattleTeamContributionRewardDef extends BaseDef {
    private int id;

    @SerializedName("team_battle_event_id")
    private int teamBattleEventId;

    private int upper;

    private int lower;

    @SerializedName("winner_reward_id")
    private int winnerRewardId;

    @SerializedName("loser_reward_id")
    private int loserRewardId;

    public int getTeamBattleEventId() {
        return teamBattleEventId;
    }

    public int getUpper() {
        return upper;
    }

    public int getLower() {
        return lower;
    }

    public int getWinnerRewardId() {
        return winnerRewardId;
    }

    public int getLoserRewardId() {
        return loserRewardId;
    }

    @Override
    public int getId() {
        return id;
    }
}