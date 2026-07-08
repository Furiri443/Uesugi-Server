package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "costume.json")
public class CostumeDef extends BaseDef {
    private int id;

    private int sort;

    @SerializedName("costume_id")
    private String costumeId;

    private String name;

    public int getSort() {
        return sort;
    }

    public String getCostumeId() {
        return costumeId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }
}