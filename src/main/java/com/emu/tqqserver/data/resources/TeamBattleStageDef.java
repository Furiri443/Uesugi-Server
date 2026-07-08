package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_stage.json")
public class TeamBattleStageDef extends BaseDef {
    private int id;

    @SerializedName("team_battle_event_id")
    private int teamBattleEventId;

    private String name;

    @SerializedName("model_id")
    private int modelId;

    @SerializedName("difficulty_id")
    private int difficultyId;

    @SerializedName("rank_score_s")
    private int rankScoreS;

    @SerializedName("rank_score_a")
    private int rankScoreA;

    @SerializedName("rank_score_b")
    private int rankScoreB;

    @SerializedName("rank_score_c")
    private int rankScoreC;

    @SerializedName("rank_ep_s")
    private int rankEpS;

    @SerializedName("rank_ep_a")
    private int rankEpA;

    @SerializedName("rank_ep_b")
    private int rankEpB;

    @SerializedName("rank_ep_c")
    private int rankEpC;

    @SerializedName("rank_ep_d")
    private int rankEpD;

    @SerializedName("gold_treasure_ep")
    private int goldTreasureEp;

    @SerializedName("silver_treasure_ep")
    private int silverTreasureEp;

    @SerializedName("bronze_treasure_ep")
    private int bronzeTreasureEp;

    @SerializedName("can_continue")
    private int canContinue;

    @SerializedName("continue_price")
    private int continuePrice;

    private int exp;

    @SerializedName("card_exp")
    private int cardExp;

    @SerializedName("gold_treasure_reward_id")
    private int goldTreasureRewardId;

    @SerializedName("silver_treasure_reward_id")
    private int silverTreasureRewardId;

    @SerializedName("bronze_treasure_reward_id")
    private int bronzeTreasureRewardId;

    public int getTeamBattleEventId() {
        return teamBattleEventId;
    }

    public String getName() {
        return name;
    }

    public int getModelId() {
        return modelId;
    }

    public int getDifficultyId() {
        return difficultyId;
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

    public int getRankEpS() {
        return rankEpS;
    }

    public int getRankEpA() {
        return rankEpA;
    }

    public int getRankEpB() {
        return rankEpB;
    }

    public int getRankEpC() {
        return rankEpC;
    }

    public int getRankEpD() {
        return rankEpD;
    }

    public int getGoldTreasureEp() {
        return goldTreasureEp;
    }

    public int getSilverTreasureEp() {
        return silverTreasureEp;
    }

    public int getBronzeTreasureEp() {
        return bronzeTreasureEp;
    }

    public int getCanContinue() {
        return canContinue;
    }

    public int getContinuePrice() {
        return continuePrice;
    }

    public int getExp() {
        return exp;
    }

    public int getCardExp() {
        return cardExp;
    }

    public int getGoldTreasureRewardId() {
        return goldTreasureRewardId;
    }

    public int getSilverTreasureRewardId() {
        return silverTreasureRewardId;
    }

    public int getBronzeTreasureRewardId() {
        return bronzeTreasureRewardId;
    }

    @Override
    public int getId() {
        return id;
    }
}