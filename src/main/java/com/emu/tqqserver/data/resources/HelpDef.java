package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "help.json")
public class HelpDef extends BaseDef {
    private int id;

    private int ios;

    private int android;

    private int pc;

    private String title;

    @SerializedName("parent_id")
    private int parentId;

    private String body;

    private int type;

    @SerializedName("mail_body")
    private String mailBody;

    public int getIos() {
        return ios;
    }

    public int getAndroid() {
        return android;
    }

    public int getPc() {
        return pc;
    }

    public String getTitle() {
        return title;
    }

    public int getParentId() {
        return parentId;
    }

    public String getBody() {
        return body;
    }

    public int getType() {
        return type;
    }

    public String getMailBody() {
        return mailBody;
    }

    @Override
    public int getId() {
        return id;
    }
}