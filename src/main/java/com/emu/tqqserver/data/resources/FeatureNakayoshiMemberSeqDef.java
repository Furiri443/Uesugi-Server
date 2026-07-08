package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_nakayoshi_member_seq.json")
public class FeatureNakayoshiMemberSeqDef extends BaseDef {
    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("member_id_1")
    private int memberId1;

    @SerializedName("member_id_2")
    private int memberId2;

    @SerializedName("member_id_3")
    private int memberId3;

    @SerializedName("member_id_4")
    private int memberId4;

    @SerializedName("member_id_5")
    private int memberId5;

    public int getFeatureId() {
        return featureId;
    }

    public int getMemberId1() {
        return memberId1;
    }

    public int getMemberId2() {
        return memberId2;
    }

    public int getMemberId3() {
        return memberId3;
    }

    public int getMemberId4() {
        return memberId4;
    }

    public int getMemberId5() {
        return memberId5;
    }

    @Override
    public int getId() {
        return 0;
    }
}