package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "scenario_choices.json")
public class ScenarioChoicesDef extends BaseDef {
    @SerializedName("story_id")
    private int storyId;

    @SerializedName("type_no1")
    private int typeNo1;

    @SerializedName("value_no1")
    private int valueNo1;

    @SerializedName("type_no2")
    private int typeNo2;

    @SerializedName("value_no2")
    private int valueNo2;

    @SerializedName("type_no3")
    private int typeNo3;

    @SerializedName("value_no3")
    private int valueNo3;

    public int getStoryId() {
        return storyId;
    }

    public int getTypeNo1() {
        return typeNo1;
    }

    public int getValueNo1() {
        return valueNo1;
    }

    public int getTypeNo2() {
        return typeNo2;
    }

    public int getValueNo2() {
        return valueNo2;
    }

    public int getTypeNo3() {
        return typeNo3;
    }

    public int getValueNo3() {
        return valueNo3;
    }

    @Override
    public int getId() {
        return 0;
    }
}