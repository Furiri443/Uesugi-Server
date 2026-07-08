package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_team.json")
public class TeamBattleTeamDef extends BaseDef {
    private int id;

    @SerializedName("teambattle_event_id")
    private int teambattleEventId;

    private int seq;

    private String name;

    @SerializedName("resource_id")
    private int resourceId;

    public int getTeambattleEventId() {
        return teambattleEventId;
    }

    public int getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public int getResourceId() {
        return resourceId;
    }

    @Override
    public int getId() {
        return id;
    }
}