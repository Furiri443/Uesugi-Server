package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "quintuplet_game.json")
public class QuintupletGameDef extends BaseDef {
    private int id;

    @SerializedName("clothes_id")
    private int clothesId;

    @SerializedName("background_resource_id")
    private int backgroundResourceId;

    @SerializedName("bgm_id")
    private int bgmId;

    @SerializedName("correct_reward_id_member1")
    private int correctRewardIdMember1;

    @SerializedName("correct_reward_id_member2")
    private int correctRewardIdMember2;

    @SerializedName("correct_reward_id_member3")
    private int correctRewardIdMember3;

    @SerializedName("correct_reward_id_member4")
    private int correctRewardIdMember4;

    @SerializedName("correct_reward_id_member5")
    private int correctRewardIdMember5;

    @SerializedName("incorrect_reward_id_member1")
    private int incorrectRewardIdMember1;

    @SerializedName("incorrect_reward_id_member2")
    private int incorrectRewardIdMember2;

    @SerializedName("incorrect_reward_id_member3")
    private int incorrectRewardIdMember3;

    @SerializedName("incorrect_reward_id_member4")
    private int incorrectRewardIdMember4;

    @SerializedName("incorrect_reward_id_member5")
    private int incorrectRewardIdMember5;

    @SerializedName("correct_voice_id_member1")
    private int correctVoiceIdMember1;

    @SerializedName("correct_voice_id_member2")
    private int correctVoiceIdMember2;

    @SerializedName("correct_voice_id_member3")
    private int correctVoiceIdMember3;

    @SerializedName("correct_voice_id_member4")
    private int correctVoiceIdMember4;

    @SerializedName("correct_voice_id_member5")
    private int correctVoiceIdMember5;

    @SerializedName("correct_message_member1")
    private String correctMessageMember1;

    @SerializedName("correct_message_member2")
    private String correctMessageMember2;

    @SerializedName("correct_message_member3")
    private String correctMessageMember3;

    @SerializedName("correct_message_member4")
    private String correctMessageMember4;

    @SerializedName("correct_message_member5")
    private String correctMessageMember5;

    @SerializedName("incorrect_voice_id_member1")
    private int incorrectVoiceIdMember1;

    @SerializedName("incorrect_voice_id_member2")
    private int incorrectVoiceIdMember2;

    @SerializedName("incorrect_voice_id_member3")
    private int incorrectVoiceIdMember3;

    @SerializedName("incorrect_voice_id_member4")
    private int incorrectVoiceIdMember4;

    @SerializedName("incorrect_voice_id_member5")
    private int incorrectVoiceIdMember5;

    @SerializedName("incorrect_message_member1")
    private String incorrectMessageMember1;

    @SerializedName("incorrect_message_member2")
    private String incorrectMessageMember2;

    @SerializedName("incorrect_message_member3")
    private String incorrectMessageMember3;

    @SerializedName("incorrect_message_member4")
    private String incorrectMessageMember4;

    @SerializedName("incorrect_message_member5")
    private String incorrectMessageMember5;

    @SerializedName("question_voice_id_member1")
    private int questionVoiceIdMember1;

    @SerializedName("question_voice_id_member2")
    private int questionVoiceIdMember2;

    @SerializedName("question_voice_id_member3")
    private int questionVoiceIdMember3;

    @SerializedName("question_voice_id_member4")
    private int questionVoiceIdMember4;

    @SerializedName("question_voice_id_member5")
    private int questionVoiceIdMember5;

    @SerializedName("question_message_member1")
    private String questionMessageMember1;

    @SerializedName("question_message_member2")
    private String questionMessageMember2;

    @SerializedName("question_message_member3")
    private String questionMessageMember3;

    @SerializedName("question_message_member4")
    private String questionMessageMember4;

    @SerializedName("question_message_member5")
    private String questionMessageMember5;

    @SerializedName("give_reward_voice_id_member1")
    private int giveRewardVoiceIdMember1;

    @SerializedName("give_reward_voice_id_member2")
    private int giveRewardVoiceIdMember2;

    @SerializedName("give_reward_voice_id_member3")
    private int giveRewardVoiceIdMember3;

    @SerializedName("give_reward_voice_id_member4")
    private int giveRewardVoiceIdMember4;

    @SerializedName("give_reward_voice_id_member5")
    private int giveRewardVoiceIdMember5;

    @SerializedName("give_reward_message_member1")
    private String giveRewardMessageMember1;

    @SerializedName("give_reward_message_member2")
    private String giveRewardMessageMember2;

    @SerializedName("give_reward_message_member3")
    private String giveRewardMessageMember3;

    @SerializedName("give_reward_message_member4")
    private String giveRewardMessageMember4;

    @SerializedName("give_reward_message_member5")
    private String giveRewardMessageMember5;

    public int getClothesId() {
        return clothesId;
    }

