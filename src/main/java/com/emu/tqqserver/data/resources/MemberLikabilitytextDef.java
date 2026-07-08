package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "member_likabilitytext.json")
public class MemberLikabilitytextDef extends BaseDef {
    private int id;

    private String text;

    public String getText() {
        return text;
    }

    @Override
    public int getId() {
        return id;
    }
}