package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "feature_howto.json")
public class FeatureHowtoDef extends BaseDef {
    @SerializedName("feature_id")
    private int featureId;

    private int no;

    @SerializedName("resource_id")
    private int resourceId;

    @SerializedName("link_type")
    private int linkType;

    private String link;

    @SerializedName("link_text")
    private String linkText;

    @SerializedName("btn_term")
    private String btnTerm;

    @SerializedName("header_text_id")
    private int headerTextId;

    private String body;

    private int revival;

    public int getFeatureId() {
        return featureId;
    }

    public int getNo() {
        return no;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getLinkType() {
        return linkType;
    }

    public String getLink() {
        return link;
    }

    public String getLinkText() {
        return linkText;
    }

    public String getBtnTerm() {
        return btnTerm;
    }

    public int getHeaderTextId() {
        return headerTextId;
    }

    public String getBody() {
        return body;
    }

    public int getRevival() {
        return revival;
    }

    @Override
    public int getId() {
        return 0;
    }
}