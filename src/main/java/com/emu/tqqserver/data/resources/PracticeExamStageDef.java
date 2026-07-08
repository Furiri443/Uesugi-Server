package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "practice_exam_stage.json")
public class PracticeExamStageDef extends BaseDef {
    @SerializedName("practice_exam_id")
    private int practiceExamId;

    @SerializedName("stage_id")
    private int stageId;

    @SerializedName("subject_type")
    private int subjectType;

    private String description;

    public int getPracticeExamId() {
        return practiceExamId;
    }

    public int getStageId() {
        return stageId;
    }

    public int getSubjectType() {
        return subjectType;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getId() {
        return 0;
    }
}