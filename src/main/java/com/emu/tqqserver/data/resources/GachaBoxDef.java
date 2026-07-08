package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "gacha_box.json")
public class GachaBoxDef extends BaseDef {
    @SerializedName("gacha_id")
    private int gachaId;

    @SerializedName("box_id")
    private int boxId;

    @SerializedName("seq_id")
    private int seqId;

    @SerializedName("lineup_id")
    private int lineupId;

    private int number;

    private int rankup;

    private int pickup;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("gacha_text_id")
    private int gachaTextId;

    public int getGachaId() {
        return gachaId;
    }

    public int getBoxId() {
        return boxId;
    }

    public int getSeqId() {
        return seqId;
    }

    public int getLineupId() {
        return lineupId;
    }

    public int getNumber() {
        return number;
    }

    public int getRankup() {
        return rankup;
    }

    public int getPickup() {
        return pickup;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getGachaTextId() {
        return gachaTextId;
    }

    @Override
    public int getId() {
        return 0;
    }
}