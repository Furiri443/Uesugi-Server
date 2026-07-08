package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "practice_exam_puzzle_rule_stage.json")
public class PracticeExamPuzzleRuleStageDef extends BaseDef {
    @SerializedName("practice_exam_stage_id")
    private int practiceExamStageId;

    @SerializedName("practice_exam_puzzle_rule_id")
    private int practiceExamPuzzleRuleId;

    public int getPracticeExamStageId() {
        return practiceExamStageId;
    }

    public int getPracticeExamPuzzleRuleId() {
        return practiceExamPuzzleRuleId;
    }

    @Override
    public int getId() {
        return 0;
    }
}