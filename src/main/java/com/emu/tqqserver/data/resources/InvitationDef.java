package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "invitation.json")
public class InvitationDef extends BaseDef {
    private int id;

    private String title;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private int closeDate;

    @SerializedName("session_start_date")
    private int sessionStartDate;

    @SerializedName("session_close_date")
    private int sessionCloseDate;

    public String getTitle() {
        return title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getOpenDate() {
        return openDate;
    }

    public int getCloseDate() {
        return closeDate;
    }

    public int getSessionStartDate() {
        return sessionStartDate;
    }

    public int getSessionCloseDate() {
        return sessionCloseDate;
    }

    @Override
    public int getId() {
        return id;
    }
}