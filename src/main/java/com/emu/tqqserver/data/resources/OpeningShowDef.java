package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "opening_show.json")
public class OpeningShowDef extends BaseDef {
    private int id;

    @SerializedName("background_resource_id")
    private int backgroundResourceId;

    @SerializedName("voice_id")
    private String voiceId;

    private int weight;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private int closeDate;

    @SerializedName("logo_id")
    private int logoId;

    public int getBackgroundResourceId() {
        return backgroundResourceId;
    }

    public String getVoiceId() {
        return voiceId;
    }

    public int getWeight() {
        return weight;
    }

    public int getOpenDate() {
        return openDate;
    }

    public int getCloseDate() {
        return closeDate;
    }

    public int getLogoId() {
        return logoId;
    }

    @Override
    public int getId() {
        return id;
    }
}