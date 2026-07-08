package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "confession_story.json")
public class ConfessionStoryDef extends BaseDef {
    private int id;

    private int type;

    @SerializedName("member_id")
    private int memberId;

    private String title;

    @SerializedName("media_id")
    private int mediaId;

    public int getType() {
        return type;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getTitle() {
        return title;
    }

    public int getMediaId() {
        return mediaId;
    }

    @Override
    public int getId() {
        return id;
    }
}