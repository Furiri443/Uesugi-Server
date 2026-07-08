package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "stage.json")
public class StageDef extends BaseDef {
    private int id;

    private String name;

    private String information;

    @SerializedName("character_id")
    private int characterId;

    @SerializedName("clothes_id")
    private int clothesId;

    @SerializedName("model_id")
    private int modelId;

    @SerializedName("can_skip")
    private int canSkip;

    @SerializedName("can_continue")
    private int canContinue;

    @SerializedName("chapter_id")
    private int chapterId;

    @SerializedName("rank_score_s")
    private int rankScoreS;

    @SerializedName("rank_score_a")
    private int rankScoreA;

    @SerializedName("rank_score_b")
    private int rankScoreB;

    @SerializedName("rank_score_c")
    private int rankScoreC;

    @SerializedName("rank_score_d")
    private int rankScoreD;

    @SerializedName("publish_date")
    private long publishDate;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("close_date")
    private long closeDate;

    private float y;

    @SerializedName("firstclear_reward_id")
    private int firstclearRewardId;

    @SerializedName("rank_s_reward_id")
    private int rankSRewardId;

    @SerializedName("clear_reward_id")
    private int clearRewardId;

    @SerializedName("drop_reward1_id")
    private int dropReward1Id;

    @SerializedName("drop_reward2_id")
    private int dropReward2Id;

    @SerializedName("drop_reward3_id")
    private int dropReward3Id;

    private int exp;

    @SerializedName("card_exp")
    private int cardExp;

    private int tutorial;

    private float x;

    @SerializedName("unlock_stage_id")
    private int unlockStageId;

    @SerializedName("ep_coefficient")
    private float epCoefficient;

    @SerializedName("secret_unlock_type")
    private int secretUnlockType;

    @SerializedName("secret_unlock_condition")
    private int secretUnlockCondition;

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getClothesId() {
        return clothesId;
    }

    public int getModelId() {
        return modelId;
    }

    public int getCanSkip() {
        return canSkip;
    }

    public int getCanContinue() {
        return canContinue;
    }

    public int getChapterId() {
        return chapterId;
    }

    public int getRankScoreS() {
        return rankScoreS;
    }

    public int getRankScoreA() {
        return rankScoreA;
    }

    public int getRankScoreB() {
        return rankScoreB;
    }

    public int getRankScoreC() {
        return rankScoreC;
    }

    public int getRankScoreD() {
        return rankScoreD;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public long getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public float getY() {
        return y;
    }

    public int getFirstclearRewardId() {
        return firstclearRewardId;
    }

    public int getRankSRewardId() {
        return rankSRewardId;
    }

    public int getClearRewardId() {
        return clearRewardId;
    }

    public int getDropReward1Id() {
        return dropReward1Id;
    }

    public int getDropReward2Id() {
        return dropReward2Id;
    }

    public int getDropReward3Id() {
        return dropReward3Id;
    }

    public int getExp() {
        return exp;
    }

    public int getCardExp() {
        return cardExp;
    }

    public int getTutorial() {
        return tutorial;
    }

    public float getX() {
        return x;
    }

    public int getUnlockStageId() {
        return unlockStageId;
    }

    public float getEpCoefficient() {
        return epCoefficient;
    }

    public int getSecretUnlockType() {
        return secretUnlockType;
    }

    public int getSecretUnlockCondition() {
        return secretUnlockCondition;
    }

    @Override
    public int getId() {
        return id;
    }
}