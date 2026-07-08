package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card_chibichara.json")
public class CardChibicharaDef extends BaseDef {
    @SerializedName("card_id")
    private int cardId;

    @SerializedName("member_id")
    private int memberId;

    private String text;

    public int getCardId() {
        return cardId;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getId() {
        return 0;
    }
}