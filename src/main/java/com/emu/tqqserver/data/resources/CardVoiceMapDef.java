package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card_voice_map.json")
public class CardVoiceMapDef extends BaseDef {
    @SerializedName("card_id")
    private int cardId;

    private int pattern;

    @SerializedName("target_card_id")
    private int targetCardId;

    @SerializedName("target_pattern")
    private int targetPattern;

    public int getCardId() {
        return cardId;
    }

    public int getPattern() {
        return pattern;
    }

    public int getTargetCardId() {
        return targetCardId;
    }

    public int getTargetPattern() {
        return targetPattern;
    }

    @Override
    public int getId() {
        return 0;
    }
}