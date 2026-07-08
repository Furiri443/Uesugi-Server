package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "member_deartool_reaction.json")
public class MemberDeartoolReactionDef extends BaseDef {
    @SerializedName("home_member_picture_id")
    private int homeMemberPictureId;

    @SerializedName("seq_id")
    private int seqId;

    @SerializedName("voice_id")
    private String voiceId;

    @SerializedName("voice_text")
    private String voiceText;

    private int weight;

    @SerializedName("resource_id")
    private int resourceId;

    public int getHomeMemberPictureId() {
        return homeMemberPictureId;
    }

    public int getSeqId() {
        return seqId;
    }

    public String getVoiceId() {
        return voiceId;
    }

    public String getVoiceText() {
        return voiceText;
    }

    public int getWeight() {
        return weight;
    }

    public int getResourceId() {
        return resourceId;
    }

    @Override
    public int getId() {
        return 0;
    }
}