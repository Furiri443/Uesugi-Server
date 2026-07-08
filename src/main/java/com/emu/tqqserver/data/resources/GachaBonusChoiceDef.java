package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "gacha_bonus_choice.json")
public class GachaBonusChoiceDef extends BaseDef {
    private int id;

    @SerializedName("seq_id")
    private int seqId;

    private int idx;

    @SerializedName("target_id")
    private int targetId;

    private int weight;

    private int type;

    public int getSeqId() {
        return seqId;
    }

    public int getIdx() {
        return idx;
    }

    public int getTargetId() {
        return targetId;
    }

    public int getWeight() {
        return weight;
    }

    public int getType() {
        return type;
    }

    @Override
    public int getId() {
        return id;
    }
}