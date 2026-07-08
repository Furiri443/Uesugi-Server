package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_appointment_message.json")
public class FeatureAppointmentMessageDef extends BaseDef {
    private int id;

    @SerializedName("feature_id")
    private int featureId;

    private int type;

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("message_")
    private String message;

    public int getFeatureId() {
        return featureId;
    }

    public int getType() {
        return type;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int getId() {
        return id;
    }
}