package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_selection.json")
public class TeamBattleSelectionDef extends BaseDef {
    @SerializedName("team_battle_event_id")
    private int teamBattleEventId;

    @SerializedName("target_id")
    private int targetId;

    private String title;

    @SerializedName("body_text_id")
    private int bodyTextId;

    public int getTeamBattleEventId() {
        return teamBattleEventId;
    }

    public int getTargetId() {
        return targetId;
    }

    public String getTitle() {
        return title;
    }

    public int getBodyTextId() {
        return bodyTextId;
    }

    @Override
    public int getId() {
        return 0;
    }
}