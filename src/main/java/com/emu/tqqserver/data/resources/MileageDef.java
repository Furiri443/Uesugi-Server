package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "mileage.json")
public class MileageDef extends BaseDef {
    @SerializedName("daily_reward_id")
    private int dailyRewardId;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("puzzle_skip_daily_limit")
    private int puzzleSkipDailyLimit;

    @SerializedName("ap_lump_use")
    private int apLumpUse;

    @SerializedName("card_limit")
    private int cardLimit;

    @SerializedName("work_lineup_limit")
    private int workLineupLimit;

    @SerializedName("work_multi_limit")
    private int workMultiLimit;

    @SerializedName("work_daily_limit")
    private int workDailyLimit;

    @SerializedName("collection_page_limit")
    private int collectionPageLimit;

    @SerializedName("synthesis_flag")
    private int synthesisFlag;

    @SerializedName("enhance_material_limit")
    private int enhanceMaterialLimit;

    @SerializedName("appointment_limit")
    private int appointmentLimit;

    @SerializedName("stamp_limit")
    private int stampLimit;

    private int grade;

    private int point;

    @SerializedName("work_free_lineup")
    private int workFreeLineup;

    public int getDailyRewardId() {
        return dailyRewardId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getPuzzleSkipDailyLimit() {
        return puzzleSkipDailyLimit;
    }

    public int getApLumpUse() {
        return apLumpUse;
    }

    public int getCardLimit() {
        return cardLimit;
    }

    public int getWorkLineupLimit() {
        return workLineupLimit;
    }

    public int getWorkMultiLimit() {
        return workMultiLimit;
    }

    public int getWorkDailyLimit() {
        return workDailyLimit;
    }

    public int getCollectionPageLimit() {
        return collectionPageLimit;
    }

    public int getSynthesisFlag() {
        return synthesisFlag;
    }

    public int getEnhanceMaterialLimit() {
        return enhanceMaterialLimit;
    }

    public int getAppointmentLimit() {
        return appointmentLimit;
    }

    public int getStampLimit() {
        return stampLimit;
    }

    public int getGrade() {
        return grade;
    }

    public int getPoint() {
        return point;
    }

    public int getWorkFreeLineup() {
        return workFreeLineup;
    }

    @Override
    public int getId() {
        return 0;
    }
}