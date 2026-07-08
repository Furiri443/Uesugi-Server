package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "reaction_clothes.json")
public class ReactionClothesDef extends BaseDef {
    private int id;

    @SerializedName("character_id")
    private int characterId;

    private int type;

    @SerializedName("line_id")
    private int lineId;

    public int getCharacterId() {
        return characterId;
    }

    public int getType() {
        return type;
    }

    public int getLineId() {
        return lineId;
    }

    @Override
    public int getId() {
        return id;
    }
}