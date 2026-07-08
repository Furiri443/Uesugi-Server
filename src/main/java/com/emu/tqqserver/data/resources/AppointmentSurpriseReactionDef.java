package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "appointment_surprise_reaction.json")
public class AppointmentSurpriseReactionDef extends BaseDef {
    private int id;

    @SerializedName("member_id")
    private int memberId;

    private int level;

    private int priority;

    @SerializedName("reaction_scene_id")
    private int reactionSceneId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public int getMemberId() {
        return memberId;
    }

    public int getLevel() {
        return level;
    }

    public int getPriority() {
        return priority;
    }

    public int getReactionSceneId() {
        return reactionSceneId;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    @Override
    public int getId() {
        return id;
    }
}