package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "active_skill_icon.json")
public class ActiveSkillIconDef extends BaseDef {
    private int id;

    private int icon;

    public int getIcon() {
        return icon;
    }

    @Override
    public int getId() {
        return id;
    }
}