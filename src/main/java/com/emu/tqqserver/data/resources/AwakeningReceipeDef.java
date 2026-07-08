package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "awakening_receipe.json")
public class AwakeningReceipeDef extends BaseDef {
    private int id;

    private int leaf;

    private int material1;

    private int material2;

    private int material3;

    private int value1;

    private int value2;

    private int value3;

    public int getLeaf() {
        return leaf;
    }

    public int getMaterial1() {
        return material1;
    }

    public int getMaterial2() {
        return material2;
    }

    public int getMaterial3() {
        return material3;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }

    public int getValue3() {
        return value3;
    }

    @Override
    public int getId() {
        return id;
    }
}