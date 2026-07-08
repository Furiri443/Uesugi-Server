package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "member_message.json")
public class MemberMessageDef extends BaseDef {
    private int id;

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("type_id")
    private int typeId;

    private String body;

    private int interval;

    public int getMemberId() {
        return memberId;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getBody() {
        return body;
    }

    public int getInterval() {
        return interval;
    }

    @Override
    public int getId() {
        return id;
    }
}