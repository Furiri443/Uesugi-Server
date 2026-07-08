package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "vr_content.json")
public class VrContentDef extends BaseDef {
    private int id;

    @SerializedName("vr_chapter_id")
    private int vrChapterId;

    private String name;

    private String time;

    private String size;

    @SerializedName("item_id")
    private int itemId;

    private int quantity;

    @SerializedName("sort_id")
    private int sortId;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    public int getVrChapterId() {
        return vrChapterId;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getSize() {
        return size;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSortId() {
        return sortId;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    @Override
    public int getId() {
        return id;
    }
}