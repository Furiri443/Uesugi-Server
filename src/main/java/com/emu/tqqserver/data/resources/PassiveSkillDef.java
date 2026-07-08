package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "passive_skill.json")
public class PassiveSkillDef extends BaseDef {
    private int id;

    private int level;

    @SerializedName("name_text_id")
    private int nameTextId;

    @SerializedName("description_text_id")
    private int descriptionTextId;

    private int type;

    private String value;

    @SerializedName("invoke_type")
    private int invokeType;

    @SerializedName("invoke_value1")
    private int invokeValue1;

    @SerializedName("stage_type")
    private int stageType;

    @SerializedName("invoke_rate")
    private int invokeRate;

    @SerializedName("invoke_value2")
    private int invokeValue2;

    public int getLevel() {
        return level;
    }

    public int getNameTextId() {
        return nameTextId;
    }

    public int getDescriptionTextId() {
        return descriptionTextId;
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getInvokeType() {
        return invokeType;
    }

    public int getInvokeValue1() {
        return invokeValue1;
    }

    public int getStageType() {
        return stageType;
    }

    public int getInvokeRate() {
        return invokeRate;
    }

    public int getInvokeValue2() {
        return invokeValue2;
    }

    @Override
    public int getId() {
        return id;
    }
}