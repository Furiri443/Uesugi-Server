package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_nakayoshi_level.json")
public class FeatureNakayoshiLevelDef extends BaseDef {
    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("home_member_tap_motion_id")
    private int homeMemberTapMotionId;

    private int level;

    @SerializedName("required_nakayoshi_pt")
    private int requiredNakayoshiPt;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("loop_flg")
    private int loopFlg;

    @SerializedName("loop_pt")
    private int loopPt;

    public int getFeatureId() {
        return featureId;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getHomeMemberTapMotionId() {
        return homeMemberTapMotionId;
    }

    public int getLevel() {
        return level;
    }

    public int getRequiredNakayoshiPt() {
        return requiredNakayoshiPt;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getLoopFlg() {
        return loopFlg;
    }

    public int getLoopPt() {
        return loopPt;
    }

    @Override
    public int getId() {
        return 0;
    }
}