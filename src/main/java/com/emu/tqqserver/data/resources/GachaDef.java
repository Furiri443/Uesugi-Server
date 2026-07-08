package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "gacha.json")
public class GachaDef extends BaseDef {
    private int id;

    private int type;

    private int priority;

    @SerializedName("name_text_id")
    private int nameTextId;

    @SerializedName("description_text_id")
    private int descriptionTextId;

    @SerializedName("color_type")
    private int colorType;

    @SerializedName("logo_type")
    private int logoType;

    @SerializedName("lineup_id")
    private int lineupId;

    @SerializedName("paid_reset_time")
    private String paidResetTime;

    @SerializedName("step_mode")
    private int stepMode;

    @SerializedName("step_repeat")
    private int stepRepeat;

    @SerializedName("gacha_option_id")
    private int gachaOptionId;

    @SerializedName("ticket_id")
    private int ticketId;

    private String introduce;

    @SerializedName("step_caution")
    private String stepCaution;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("background_movie_resource_id")
    private int backgroundMovieResourceId;

    @SerializedName("icon_resource_id")
    private int iconResourceId;

    @SerializedName("banner_num")
    private int bannerNum;

    @SerializedName("offer_wall_banner_num")
    private int offerWallBannerNum;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("open_week")
    private int openWeek;

    @SerializedName("reward_open_date")
    private int rewardOpenDate;

    @SerializedName("reward_close_date")
    private long rewardCloseDate;

    @SerializedName("gacha_panel_close_date")
    private long gachaPanelCloseDate;

    @SerializedName("diamond_free_lump_gacha_count")
    private int diamondFreeLumpGachaCount;

    @SerializedName("gold_free_lump_gacha_count")
    private int goldFreeLumpGachaCount;

    private String notice;

    @SerializedName("silver_free_lump_gacha_count")
    private int silverFreeLumpGachaCount;

    @SerializedName("free_single_gacha_count")
    private int freeSingleGachaCount;

    @SerializedName("free_single_gacha_reset_flag")
    private int freeSingleGachaResetFlag;

    @SerializedName("pop_text")
    private String popText;

    @SerializedName("panel_gacha_type")
    private int panelGachaType;

    @SerializedName("panel_board_id")
    private int panelBoardId;

    @SerializedName("single_gacha_panel_pt")
    private int singleGachaPanelPt;

    @SerializedName("lump_gacha_panel_pt")
    private int lumpGachaPanelPt;

    @SerializedName("panel_pt_per_lot")
    private int panelPtPerLot;

    @SerializedName("vip_limited")
    private int vipLimited;

    @SerializedName("gacha_bonus_choice_id")
    private int gachaBonusChoiceId;

    @SerializedName("background_image_resource_id")
    private int backgroundImageResourceId;

    @SerializedName("limit_count")
    private int limitCount;

    @SerializedName("limit_count_ex")
    private int limitCountEx;

    @SerializedName("limit_over")
    private int limitOver;

    @SerializedName("disable_period")
    private int disablePeriod;

    @SerializedName("summary_name_text_id")
    private int summaryNameTextId;

    @SerializedName("advertising_id")
    private int advertisingId;

    public int getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    public int getNameTextId() {
        return nameTextId;
    }

    public int getDescriptionTextId() {
        return descriptionTextId;
    }

    public int getColorType() {
        return colorType;
    }

    public int getLogoType() {
        return logoType;
    }

    public int getLineupId() {
        return lineupId;
    }

    public String getPaidResetTime() {
        return paidResetTime;
    }

    public int getStepMode() {
        return stepMode;
    }

    public int getStepRepeat() {
        return stepRepeat;
    }

    public int getGachaOptionId() {
        return gachaOptionId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getStepCaution() {
        return stepCaution;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getBackgroundMovieResourceId() {
        return backgroundMovieResourceId;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public int getBannerNum() {
        return bannerNum;
    }

    public int getOfferWallBannerNum() {
        return offerWallBannerNum;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getOpenWeek() {
        return openWeek;
    }

    public int getRewardOpenDate() {
        return rewardOpenDate;
    }

    public long getRewardCloseDate() {
        return rewardCloseDate;
    }

    public long getGachaPanelCloseDate() {
        return gachaPanelCloseDate;
    }

    public int getDiamondFreeLumpGachaCount() {
        return diamondFreeLumpGachaCount;
    }

    public int getGoldFreeLumpGachaCount() {
        return goldFreeLumpGachaCount;
    }

    public String getNotice() {
        return notice;
    }

    public int getSilverFreeLumpGachaCount() {
        return silverFreeLumpGachaCount;
    }

    public int getFreeSingleGachaCount() {
        return freeSingleGachaCount;
    }

    public int getFreeSingleGachaResetFlag() {
        return freeSingleGachaResetFlag;
    }

    public String getPopText() {
        return popText;
    }

    public int getPanelGachaType() {
        return panelGachaType;
    }

    public int getPanelBoardId() {
        return panelBoardId;
    }

    public int getSingleGachaPanelPt() {
        return singleGachaPanelPt;
    }

    public int getLumpGachaPanelPt() {
        return lumpGachaPanelPt;
    }

    public int getPanelPtPerLot() {
        return panelPtPerLot;
    }

    public int getVipLimited() {
        return vipLimited;
    }

    public int getGachaBonusChoiceId() {
        return gachaBonusChoiceId;
    }

    public int getBackgroundImageResourceId() {
        return backgroundImageResourceId;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public int getLimitCountEx() {
        return limitCountEx;
    }

    public int getLimitOver() {
        return limitOver;
    }

    public int getDisablePeriod() {
        return disablePeriod;
    }

    public int getSummaryNameTextId() {
        return summaryNameTextId;
    }

    public int getAdvertisingId() {
        return advertisingId;
    }

    @Override
    public int getId() {
        return id;
    }
}