package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "round.json")
public class RoundDef extends BaseDef {
    private int id;

    private int type;

    @SerializedName("stage_id")
    private int stageId;

    @SerializedName("puzzle_id")
    private int puzzleId;

    private int background;

    @SerializedName("bgm_text_id")
    private int bgmTextId;

    @SerializedName("popratio_id")
    private int popratioId;

    @SerializedName("limit_type")
    private int limitType;

    @SerializedName("limit_count")
    private int limitCount;

    @SerializedName("gimmick_design")
    private int gimmickDesign;

    private int profile;

    private int shadow;

    public int getType() {
        return type;
    }

    public int getStageId() {
        return stageId;
    }

    public int getPuzzleId() {
        return puzzleId;
    }

    public int getBackground() {
        return background;
    }

    public int getBgmTextId() {
        return bgmTextId;
    }

    public int getPopratioId() {
        return popratioId;
    }

    public int getLimitType() {
        return limitType;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public int getGimmickDesign() {
        return gimmickDesign;
    }

    public int getProfile() {
        return profile;
    }

    public int getShadow() {
        return shadow;
    }

    @Override
    public int getId() {
        return id;
    }
}