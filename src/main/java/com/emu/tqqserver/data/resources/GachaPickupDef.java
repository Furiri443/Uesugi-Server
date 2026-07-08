package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "gacha_pickup.json")
public class GachaPickupDef extends BaseDef {
    @SerializedName("gacha_id")
    private int gachaId;

    @SerializedName("card_id")
    private int cardId;

    private int priority;

    @SerializedName("resource_idx")
    private int resourceIdx;

    private String voices;

    @SerializedName("model_display_flag")
    private int modelDisplayFlag;

    public int getGachaId() {
        return gachaId;
    }

    public int getCardId() {
        return cardId;
    }

    public int getPriority() {
        return priority;
    }

    public int getResourceIdx() {
        return resourceIdx;
    }

    public String getVoices() {
        return voices;
    }

    public int getModelDisplayFlag() {
        return modelDisplayFlag;
    }

    @Override
    public int getId() {
        return 0;
    }
}