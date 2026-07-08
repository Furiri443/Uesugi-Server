package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "birth_festival.json")
public class BirthFestivalDef extends BaseDef {
    private int id;

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("seq_id")
    private int seqId;

    @SerializedName("gacha_id")
    private int gachaId;

    @SerializedName("eve_reward_id")
    private int eveRewardId;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("eve_date")
    private int eveDate;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private int closeDate;

    public int getMemberId() {
        return memberId;
    }

    public int getSeqId() {
        return seqId;
    }

    public int getGachaId() {
        return gachaId;
    }

    public int getEveRewardId() {
        return eveRewardId;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getEveDate() {
        return eveDate;
    }

    public int getOpenDate() {
        return openDate;
    }

    public int getCloseDate() {
        return closeDate;
    }

    @Override
    public int getId() {
        return id;
    }
}