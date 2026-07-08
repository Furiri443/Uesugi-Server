package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "tutorial.json")
public class TutorialDef extends BaseDef {
    private String title;

    private int step;

    private String scene;

    public String getTitle() {
        return title;
    }

    public int getStep() {
        return step;
    }

    public String getScene() {
        return scene;
    }

    @Override
    public int getId() {
        return 0;
    }
}