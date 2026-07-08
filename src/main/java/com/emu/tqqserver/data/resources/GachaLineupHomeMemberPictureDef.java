package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "gacha_lineup_home_member_picture.json")
public class GachaLineupHomeMemberPictureDef extends BaseDef {
    @SerializedName("open_date")
    private long openDate;

    @SerializedName("bonus_start_date")
    private long bonusStartDate;

    @SerializedName("bonus_end_date")
    private long bonusEndDate;

    public long getOpenDate() {
        return openDate;
    }

    public long getBonusStartDate() {
        return bonusStartDate;
    }

    public long getBonusEndDate() {
        return bonusEndDate;
    }

    @Override
    public int getId() {
        return 0;
    }
}