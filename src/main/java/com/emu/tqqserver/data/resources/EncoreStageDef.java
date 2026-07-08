package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "encore_stage.json")
public class EncoreStageDef extends BaseDef {
    private int id;

    @SerializedName("encore_id")
    private int encoreId;

    @SerializedName("feature_id")
    private int featureId;

    private int rank;

    private int upper;

    private int lower;

    private int exp;

    @SerializedName("boost_item_effect")
    private int boostItemEffect;

    private int weight;

    @SerializedName("ep_coefficient")
    private float epCoefficient;

    private String information;

    @SerializedName("clothes_id")
    private int clothesId;

    @SerializedName("model_id")
    private int modelId;

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

    private int rating;

    @SerializedName("character_id")
    private int characterId;

    public int getEncoreId() {
        return encoreId;
    }

    public int getFeatureId() {
        return featureId;
    }

    public int getRank() {
        return rank;
    }

    public int getUpper() {
        return upper;
    }

    public int getLower() {
        return lower;
    }

    public int getExp() {
        return exp;
    }

    public int getBoostItemEffect() {
        return boostItemEffect;
    }

    public int getWeight() {
        return weight;
    }

    public float getEpCoefficient() {
        return epCoefficient;
    }

    public String getInformation() {
        return information;
    }

    public int getClothesId() {
        return clothesId;
    }

    public int getModelId() {
        return modelId;
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

    public int getRating() {
        return rating;
    }

    public int getCharacterId() {
        return characterId;
    }

    @Override
    public int getId() {
        return id;
    }
}