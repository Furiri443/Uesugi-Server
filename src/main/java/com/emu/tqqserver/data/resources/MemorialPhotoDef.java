package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "memorial_photo.json")
public class MemorialPhotoDef extends BaseDef {
    private int id;

    private int character1;

    public int getCharacter1() {
        return character1;
    }

    @Override
    public int getId() {
        return id;
    }
}