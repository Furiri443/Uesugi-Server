package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "member_dearlevel.json")
public class MemberDearlevelDef extends BaseDef {
    @SerializedName("member_id")
    private int memberId;

    private int level;

    @SerializedName("title_text_id")
    private int titleTextId;

    @SerializedName("description_text_id")
    private int descriptionTextId;

    private int point;

    private int type;

    private int val1;

    private int val2;

    public int getMemberId() {
        return memberId;
    }

    public int getLevel() {
        return level;
    }

    public int getTitleTextId() {
        return titleTextId;
    }

    public int getDescriptionTextId() {
        return descriptionTextId;
    }

    public int getPoint() {
        return point;
    }

    public int getType() {
        return type;
    }

    public int getVal1() {
        return val1;
    }

    public int getVal2() {
        return val2;
    }

    @Override
    public int getId() {
        return 0;
    }
}