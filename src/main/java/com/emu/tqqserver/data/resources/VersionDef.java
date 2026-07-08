package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "version.json")
public class VersionDef extends BaseDef {
    private String platform;

    private String application;

    private int resource;

    private int master;

    private int term;

    private int revision;

    public String getPlatform() {
        return platform;
    }

    public String getApplication() {
        return application;
    }

    public int getResource() {
        return resource;
    }

    public int getMaster() {
        return master;
    }

    public int getTerm() {
        return term;
    }

    public int getRevision() {
        return revision;
    }

    @Override
    public int getId() {
        return 0;
    }
}