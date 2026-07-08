package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "player_title_special.json")
public class PlayerTitleSpecialDef extends BaseDef {
    private int type;

    private String label;

    public int getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public int getId() {
        return 0;
    }
}