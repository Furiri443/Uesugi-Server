package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "ep_fever_rating.json")
public class EpFeverRatingDef extends BaseDef {
    private int id;

    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("average_weight")
    private int averageWeight;

    @SerializedName("good_weight")
    private int goodWeight;

    @SerializedName("excellent_weight")
    private int excellentWeight;

    @SerializedName("average_val")
    private int averageVal;

    @SerializedName("good_val")
    private int goodVal;

    @SerializedName("excellent_val")
    private int excellentVal;

    private int rating;

    public int getFeatureId() {
        return featureId;
    }

    public int getAverageWeight() {
        return averageWeight;
    }

    public int getGoodWeight() {
        return goodWeight;
    }

    public int getExcellentWeight() {
        return excellentWeight;
    }

    public int getAverageVal() {
        return averageVal;
    }

    public int getGoodVal() {
        return goodVal;
    }

    public int getExcellentVal() {
        return excellentVal;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public int getId() {
        return id;
    }
}