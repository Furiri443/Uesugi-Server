package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_team.json")
public class FeatureTeamDef extends BaseDef {
    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("member_limit")
    private int memberLimit;

    @SerializedName("create_limit")
    private int createLimit;

    @SerializedName("request_send_limit")
    private int requestSendLimit;

    @SerializedName("request_receive_limit")
    private int requestReceiveLimit;

    @SerializedName("boost_unit")
    private int boostUnit;

    @SerializedName("boost_max")
    private int boostMax;

    @SerializedName("boost_seconds")
    private int boostSeconds;

    @SerializedName("kickable_days")
    private int kickableDays;

    @SerializedName("transfer_deadline")
    private int transferDeadline;

    public int getFeatureId() {
        return featureId;
    }

    public int getMemberLimit() {
        return memberLimit;
    }

    public int getCreateLimit() {
        return createLimit;
    }

    public int getRequestSendLimit() {
        return requestSendLimit;
    }

    public int getRequestReceiveLimit() {
        return requestReceiveLimit;
    }

    public int getBoostUnit() {
        return boostUnit;
    }

    public int getBoostMax() {
        return boostMax;
    }

    public int getBoostSeconds() {
        return boostSeconds;
    }

    public int getKickableDays() {
        return kickableDays;
    }

    public int getTransferDeadline() {
        return transferDeadline;
    }

    @Override
    public int getId() {
        return 0;
    }
}