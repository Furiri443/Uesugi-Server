package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "vr_chapter.json")
public class VrChapterDef extends BaseDef {
    private int id;

    private String name;

    private String detail;

    @SerializedName("member_ids")
    private String memberIds;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("sort_id")
    private int sortId;

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public String getMemberIds() {
        return memberIds;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getSortId() {
        return sortId;
    }

    @Override
    public int getId() {
        return id;
    }
}