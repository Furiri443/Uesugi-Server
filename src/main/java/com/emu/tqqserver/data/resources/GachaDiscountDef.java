package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "gacha_discount.json")
public class GachaDiscountDef extends BaseDef {
    @SerializedName("gacha_option_id")
    private int gachaOptionId;

    @SerializedName("seq_id")
    private int seqId;

    private int price;

    public int getGachaOptionId() {
        return gachaOptionId;
    }

    public int getSeqId() {
        return seqId;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int getId() {
        return 0;
    }
}