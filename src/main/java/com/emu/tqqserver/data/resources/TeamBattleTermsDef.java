package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_terms.json")
public class TeamBattleTermsDef extends BaseDef {
    @SerializedName("team_battle_event_id")
    private int teamBattleEventId;

    private String overview;

    private String description;

    public int getTeamBattleEventId() {
        return teamBattleEventId;
    }

    public String getOverview() {
        return overview;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getId() {
        return 0;
    }
}