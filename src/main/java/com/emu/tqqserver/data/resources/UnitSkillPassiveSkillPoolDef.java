package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "unit_skill_passive_skill_pool.json")
public class UnitSkillPassiveSkillPoolDef extends BaseDef {
    @SerializedName("unit_skill_id")
    private int unitSkillId;

    @SerializedName("passive_skill_id")
    private int passiveSkillId;

    @SerializedName("passive_skill_level")
    private int passiveSkillLevel;

    public int getUnitSkillId() {
        return unitSkillId;
    }

    public int getPassiveSkillId() {
        return passiveSkillId;
    }

    public int getPassiveSkillLevel() {
        return passiveSkillLevel;
    }

    @Override
    public int getId() {
        return 0;
    }
}