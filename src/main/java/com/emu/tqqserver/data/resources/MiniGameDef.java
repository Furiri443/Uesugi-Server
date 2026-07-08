package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "mini_game.json")
public class MiniGameDef extends BaseDef {
    private int id;

    private int type;

    @SerializedName("rp_limit")
    private int rpLimit;

    @SerializedName("rp_reset_cycle_hour")
    private int rpResetCycleHour;

    @SerializedName("advertising_id")
    private int advertisingId;

    private int price;

    private String comment;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("playable_start_date")
    private int playableStartDate;

    @SerializedName("playable_end_date")
    private int playableEndDate;

    @SerializedName("close_date")
    private int closeDate;

    public int getType() {
        return type;
    }

    public int getRpLimit() {
        return rpLimit;
    }

    public int getRpResetCycleHour() {
        return rpResetCycleHour;
    }

    public int getAdvertisingId() {
        return advertisingId;
    }

    public int getPrice() {
        return price;
    }

    public String getComment() {
        return comment;
    }

    public int getOpenDate() {
        return openDate;
    }

    public int getPlayableStartDate() {
        return playableStartDate;
    }

    public int getPlayableEndDate() {
        return playableEndDate;
    }

    public int getCloseDate() {
        return closeDate;
    }

    @Override
    public int getId() {
        return id;
    }
}