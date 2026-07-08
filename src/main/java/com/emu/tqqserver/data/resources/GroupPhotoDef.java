package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "group_photo.json")
public class GroupPhotoDef extends BaseDef {
    private int id;

    private int direction;

    private String name;

    private String comment;

    public int getDirection() {
        return direction;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public int getId() {
        return id;
    }
}