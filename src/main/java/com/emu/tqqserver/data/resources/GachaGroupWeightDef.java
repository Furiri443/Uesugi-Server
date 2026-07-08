package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "gacha_group_weight.json")
public class GachaGroupWeightDef extends BaseDef {
    @SerializedName("gacha_id")
    private int gachaId;

    @SerializedName("group_id")
    private int groupId;

    private int weight;

    @SerializedName("bonus_weight")
    private int bonusWeight;

    public int getGachaId() {
        return gachaId;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getWeight() {
        return weight;
    }

    public int getBonusWeight() {
        return bonusWeight;
    }

    @Override
    public int getId() {
        return 0;
    }
}