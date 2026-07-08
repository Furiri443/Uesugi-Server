package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "challenge_title.json")
public class ChallengeTitleDef extends BaseDef {
    private int id;

    private String title;

    @SerializedName("challenge_section_id")
    private int challengeSectionId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("end_date")
    private long endDate;

    public String getTitle() {
        return title;
    }

    public int getChallengeSectionId() {
        return challengeSectionId;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getEndDate() {
        return endDate;
    }

    @Override
    public int getId() {
        return id;
    }
}