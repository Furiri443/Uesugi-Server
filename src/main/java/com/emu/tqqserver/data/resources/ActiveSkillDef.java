package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "active_skill.json")
public class ActiveSkillDef extends BaseDef {
    private int id;

    private int level;

    @SerializedName("name_text_id")
    private int nameTextId;

    @SerializedName("description_text_id")
    private int descriptionTextId;

    private int exp;

    private int category;

    @SerializedName("invoke_count")
    private int invokeCount;

    @SerializedName("area_param2")
    private int areaParam2;

    @SerializedName("erase_score")
    private int eraseScore;

    @SerializedName("area_type")
    private int areaType;

    @SerializedName("area_param1")
    private int areaParam1;

    @SerializedName("skill_type")
    private int skillType;

    @SerializedName("skill_param1")
    private int skillParam1;

    @SerializedName("skill_param2")
    private int skillParam2;

    @SerializedName("skill_param3")
    private int skillParam3;

    @SerializedName("limit_count")
    private int limitCount;

    @SerializedName("invoke_type")
    private int invokeType;

    public int getLevel() {
        return level;
    }

    public int getNameTextId() {
        return nameTextId;
    }

    public int getDescriptionTextId() {
        return descriptionTextId;
    }

    public int getExp() {
        return exp;
    }

    public int getCategory() {
        return category;
    }

    public int getInvokeCount() {
        return invokeCount;
    }

    public int getAreaParam2() {
        return areaParam2;
    }

    public int getEraseScore() {
        return eraseScore;
    }

    public int getAreaType() {
        return areaType;
    }

    public int getAreaParam1() {
        return areaParam1;
    }

    public int getSkillType() {
        return skillType;
    }

    public int getSkillParam1() {
        return skillParam1;
    }

    public int getSkillParam2() {
        return skillParam2;
    }

    public int getSkillParam3() {
        return skillParam3;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public int getInvokeType() {
        return invokeType;
    }

    @Override
    public int getId() {
        return id;
    }
}