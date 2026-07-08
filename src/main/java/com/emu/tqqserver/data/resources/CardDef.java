package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "card.json")
public class CardDef extends BaseDef {
    private int id;

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("costume_id")
    private int costumeId;

    @SerializedName("name_prefix_text_id")
    private int namePrefixTextId;

    private int rarity;

    @SerializedName("max_level_id")
    private int maxLevelId;

    @SerializedName("anime_season")
    private int animeSeason;

    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("picture_release_id")
    private int pictureReleaseId;

    @SerializedName("limitbreak_id")
    private int limitbreakId;

    @SerializedName("limitbreak_target_ids")
    private String limitbreakTargetIds;

    @SerializedName("level_growth_id")
    private int levelGrowthId;

    @SerializedName("is_limitbreak_material")
    private int isLimitbreakMaterial;

    @SerializedName("is_special_limitbreak_material")
    private int isSpecialLimitbreakMaterial;

    @SerializedName("book_growth_reward_group_id")
    private int bookGrowthRewardGroupId;

    private String description;

    @SerializedName("direction_id")
    private int directionId;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("exchange_start_date")
    private int exchangeStartDate;

    @SerializedName("limitbreak_material_usable_flag")
    private int limitbreakMaterialUsableFlag;

    @SerializedName("clothes_id")
    private int clothesId;

    @SerializedName("reaction_scene_id")
    private int reactionSceneId;

    @SerializedName("kirameki_awakening_receipe_id")
    private int kiramekiAwakeningReceipeId;

    @SerializedName("tokimeki_awakening_receipe_id")
    private int tokimekiAwakeningReceipeId;

    @SerializedName("reawakening_receipe_id")
    private int reawakeningReceipeId;

    @SerializedName("interlude_voice_no1")
    private String interludeVoiceNo1;

    @SerializedName("interlude_voice_no2")
    private String interludeVoiceNo2;

    @SerializedName("growth_reward_group_id")
    private int growthRewardGroupId;

    private int type;

    private int value;

    private int exp;

    @SerializedName("enhance_target_min_level")
    private int enhanceTargetMinLevel;

    @SerializedName("enhance_target_max_level")
    private int enhanceTargetMaxLevel;

    public int getMemberId() {
        return memberId;
    }

    public int getCostumeId() {
        return costumeId;
    }

    public int getNamePrefixTextId() {
        return namePrefixTextId;
    }

    public int getRarity() {
        return rarity;
    }

    public int getMaxLevelId() {
        return maxLevelId;
    }

    public int getAnimeSeason() {
        return animeSeason;
    }

    public int getFeatureId() {
        return featureId;
    }

    public int getPictureReleaseId() {
        return pictureReleaseId;
    }

    public int getLimitbreakId() {
        return limitbreakId;
    }

    public String getLimitbreakTargetIds() {
        return limitbreakTargetIds;
    }

    public int getLevelGrowthId() {
        return levelGrowthId;
    }

    public int getIsLimitbreakMaterial() {
        return isLimitbreakMaterial;
    }

    public int getIsSpecialLimitbreakMaterial() {
        return isSpecialLimitbreakMaterial;
    }

    public int getBookGrowthRewardGroupId() {
        return bookGrowthRewardGroupId;
    }

    public String getDescription() {
        return description;
    }

    public int getDirectionId() {
        return directionId;
    }

    public long getOpenDate() {
        return openDate;
    }

    public int getExchangeStartDate() {
        return exchangeStartDate;
    }

    public int getLimitbreakMaterialUsableFlag() {
        return limitbreakMaterialUsableFlag;
    }

    public int getClothesId() {
        return clothesId;
    }

    public int getReactionSceneId() {
        return reactionSceneId;
    }

    public int getKiramekiAwakeningReceipeId() {
        return kiramekiAwakeningReceipeId;
    }

    public int getTokimekiAwakeningReceipeId() {
        return tokimekiAwakeningReceipeId;
    }

    public int getReawakeningReceipeId() {
        return reawakeningReceipeId;
    }

    public String getInterludeVoiceNo1() {
        return interludeVoiceNo1;
    }

    public String getInterludeVoiceNo2() {
        return interludeVoiceNo2;
    }

    public int getGrowthRewardGroupId() {
        return growthRewardGroupId;
    }

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getExp() {
        return exp;
    }

    public int getEnhanceTargetMinLevel() {
        return enhanceTargetMinLevel;
    }

    public int getEnhanceTargetMaxLevel() {
        return enhanceTargetMaxLevel;
    }

    @Override
    public int getId() {
        return id;
    }
}