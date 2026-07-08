package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "team_battle_event.json")
public class TeamBattleEventDef extends BaseDef {
    private int id;

    @SerializedName("selection_type")
    private int selectionType;

    @SerializedName("is_enabled_agree_terms")
    private int isEnabledAgreeTerms;

    @SerializedName("stage_type")
    private int stageType;

    private String title;

    private String abbreviation;

    private String rule;

    private String theme;

    @SerializedName("boost_name")
    private String boostName;

    @SerializedName("boost_name2")
    private String boostName2;

    @SerializedName("tutorial_navi_card_id")
    private int tutorialNaviCardId;

    @SerializedName("real_incentive_title")
    private String realIncentiveTitle;

    @SerializedName("num_of_real_incentive_winners")
    private int numOfRealIncentiveWinners;

    @SerializedName("num_of_tickets_required_per_application")
    private int numOfTicketsRequiredPerApplication;

    @SerializedName("box_count_boost_item_id")
    private int boxCountBoostItemId;

    @SerializedName("gold_box_rate_boost_item_id")
    private int goldBoxRateBoostItemId;

    @SerializedName("skip_item_id")
    private int skipItemId;

    @SerializedName("real_incentive_application_ticket_item_id")
    private int realIncentiveApplicationTicketItemId;

    @SerializedName("real_incentive_winning_ticket_item_id")
    private int realIncentiveWinningTicketItemId;

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

    public int getSelectionType() {
        return selectionType;
    }

    public int getIsEnabledAgreeTerms() {
        return isEnabledAgreeTerms;
    }

    public int getStageType() {
        return stageType;
    }

    public String getTitle() {
        return title;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getRule() {
        return rule;
    }

    public String getTheme() {
        return theme;
    }

    public String getBoostName() {
        return boostName;
    }

    public String getBoostName2() {
        return boostName2;
    }

    public int getTutorialNaviCardId() {
        return tutorialNaviCardId;
    }

    public String getRealIncentiveTitle() {
        return realIncentiveTitle;
    }

    public int getNumOfRealIncentiveWinners() {
        return numOfRealIncentiveWinners;
    }

    public int getNumOfTicketsRequiredPerApplication() {
        return numOfTicketsRequiredPerApplication;
    }

    public int getBoxCountBoostItemId() {
        return boxCountBoostItemId;
    }

    public int getGoldBoxRateBoostItemId() {
        return goldBoxRateBoostItemId;
    }

    public int getSkipItemId() {
        return skipItemId;
    }

    public int getRealIncentiveApplicationTicketItemId() {
        return realIncentiveApplicationTicketItemId;
    }

    public int getRealIncentiveWinningTicketItemId() {
        return realIncentiveWinningTicketItemId;
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

    @Override
    public int getId() {
        return id;
    }
}