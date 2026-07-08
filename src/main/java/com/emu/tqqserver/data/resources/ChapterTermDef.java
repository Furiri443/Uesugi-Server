package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "chapter_term.json")
public class ChapterTermDef extends BaseDef {
    private int id;

    @SerializedName("chapter_id")
    private int chapterId;

    @SerializedName("open_time")
    private int openTime;

    @SerializedName("close_time")
    private int closeTime;

    public int getChapterId() {
        return chapterId;
    }

    public int getOpenTime() {
        return openTime;
    }

    public int getCloseTime() {
        return closeTime;
    }

    @Override
    public int getId() {
        return id;
    }
}