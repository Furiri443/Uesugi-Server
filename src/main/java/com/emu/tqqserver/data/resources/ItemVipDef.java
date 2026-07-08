package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "item_vip.json")
public class ItemVipDef extends BaseDef {
    @SerializedName("item_id")
    private int itemId;

    private int type;

    private String label;

    private int value;

    @SerializedName("target_id")
    private int targetId;

    public int getItemId() {
        return itemId;
    }

    public int getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    public int getTargetId() {
        return targetId;
    }

    @Override
    public int getId() {
        return 0;
    }
}