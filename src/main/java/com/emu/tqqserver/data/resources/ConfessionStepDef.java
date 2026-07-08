package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "confession_step.json")
public class ConfessionStepDef extends BaseDef {
    @SerializedName("season_id")
    private int seasonId;

    private int type;

    private int step;

    @SerializedName("bonds_score")
    private int bondsScore;

    @SerializedName("standard_reward_id")
    private int standardRewardId;

    @SerializedName("premium_reward_id")
    private int premiumRewardId;

    @SerializedName("ep_bonus_rate")
    private int epBonusRate;

    public int getSeasonId() {
        return seasonId;
    }

    public int getType() {
        return type;
    }

    public int getStep() {
        return step;
    }

    public int getBondsScore() {
        return bondsScore;
    }

    public int getStandardRewardId() {
        return standardRewardId;
    }

    public int getPremiumRewardId() {
        return premiumRewardId;
    }

    public int getEpBonusRate() {
        return epBonusRate;
    }

    @Override
    public int getId() {
        return 0;
    }
}