package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "challenge_group.json")
public class ChallengeGroupDef extends BaseDef {
    private int id;

    private int type;

    private String title;

    @SerializedName("banner_resource_id")
    private int bannerResourceId;

    @SerializedName("challenge_section_id")
    private int challengeSectionId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("end_date")
    private long endDate;

    private int rank;

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("bonds_ranker")
    private int bondsRanker;

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getBannerResourceId() {
        return bannerResourceId;
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

    public int getRank() {
        return rank;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getBondsRanker() {
        return bondsRanker;
    }

    @Override
    public int getId() {
        return id;
    }
}