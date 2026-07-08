package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "lottery_selection.json")
public class LotterySelectionDef extends BaseDef {
    @SerializedName("lottery_reward_id")
    private int lotteryRewardId;

    @SerializedName("target_id")
    private int targetId;

    private int type;

    private String title;

    @SerializedName("body_text_id")
    private int bodyTextId;

    public int getLotteryRewardId() {
        return lotteryRewardId;
    }

    public int getTargetId() {
        return targetId;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getBodyTextId() {
        return bodyTextId;
    }

    @Override
    public int getId() {
        return 0;
    }
}