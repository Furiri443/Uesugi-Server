package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "challenge.json")
public class ChallengeDef extends BaseDef {
    private int id;

    private int group;

    private int type;

    private int priority;

    @SerializedName("name_text_id")
    private int nameTextId;

    @SerializedName("target_type")
    private int targetType;

    private int category;

    @SerializedName("start_value")
    private int startValue;

    @SerializedName("end_value")
    private int endValue;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("achivement_id")
    private String achivementId;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("target_a")
    private int targetA;

    @SerializedName("target_b")
    private int targetB;

    private int condition;

    @SerializedName("condition_value")
    private int conditionValue;

    @SerializedName("condition_step")
    private int conditionStep;

    @SerializedName("step_value")
    private int stepValue;

    public int getGroup() {
        return group;
    }

    public int getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    public int getNameTextId() {
        return nameTextId;
    }

    public int getTargetType() {
        return targetType;
    }

    public int getCategory() {
        return category;
    }

    public int getStartValue() {
        return startValue;
    }

    public int getEndValue() {
        return endValue;
    }

    public int getRewardId() {
        return rewardId;
    }

    public String getAchivementId() {
        return achivementId;
    }

    public long getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getTargetA() {
        return targetA;
    }

    public int getTargetB() {
        return targetB;
    }

    public int getCondition() {
        return condition;
    }

    public int getConditionValue() {
        return conditionValue;
    }

    public int getConditionStep() {
        return conditionStep;
    }

    public int getStepValue() {
        return stepValue;
    }

    @Override
    public int getId() {
        return id;
    }
}