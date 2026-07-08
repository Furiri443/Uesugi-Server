package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_tutorial.json")
public class TeamBattleTutorialDef extends BaseDef {
    @SerializedName("team_battle_event_id")
    private int teamBattleEventId;

    private int seq;

    private String cmd;

    private String val1;

    private String val2;

    private String val3;

    public int getTeamBattleEventId() {
        return teamBattleEventId;
    }

    public int getSeq() {
        return seq;
    }

    public String getCmd() {
        return cmd;
    }

    public String getVal1() {
        return val1;
    }

    public String getVal2() {
        return val2;
    }

    public String getVal3() {
        return val3;
    }

    @Override
    public int getId() {
        return 0;
    }
}