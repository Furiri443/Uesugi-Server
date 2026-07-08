package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "practice_exam_puzzle_rule.json")
public class PracticeExamPuzzleRuleDef extends BaseDef {
    private int id;

    @SerializedName("rule_type")
    private int ruleType;

    private int target;

    @SerializedName("score_rate")
    private float scoreRate;

    private String comment;

    public int getRuleType() {
        return ruleType;
    }

    public int getTarget() {
        return target;
    }

    public float getScoreRate() {
        return scoreRate;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public int getId() {
        return id;
    }
}