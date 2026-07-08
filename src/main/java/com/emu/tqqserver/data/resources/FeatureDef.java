package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature.json")
public class FeatureDef extends BaseDef {
    private int id;

    private int type;

    private String title;

    private String abbreviation;

    private String description;

    @SerializedName("encore_fail_max_seq")
    private int encoreFailMaxSeq;

    @SerializedName("boost_name")
    private String boostName;

    @SerializedName("boost_name2")
    private String boostName2;

    @SerializedName("album_resource_id")
    private int albumResourceId;

    @SerializedName("bg_resource_id")
    private int bgResourceId;

    @SerializedName("navi_card_id")
    private int naviCardId;

    @SerializedName("teaser_clothes_id")
    private int teaserClothesId;

    @SerializedName("teaser_model_id")
    private int teaserModelId;

    @SerializedName("teaser_reaction_scene_id")
    private int teaserReactionSceneId;

    @SerializedName("boost_item_effect1")
    private int boostItemEffect1;

    @SerializedName("boost_item_effect2")
    private int boostItemEffect2;

    @SerializedName("boost_item_effect3")
    private int boostItemEffect3;

    @SerializedName("boost_item_effect4")
    private int boostItemEffect4;

    @SerializedName("boost_item_effect5")
    private int boostItemEffect5;

    @SerializedName("boost_item_effect6")
    private int boostItemEffect6;

    @SerializedName("boost_item_effect_rte7")
    private int boostItemEffectRte7;

    @SerializedName("friend_boost_ratio")
    private int friendBoostRatio;

    @SerializedName("friend_boost_max")
    private int friendBoostMax;

    @SerializedName("ep_a")
    private float epA;

    @SerializedName("ep_n")
    private float epN;

    @SerializedName("ep_gimmick_s")
    private int epGimmickS;

    @SerializedName("ep_gimmick_m")
    private int epGimmickM;

    @SerializedName("ep_gimmick_l")
    private int epGimmickL;

    @SerializedName("practice_exam_id")
    private int practiceExamId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("start_date")
    private int startDate;

    @SerializedName("selection_end_date")
    private int selectionEndDate;

    @SerializedName("end_date")
    private int endDate;

    @SerializedName("application_end_date")
    private int applicationEndDate;

    @SerializedName("lottery_result_date")
    private int lotteryResultDate;

    @SerializedName("close_date")
    private int closeDate;

    @SerializedName("revival_open_date")
    private long revivalOpenDate;

    @SerializedName("revival_close_date")
    private long revivalCloseDate;

    @SerializedName("revival_display_date")
    private long revivalDisplayDate;

    @SerializedName("boost_type")
    private int boostType;

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public int getEncoreFailMaxSeq() {
        return encoreFailMaxSeq;
    }

    public String getBoostName() {
        return boostName;
    }

    public String getBoostName2() {
        return boostName2;
    }

    public int getAlbumResourceId() {
        return albumResourceId;
    }

    public int getBgResourceId() {
        return bgResourceId;
    }

    public int getNaviCardId() {
        return naviCardId;
    }

    public int getTeaserClothesId() {
        return teaserClothesId;
    }

    public int getTeaserModelId() {
        return teaserModelId;
    }

    public int getTeaserReactionSceneId() {
        return teaserReactionSceneId;
    }

    public int getBoostItemEffect1() {
        return boostItemEffect1;
    }

    public int getBoostItemEffect2() {
        return boostItemEffect2;
    }

    public int getBoostItemEffect3() {
        return boostItemEffect3;
    }

    public int getBoostItemEffect4() {
        return boostItemEffect4;
    }

    public int getBoostItemEffect5() {
        return boostItemEffect5;
    }

    public int getBoostItemEffect6() {
        return boostItemEffect6;
    }

    public int getBoostItemEffectRte7() {
        return boostItemEffectRte7;
    }

    public int getFriendBoostRatio() {
        return friendBoostRatio;
    }

    public int getFriendBoostMax() {
        return friendBoostMax;
    }

    public float getEpA() {
        return epA;
    }

    public float getEpN() {
        return epN;
    }

    public int getEpGimmickS() {
        return epGimmickS;
    }

    public int getEpGimmickM() {
        return epGimmickM;
    }

    public int getEpGimmickL() {
        return epGimmickL;
    }

    public int getPracticeExamId() {
        return practiceExamId;
    }

    public int getOpenDate() {
        return openDate;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getSelectionEndDate() {
        return selectionEndDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public int getApplicationEndDate() {
        return applicationEndDate;
    }

    public int getLotteryResultDate() {
        return lotteryResultDate;
    }

    public int getCloseDate() {
        return closeDate;
    }

    public long getRevivalOpenDate() {
        return revivalOpenDate;
    }

    public long getRevivalCloseDate() {
        return revivalCloseDate;
    }

    public long getRevivalDisplayDate() {
        return revivalDisplayDate;
    }

    public int getBoostType() {
        return boostType;
    }

    @Override
    public int getId() {
        return id;
    }
}