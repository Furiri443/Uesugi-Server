package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "offer_wall.json")
public class OfferWallDef extends BaseDef {
    private int id;

    @SerializedName("resource_id")
    private int resourceId;

    private int ios;

    private int android;

    private int pc;

    private int priority;

    private int interval;

    private String link;

    @SerializedName("link_type")
    private int linkType;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("close_date")
    private long closeDate;

    private int type;

    @SerializedName("resource_type")
    private int resourceType;

    @SerializedName("link_id")
    private int linkId;

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

    public int getPriority() {
        return priority;
    }

    public int getInterval() {
        return interval;
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

    public int getType() {
        return type;
    }

    public int getResourceType() {
        return resourceType;
    }

    public int getLinkId() {
        return linkId;
    }

    @Override
    public int getId() {
        return id;
    }
}