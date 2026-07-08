package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "boost.json")
public class BoostDef extends BaseDef {
    private int id;

    private int group;

    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("card_id")
    private int cardId;

    private int value;

    private int lb1;

    private int lb2;

    private int lb3;

    private int lb4;

    @SerializedName("lb_bonus")
    private int lbBonus;

    @SerializedName("card_id_2")
    private int cardId2;

    public int getGroup() {
        return group;
    }

    public int getFeatureId() {
        return featureId;
    }

    public int getCardId() {
        return cardId;
    }

    public int getValue() {
        return value;
    }

    public int getLb1() {
        return lb1;
    }

    public int getLb2() {
        return lb2;
    }

    public int getLb3() {
        return lb3;
    }

    public int getLb4() {
        return lb4;
    }

    public int getLbBonus() {
        return lbBonus;
    }

    public int getCardId2() {
        return cardId2;
    }

    @Override
    public int getId() {
        return id;
    }
}