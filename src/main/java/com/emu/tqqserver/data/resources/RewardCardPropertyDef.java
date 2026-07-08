package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "reward_card_property.json")
public class RewardCardPropertyDef extends BaseDef {
    private int id;

    @SerializedName("card_level")
    private int cardLevel;

    @SerializedName("awakening_type")
    private int awakeningType;

    @SerializedName("card_skill_level")
    private int cardSkillLevel;

    @SerializedName("passive_skill_level")
    private int passiveSkillLevel;

    @SerializedName("limitbreak_rank")
    private int limitbreakRank;

    @SerializedName("interlude_voice1")
    private int interludeVoice1;

    @SerializedName("interlude_voice2")
    private int interludeVoice2;

    public int getCardLevel() {
        return cardLevel;
    }

    public int getAwakeningType() {
        return awakeningType;
    }

    public int getCardSkillLevel() {
        return cardSkillLevel;
    }

    public int getPassiveSkillLevel() {
        return passiveSkillLevel;
    }

    public int getLimitbreakRank() {
        return limitbreakRank;
    }

    public int getInterludeVoice1() {
        return interludeVoice1;
    }

    public int getInterludeVoice2() {
        return interludeVoice2;
    }

    @Override
    public int getId() {
        return id;
    }
}