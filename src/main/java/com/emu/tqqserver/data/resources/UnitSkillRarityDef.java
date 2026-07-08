package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "unit_skill_rarity.json")
public class UnitSkillRarityDef extends BaseDef {
    private int rarity;

    @SerializedName("base_exp")
    private int baseExp;

    @SerializedName("active_skill_max_lv")
    private int activeSkillMaxLv;

    @SerializedName("passive_skill_max_lv")
    private int passiveSkillMaxLv;

    @SerializedName("passive_skill_slot_weight")
    private String passiveSkillSlotWeight;

    @SerializedName("passive_skill_lot_weight")
    private String passiveSkillLotWeight;

    @SerializedName("score_attribute_count_weight")
    private String scoreAttributeCountWeight;

    @SerializedName("reduction_reward_id")
    private int reductionRewardId;

    public int getRarity() {
        return rarity;
    }

    public int getBaseExp() {
        return baseExp;
    }

    public int getActiveSkillMaxLv() {
        return activeSkillMaxLv;
    }

    public int getPassiveSkillMaxLv() {
        return passiveSkillMaxLv;
    }

    public String getPassiveSkillSlotWeight() {
        return passiveSkillSlotWeight;
    }

    public String getPassiveSkillLotWeight() {
        return passiveSkillLotWeight;
    }

    public String getScoreAttributeCountWeight() {
        return scoreAttributeCountWeight;
    }

    public int getReductionRewardId() {
        return reductionRewardId;
    }

    @Override
    public int getId() {
        return 0;
    }
}