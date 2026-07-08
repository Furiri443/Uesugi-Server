package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "practice_exam_evaluation_comment.json")
public class PracticeExamEvaluationCommentDef extends BaseDef {
    @SerializedName("subject_type")
    private int subjectType;

    @SerializedName("comment_type")
    private int commentType;

    @SerializedName("t_score_range_start")
    private float tScoreRangeStart;

    @SerializedName("t_score_range_end")
    private float tScoreRangeEnd;

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("eval_comment")
    private String evalComment;

    public int getSubjectType() {
        return subjectType;
    }

    public int getCommentType() {
        return commentType;
    }

    public float getTScoreRangeStart() {
        return tScoreRangeStart;
    }

    public float getTScoreRangeEnd() {
        return tScoreRangeEnd;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getEvalComment() {
        return evalComment;
    }

    @Override
    public int getId() {
        return 0;
    }
}