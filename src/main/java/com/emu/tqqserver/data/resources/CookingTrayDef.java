package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "cooking_tray.json")
public class CookingTrayDef extends BaseDef {
    private int index;

    @SerializedName("tray_open_type")
    private int trayOpenType;

    private int value;

    public int getIndex() {
        return index;
    }

    public int getTrayOpenType() {
        return trayOpenType;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int getId() {
        return 0;
    }
}