package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "practice_exam.json")
public class PracticeExamDef extends BaseDef {
    private int id;

    @SerializedName("series_no")
    private int seriesNo;

    private int type;

    private String name;

    public int getSeriesNo() {
        return seriesNo;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }
}