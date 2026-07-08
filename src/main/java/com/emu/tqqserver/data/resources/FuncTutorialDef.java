package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "func_tutorial.json")
public class FuncTutorialDef extends BaseDef {
    private int id;

    private String title;

    private int level;

    @SerializedName("org_scene")
    private String orgScene;

    private String scene;

    private int version;

    public String getTitle() {
        return title;
    }

    public int getLevel() {
        return level;
    }

    public String getOrgScene() {
        return orgScene;
    }

    public String getScene() {
        return scene;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public int getId() {
        return id;
    }
}