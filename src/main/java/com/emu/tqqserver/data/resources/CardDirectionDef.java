package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card_direction.json")
public class CardDirectionDef extends BaseDef {
    private int id;

    @SerializedName("default")
    private int defaultValue;

    private int kirameki;

    private int tokimeki;

    private int reawakening;

    @SerializedName("limit_state1")
    private int limitState1;

    @SerializedName("limit_state2")
    private int limitState2;

    private int pair;

    private int special1;

    private int special2;

    private int special3;

    public int getDefaultValue() {
        return defaultValue;
    }

    public int getKirameki() {
        return kirameki;
    }

    public int getTokimeki() {
        return tokimeki;
    }

    public int getReawakening() {
        return reawakening;
    }

    public int getLimitState1() {
        return limitState1;
    }

    public int getLimitState2() {
        return limitState2;
    }

    public int getPair() {
        return pair;
    }

    public int getSpecial1() {
        return special1;
    }

    public int getSpecial2() {
        return special2;
    }

    public int getSpecial3() {
        return special3;
    }

    @Override
    public int getId() {
        return id;
    }
}