package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "stage_ex.json")
public class StageExDef extends BaseDef {
    private int id;

    private int rank;

    private String information;

    public int getRank() {
        return rank;
    }

    public String getInformation() {
        return information;
    }

    @Override
    public int getId() {
        return id;
    }
}