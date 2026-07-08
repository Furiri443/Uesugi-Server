package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "member.json")
public class MemberDef extends BaseDef {
    private int id;

    private String firstname;

    private int birthday;

    private String constellation;

    private int personality;

    @SerializedName("home_member_picture_id")
    private int homeMemberPictureId;

    @SerializedName("work_type_values")
    private String workTypeValues;

    @SerializedName("group_id")
    private int groupId;

    private int order;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public String getFirstname() {
        return firstname;
    }

    public int getBirthday() {
        return birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public int getPersonality() {
        return personality;
    }

    public int getHomeMemberPictureId() {
        return homeMemberPictureId;
    }

    public String getWorkTypeValues() {
        return workTypeValues;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getOrder() {
        return order;
    }

    public int getRewardId() {
        return rewardId;
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