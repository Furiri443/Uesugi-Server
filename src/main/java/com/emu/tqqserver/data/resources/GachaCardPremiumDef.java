package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "gacha_card_premium.json")
public class GachaCardPremiumDef extends BaseDef {
    private int id;

    @SerializedName("gacha_id")
    private int gachaId;

    @SerializedName("card_id")
    private int cardId;

    private int weight;

    public int getGachaId() {
        return gachaId;
    }

    public int getCardId() {
        return cardId;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int getId() {
        return id;
    }
}