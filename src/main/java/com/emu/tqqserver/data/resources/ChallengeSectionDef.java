package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "challenge_section.json")
public class ChallengeSectionDef extends BaseDef {
    private int id;

    private String name;

    private int priority;

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int getId() {
        return id;
    }
}