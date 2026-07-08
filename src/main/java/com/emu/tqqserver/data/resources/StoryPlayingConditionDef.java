package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "story_playing_condition.json")
public class StoryPlayingConditionDef extends BaseDef {
    private int id;

    @SerializedName("story_id")
    private int storyId;

    private int priority;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private int closeDate;

    private int condition;

    @SerializedName("condition_value")
    private int conditionValue;

    public int getStoryId() {
        return storyId;
    }

    public int getPriority() {
        return priority;
    }

    public int getOpenDate() {
        return openDate;
    }

    public int getCloseDate() {
        return closeDate;
    }

    public int getCondition() {
        return condition;
    }

    public int getConditionValue() {
        return conditionValue;
    }

    @Override
    public int getId() {
        return id;
    }
}