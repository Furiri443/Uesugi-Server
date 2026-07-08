package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "shop_exchange_group.json")
public class ShopExchangeGroupDef extends BaseDef {
    @SerializedName("shop_type")
    private int shopType;

    @SerializedName("group_id")
    private int groupId;

    private int num;

    public int getShopType() {
        return shopType;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getNum() {
        return num;
    }

    @Override
    public int getId() {
        return 0;
    }
}