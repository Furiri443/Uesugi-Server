package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "practice_exam_stage_ranking_reward.json")
public class PracticeExamStageRankingRewardDef extends BaseDef {
    @SerializedName("practice_exam_stage_id")
    private int practiceExamStageId;

    @SerializedName("reward_type")
    private int rewardType;

    private int upper;

    private int lower;

    @SerializedName("reward_id")
    private int rewardId;

    public int getPracticeExamStageId() {
        return practiceExamStageId;
    }

    public int getRewardType() {
        return rewardType;
    }

    public int getUpper() {
        return upper;
    }

    public int getLower() {
        return lower;
    }

    public int getRewardId() {
        return rewardId;
    }

    @Override
    public int getId() {
        return 0;
    }
}