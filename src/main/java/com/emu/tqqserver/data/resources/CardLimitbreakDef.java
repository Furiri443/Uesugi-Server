package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card_limitbreak.json")
public class CardLimitbreakDef extends BaseDef {
    private int id;

    @SerializedName("score_red")
    private int scoreRed;

    @SerializedName("score_green")
    private int scoreGreen;

    @SerializedName("score_blue")
    private int scoreBlue;

    @SerializedName("score_yellow")
    private int scoreYellow;

    @SerializedName("score_purple")
    private int scorePurple;

    public int getScoreRed() {
        return scoreRed;
    }

    public int getScoreGreen() {
        return scoreGreen;
    }

    public int getScoreBlue() {
        return scoreBlue;
    }

    public int getScoreYellow() {
        return scoreYellow;
    }

    public int getScorePurple() {
        return scorePurple;
    }

    @Override
    public int getId() {
        return id;
    }
}