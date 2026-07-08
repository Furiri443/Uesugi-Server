package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_period_member.json")
public class FeaturePeriodMemberDef extends BaseDef {
    private int id;

    @SerializedName("period_id")
    private int periodId;

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("resource_id")
    private int resourceId;

    public int getPeriodId() {
        return periodId;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getResourceId() {
        return resourceId;
    }

    @Override
    public int getId() {
        return id;
    }
}