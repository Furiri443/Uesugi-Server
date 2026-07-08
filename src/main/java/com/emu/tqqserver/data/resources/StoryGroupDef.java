package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "story_group.json")
public class StoryGroupDef extends BaseDef {
    private int id;

    private String name;

    @SerializedName("member_cnt")
    private int memberCnt;

    @SerializedName("card_id")
    private int cardId;

    private int member1;

    private int member2;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public String getName() {
        return name;
    }

    public int getMemberCnt() {
        return memberCnt;
    }

    public int getCardId() {
        return cardId;
    }

    public int getMember1() {
        return member1;
    }

    public int getMember2() {
        return member2;
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