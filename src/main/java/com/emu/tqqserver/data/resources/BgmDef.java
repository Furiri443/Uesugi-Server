package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "bgm.json")
public class BgmDef extends BaseDef {
    private int id;

    @SerializedName("media_id")
    private int mediaId;

    private String title;

    private String filename;

    public int getMediaId() {
        return mediaId;
    }

    public String getTitle() {
        return title;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public int getId() {
        return id;
    }
}