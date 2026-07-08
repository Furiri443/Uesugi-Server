package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "player_title.json")
public class PlayerTitleDef extends BaseDef {
    @SerializedName("challenge_id")
    private int challengeId;

    private int type;

    private int category;

    @SerializedName("disp_flag")
    private int dispFlag;

    @SerializedName("name_text_id")
    private int nameTextId;

    @SerializedName("banner_id")
    private int bannerId;

    @SerializedName("feature_id")
    private int featureId;

    private String conditions;

    private int grade;

    private int priority;

    @SerializedName("season_id")
    private int seasonId;

    public int getChallengeId() {
        return challengeId;
    }

    public int getType() {
        return type;
    }

    public int getCategory() {
        return category;
    }

    public int getDispFlag() {
        return dispFlag;
    }

    public int getNameTextId() {
        return nameTextId;
    }

    public int getBannerId() {
        return bannerId;
    }

    public int getFeatureId() {
        return featureId;
    }

    public String getConditions() {
        return conditions;
    }

    public int getGrade() {
        return grade;
    }

    public int getPriority() {
        return priority;
    }

    public int getSeasonId() {
        return seasonId;
    }

    @Override
    public int getId() {
        return 0;
    }
}