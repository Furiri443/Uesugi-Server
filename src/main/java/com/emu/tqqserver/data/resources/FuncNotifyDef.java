package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "func_notify.json")
public class FuncNotifyDef extends BaseDef {
    private int id;

    private String title;

    @SerializedName("message_")
    private String message;

    private int order;

    private String link;

    private float version;

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public int getOrder() {
        return order;
    }

    public String getLink() {
        return link;
    }

    public float getVersion() {
        return version;
    }

    @Override
    public int getId() {
        return id;
    }
}