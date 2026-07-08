package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "special_content.json")
public class SpecialContentDef extends BaseDef {
    private int id;

    private int type;

    @SerializedName("sort_id")
    private int sortId;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("thumbnail_resource_id")
    private int thumbnailResourceId;

    @SerializedName("banner_resource_id")
    private int bannerResourceId;

    private String name;

    private String comment;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("target_uid")
    private int targetUid;

    public int getType() {
        return type;
    }

    public int getSortId() {
        return sortId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getThumbnailResourceId() {
        return thumbnailResourceId;
    }

    public int getBannerResourceId() {
        return bannerResourceId;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getTargetUid() {
        return targetUid;
    }

    @Override
    public int getId() {
        return id;
    }
}