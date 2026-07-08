package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "login_bonus_seq.json")
public class LoginBonusSeqDef extends BaseDef {
    private int id;

    @SerializedName("login_num")
    private int loginNum;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("special_flag")
    private int specialFlag;

    public int getLoginNum() {
        return loginNum;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getSpecialFlag() {
        return specialFlag;
    }

    @Override
    public int getId() {
        return id;
    }
}