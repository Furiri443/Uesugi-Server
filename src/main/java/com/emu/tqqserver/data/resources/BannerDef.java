package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "banner.json")
public class BannerDef extends BaseDef {
    private int id;

    private int type;

    private int priority;

    private String title;

    @SerializedName("resource_id")
    private int resourceId;

    private int ios;

    private int android;

    private int pc;

    private String link;

    @SerializedName("link_type")
    private int linkType;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("resource_type")
    private int resourceType;

    @SerializedName("feature_id")
    private int featureId;

    public int getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getIos() {
        return ios;
    }

    public int getAndroid() {
        return android;
    }

    public int getPc() {
        return pc;
    }

    public String getLink() {
        return link;
    }

    public int getLinkType() {
        return linkType;
    }

    public long getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getResourceType() {
        return resourceType;
    }

    public int getFeatureId() {
        return featureId;
    }

    @Override
    public int getId() {
        return id;
    }
}