package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "story.json")
public class StoryDef extends BaseDef {
    private int id;

    @SerializedName("story_type")
    private int storyType;

    @SerializedName("chapter_text_id")
    private int chapterTextId;

    @SerializedName("title_text_id")
    private int titleTextId;

    @SerializedName("media_type")
    private int mediaType;

    @SerializedName("target_id")
    private int targetId;

    @SerializedName("media_id")
    private int mediaId;

    @SerializedName("publish_date")
    private long publishDate;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("sort_id")
    private int sortId;

    @SerializedName("card_lv")
    private int cardLv;

    @SerializedName("awakening_type")
    private int awakeningType;

    @SerializedName("feature_id")
    private int featureId;

    @SerializedName("has_choice")
    private int hasChoice;

    public int getStoryType() {
        return storyType;
    }

    public int getChapterTextId() {
        return chapterTextId;
    }

    public int getTitleTextId() {
        return titleTextId;
    }

    public int getMediaType() {
        return mediaType;
    }

    public int getTargetId() {
        return targetId;
    }

    public int getMediaId() {
        return mediaId;
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

    public int getSortId() {
        return sortId;
    }

    public int getCardLv() {
        return cardLv;
    }

    public int getAwakeningType() {
        return awakeningType;
    }

    public int getFeatureId() {
        return featureId;
    }

    public int getHasChoice() {
        return hasChoice;
    }

    @Override
    public int getId() {
        return id;
    }
}