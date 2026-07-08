package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "ap.json")
public class ApDef extends BaseDef {
    private int id;

    private String name;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("recover_amount")
    private int recoverAmount;

    private int price;

    private int type;

    public String getName() {
        return name;
    }

    public String getProductId() {
        return productId;
    }

    public int getRecoverAmount() {
        return recoverAmount;
    }

    public int getPrice() {
        return price;
    }

    public int getType() {
        return type;
    }

    @Override
    public int getId() {
        return id;
    }
}