package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "player.json")
public class PlayerDef extends BaseDef {
    private int level;

    private int exp;

    @SerializedName("limit_follow")
    private int limitFollow;

    @SerializedName("work_target_types")
    private int workTargetTypes;

    @SerializedName("work_weight_level1")
    private int workWeightLevel1;

    @SerializedName("work_weight_level2")
    private int workWeightLevel2;

    @SerializedName("work_weight_level3")
    private int workWeightLevel3;

    @SerializedName("work_weight_level4")
    private int workWeightLevel4;

    @SerializedName("work_weight_level5")
    private int workWeightLevel5;

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public int getLimitFollow() {
        return limitFollow;
    }

    public int getWorkTargetTypes() {
        return workTargetTypes;
    }

    public int getWorkWeightLevel1() {
        return workWeightLevel1;
    }

    public int getWorkWeightLevel2() {
        return workWeightLevel2;
    }

    public int getWorkWeightLevel3() {
        return workWeightLevel3;
    }

    public int getWorkWeightLevel4() {
        return workWeightLevel4;
    }

    public int getWorkWeightLevel5() {
        return workWeightLevel5;
    }

    @Override
    public int getId() {
        return 0;
    }
}