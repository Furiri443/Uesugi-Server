package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "chapter.json")
public class ChapterDef extends BaseDef {
    private int id;

    @SerializedName("group_id")
    private int groupId;

    private int difficulty;

    @SerializedName("name_text_id")
    private int nameTextId;

    private String description;

    @SerializedName("model_id")
    private int modelId;

    @SerializedName("bg_resource_id")
    private int bgResourceId;

    @SerializedName("bg_model_resource_id")
    private int bgModelResourceId;

    @SerializedName("icon_resource_id")
    private int iconResourceId;

    @SerializedName("bar_color_text_id")
    private int barColorTextId;

    @SerializedName("position_tmpl_id")
    private int positionTmplId;

    @SerializedName("picture_flg")
    private int pictureFlg;

    @SerializedName("quest_id")
    private int questId;

    @SerializedName("ap_multiple_use")
    private int apMultipleUse;

    @SerializedName("complete_reward_id")
    private int completeRewardId;

    @SerializedName("perfect_reward_id")
    private int perfectRewardId;

    @SerializedName("unlock_chapter_ids")
    private String unlockChapterIds;

    @SerializedName("last_stage_id")
    private int lastStageId;

    @SerializedName("end_x")
    private float endX;

    @SerializedName("end_y")
    private float endY;

    private int sunday;

    private int monday;

    private int tuesday;

    private int wednesday;

    private int thursday;

    private int friday;

    private int saturday;

    @SerializedName("publish_date")
    private long publishDate;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("character_id")
    private int characterId;

    @SerializedName("clothes_id")
    private int clothesId;

    private String detail;

    public int getGroupId() {
        return groupId;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getNameTextId() {
        return nameTextId;
    }

    public String getDescription() {
        return description;
    }

    public int getModelId() {
        return modelId;
    }

    public int getBgResourceId() {
        return bgResourceId;
    }

    public int getBgModelResourceId() {
        return bgModelResourceId;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public int getBarColorTextId() {
        return barColorTextId;
    }

    public int getPositionTmplId() {
        return positionTmplId;
    }

    public int getPictureFlg() {
        return pictureFlg;
    }

    public int getQuestId() {
        return questId;
    }

    public int getApMultipleUse() {
        return apMultipleUse;
    }

    public int getCompleteRewardId() {
        return completeRewardId;
    }

    public int getPerfectRewardId() {
        return perfectRewardId;
    }

    public String getUnlockChapterIds() {
        return unlockChapterIds;
    }

    public int getLastStageId() {
        return lastStageId;
    }

    public float getEndX() {
        return endX;
    }

    public float getEndY() {
        return endY;
    }

    public int getSunday() {
        return sunday;
    }

    public int getMonday() {
        return monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public int getWednesday() {
        return wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public int getFriday() {
        return friday;
    }

    public int getSaturday() {
        return saturday;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public long getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getClothesId() {
        return clothesId;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public int getId() {
        return id;
    }
}