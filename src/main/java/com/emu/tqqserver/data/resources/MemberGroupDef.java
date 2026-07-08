package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "member_group.json")
public class MemberGroupDef extends BaseDef {
    private int id;

    private int sort;

    private String name;

    @SerializedName("member_ids")
    private String memberIds;

    public int getSort() {
        return sort;
    }

    public String getName() {
        return name;
    }

    public String getMemberIds() {
        return memberIds;
    }

    @Override
    public int getId() {
        return id;
    }
}