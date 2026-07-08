package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card_max_level.json")
public class CardMaxLevelDef extends BaseDef {
    @SerializedName("init_max_lv")
    private int initMaxLv;

    private int id;

    @SerializedName("max_limitbreak_rank")
    private int maxLimitbreakRank;

    @SerializedName("limitbreak_rank1")
    private int limitbreakRank1;

    @SerializedName("limitbreak_rank2")
    private int limitbreakRank2;

    @SerializedName("limitbreak_rank3")
    private int limitbreakRank3;

    @SerializedName("limitbreak_rank4")
    private int limitbreakRank4;

    private int awakening;

    @SerializedName("limitbreak_rank5")
    private int limitbreakRank5;

    @SerializedName("limitbreak_rank6")
    private int limitbreakRank6;

    @SerializedName("limitbreak_rank7")
    private int limitbreakRank7;

    @SerializedName("limitbreak_rank8")
    private int limitbreakRank8;

    @SerializedName("limitbreak_rank9")
    private int limitbreakRank9;

    @SerializedName("limitbreak_rank10")
    private int limitbreakRank10;

    public int getInitMaxLv() {
        return initMaxLv;
    }

    public int getMaxLimitbreakRank() {
        return maxLimitbreakRank;
    }

    public int getLimitbreakRank1() {
        return limitbreakRank1;
    }

    public int getLimitbreakRank2() {
        return limitbreakRank2;
    }

    public int getLimitbreakRank3() {
        return limitbreakRank3;
    }

    public int getLimitbreakRank4() {
        return limitbreakRank4;
    }

    public int getAwakening() {
        return awakening;
    }

    public int getLimitbreakRank5() {
        return limitbreakRank5;
    }

    public int getLimitbreakRank6() {
        return limitbreakRank6;
    }

    public int getLimitbreakRank7() {
        return limitbreakRank7;
    }

    public int getLimitbreakRank8() {
        return limitbreakRank8;
    }

    public int getLimitbreakRank9() {
        return limitbreakRank9;
    }

    public int getLimitbreakRank10() {
        return limitbreakRank10;
    }

    @Override
    public int getId() {
        return id;
    }
}