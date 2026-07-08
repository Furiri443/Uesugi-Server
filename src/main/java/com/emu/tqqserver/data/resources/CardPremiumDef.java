package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card_premium.json")
public class CardPremiumDef extends BaseDef {
    private int id;

    private int type;

    @SerializedName("home_message_id")
    private int homeMessageId;

    @SerializedName("max_release_count")
    private int maxReleaseCount;

    public int getType() {
        return type;
    }

    public int getHomeMessageId() {
        return homeMessageId;
    }

    public int getMaxReleaseCount() {
        return maxReleaseCount;
    }

    @Override
    public int getId() {
        return id;
    }
}