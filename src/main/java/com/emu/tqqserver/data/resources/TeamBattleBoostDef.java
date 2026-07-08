package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_boost.json")
public class TeamBattleBoostDef extends BaseDef {
    private int id;

    @SerializedName("team_battle_event_id")
    private int teamBattleEventId;

    @SerializedName("card_id")
    private int cardId;

    private int value;

    private int lb1;

    private int lb2;

    private int lb3;

    private int lb4;

    @SerializedName("lb_bonus")
    private int lbBonus;

    @SerializedName("is_reward_card")
    private int isRewardCard;

    public int getTeamBattleEventId() {
        return teamBattleEventId;
    }

    public int getCardId() {
        return cardId;
    }

    public int getValue() {
        return value;
    }

    public int getLb1() {
        return lb1;
    }

    public int getLb2() {
        return lb2;
    }

    public int getLb3() {
        return lb3;
    }

    public int getLb4() {
        return lb4;
    }

    public int getLbBonus() {
        return lbBonus;
    }

    public int getIsRewardCard() {
        return isRewardCard;
    }

    @Override
    public int getId() {
        return id;
    }
}