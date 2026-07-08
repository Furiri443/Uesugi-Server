package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "appointment_message.json")
public class AppointmentMessageDef extends BaseDef {
    private int id;

    private int type;

    @SerializedName("sub_type")
    private int subType;

    @SerializedName("target_id")
    private int targetId;

    @SerializedName("message_")
    private String message;

    private int motion;

    @SerializedName("voice_id")
    private int voiceId;

    @SerializedName("situation_id")
    private int situationId;

    public int getType() {
        return type;
    }

    public int getSubType() {
        return subType;
    }

    public int getTargetId() {
        return targetId;
    }

    public String getMessage() {
        return message;
    }

    public int getMotion() {
        return motion;
    }

    public int getVoiceId() {
        return voiceId;
    }

    public int getSituationId() {
        return situationId;
    }

    @Override
    public int getId() {
        return id;
    }
}