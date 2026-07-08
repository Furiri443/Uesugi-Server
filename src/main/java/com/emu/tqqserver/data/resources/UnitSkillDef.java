package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "unit_skill.json")
public class UnitSkillDef extends BaseDef {
    private int id;

    @SerializedName("name_text_id")
    private int nameTextId;

    private int rarity;

    @SerializedName("active_skill_id")
    private int activeSkillId;

    @SerializedName("lot_passive_skill")
    private int lotPassiveSkill;

    @SerializedName("score_attribute")
    private int scoreAttribute;

    @SerializedName("passive_skill_slot")
    private int passiveSkillSlot;

    public int getNameTextId() {
        return nameTextId;
    }

    public int getRarity() {
        return rarity;
    }

    public int getActiveSkillId() {
        return activeSkillId;
    }

    public int getLotPassiveSkill() {
        return lotPassiveSkill;
    }

    public int getScoreAttribute() {
        return scoreAttribute;
    }

    public int getPassiveSkillSlot() {
        return passiveSkillSlot;
    }

    @Override
    public int getId() {
        return id;
    }
}