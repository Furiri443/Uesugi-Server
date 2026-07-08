package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "practice_exam_stage_drop_reward.json")
public class PracticeExamStageDropRewardDef extends BaseDef {
    @SerializedName("practice_exam_stage_id")
    private int practiceExamStageId;

    @SerializedName("clear_rank")
    private int clearRank;

    @SerializedName("reward_id_1")
    private int rewardId1;

    @SerializedName("reward_id_2")
    private int rewardId2;

    @SerializedName("reward_id_3")
    private int rewardId3;

    public int getPracticeExamStageId() {
        return practiceExamStageId;
    }

    public int getClearRank() {
        return clearRank;
    }

    public int getRewardId1() {
        return rewardId1;
    }

    public int getRewardId2() {
        return rewardId2;
    }

    public int getRewardId3() {
        return rewardId3;
    }

    @Override
    public int getId() {
        return 0;
    }
}