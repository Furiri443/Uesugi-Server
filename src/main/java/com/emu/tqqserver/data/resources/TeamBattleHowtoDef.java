package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_howto.json")
public class TeamBattleHowtoDef extends BaseDef {
    @SerializedName("team_battle_event_id")
    private int teamBattleEventId;

    private int no;

    @SerializedName("header_text_id")
    private int headerTextId;

    @SerializedName("resource_id")
    private int resourceId;

    private String link;

    @SerializedName("link_type")
    private int linkType;

    @SerializedName("link_text")
    private String linkText;

    private String body;

    public int getTeamBattleEventId() {
        return teamBattleEventId;
    }

    public int getNo() {
        return no;
    }

    public int getHeaderTextId() {
        return headerTextId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getLink() {
        return link;
    }

    public int getLinkType() {
        return linkType;
    }

    public String getLinkText() {
        return linkText;
    }

    public String getBody() {
        return body;
    }

    @Override
    public int getId() {
        return 0;
    }
}