    public int getBackgroundResourceId() {
        return backgroundResourceId;
    }

    public int getBgmId() {
        return bgmId;
    }

    public int getCorrectRewardIdMember1() {
        return correctRewardIdMember1;
    }

    public int getCorrectRewardIdMember2() {
        return correctRewardIdMember2;
    }

    public int getCorrectRewardIdMember3() {
        return correctRewardIdMember3;
    }

    public int getCorrectRewardIdMember4() {
        return correctRewardIdMember4;
    }

    public int getCorrectRewardIdMember5() {
        return correctRewardIdMember5;
    }

    public int getIncorrectRewardIdMember1() {
        return incorrectRewardIdMember1;
    }

    public int getIncorrectRewardIdMember2() {
        return incorrectRewardIdMember2;
    }

    public int getIncorrectRewardIdMember3() {
        return incorrectRewardIdMember3;
    }

    public int getIncorrectRewardIdMember4() {
        return incorrectRewardIdMember4;
    }

    public int getIncorrectRewardIdMember5() {
        return incorrectRewardIdMember5;
    }

    public int getCorrectVoiceIdMember1() {
        return correctVoiceIdMember1;
    }

    public int getCorrectVoiceIdMember2() {
        return correctVoiceIdMember2;
    }

    public int getCorrectVoiceIdMember3() {
        return correctVoiceIdMember3;
    }

    public int getCorrectVoiceIdMember4() {
        return correctVoiceIdMember4;
    }

    public int getCorrectVoiceIdMember5() {
        return correctVoiceIdMember5;
    }

    public String getCorrectMessageMember1() {
        return correctMessageMember1;
    }

    public String getCorrectMessageMember2() {
        return correctMessageMember2;
    }

    public String getCorrectMessageMember3() {
        return correctMessageMember3;
    }

    public String getCorrectMessageMember4() {
        return correctMessageMember4;
    }

    public String getCorrectMessageMember5() {
        return correctMessageMember5;
    }

    public int getIncorrectVoiceIdMember1() {
        return incorrectVoiceIdMember1;
    }

    public int getIncorrectVoiceIdMember2() {
        return incorrectVoiceIdMember2;
    }

    public int getIncorrectVoiceIdMember3() {
        return incorrectVoiceIdMember3;
    }

    public int getIncorrectVoiceIdMember4() {
        return incorrectVoiceIdMember4;
    }

    public int getIncorrectVoiceIdMember5() {
        return incorrectVoiceIdMember5;
    }

    public String getIncorrectMessageMember1() {
        return incorrectMessageMember1;
    }

    public String getIncorrectMessageMember2() {
        return incorrectMessageMember2;
    }

    public String getIncorrectMessageMember3() {
        return incorrectMessageMember3;
    }

    public String getIncorrectMessageMember4() {
        return incorrectMessageMember4;
    }

    public String getIncorrectMessageMember5() {
        return incorrectMessageMember5;
    }

    public int getQuestionVoiceIdMember1() {
        return questionVoiceIdMember1;
    }

    public int getQuestionVoiceIdMember2() {
        return questionVoiceIdMember2;
    }

    public int getQuestionVoiceIdMember3() {
        return questionVoiceIdMember3;
    }

    public int getQuestionVoiceIdMember4() {
        return questionVoiceIdMember4;
    }

    public int getQuestionVoiceIdMember5() {
        return questionVoiceIdMember5;
    }

    public String getQuestionMessageMember1() {
        return questionMessageMember1;
    }

    public String getQuestionMessageMember2() {
        return questionMessageMember2;
    }

    public String getQuestionMessageMember3() {
        return questionMessageMember3;
    }

    public String getQuestionMessageMember4() {
        return questionMessageMember4;
    }

    public String getQuestionMessageMember5() {
        return questionMessageMember5;
    }

    public int getGiveRewardVoiceIdMember1() {
        return giveRewardVoiceIdMember1;
    }

    public int getGiveRewardVoiceIdMember2() {
        return giveRewardVoiceIdMember2;
    }

    public int getGiveRewardVoiceIdMember3() {
        return giveRewardVoiceIdMember3;
    }

    public int getGiveRewardVoiceIdMember4() {
        return giveRewardVoiceIdMember4;
    }

    public int getGiveRewardVoiceIdMember5() {
        return giveRewardVoiceIdMember5;
    }

    public String getGiveRewardMessageMember1() {
        return giveRewardMessageMember1;
    }

    public String getGiveRewardMessageMember2() {
        return giveRewardMessageMember2;
    }

    public String getGiveRewardMessageMember3() {
        return giveRewardMessageMember3;
    }

    public String getGiveRewardMessageMember4() {
        return giveRewardMessageMember4;
    }

    public String getGiveRewardMessageMember5() {
        return giveRewardMessageMember5;
    }

    @Override
    public int getId() {
        return id;
    }
}