package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "shop_exchange.json")
public class ShopExchangeDef extends BaseDef {
    private int id;

    @SerializedName("shop_type")
    private int shopType;

    @SerializedName("banner_resource_id")
    private int bannerResourceId;

    @SerializedName("sort_priority")
    private int sortPriority;

    @SerializedName("open_date")
    private int openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("pay_item_id")
    private int payItemId;

    @SerializedName("pay_item_name_abbr")
    private String payItemNameAbbr;

    private String link;

    public int getShopType() {
        return shopType;
    }

    public int getBannerResourceId() {
        return bannerResourceId;
    }

    public int getSortPriority() {
        return sortPriority;
    }

    public int getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getPayItemId() {
        return payItemId;
    }

    public String getPayItemNameAbbr() {
        return payItemNameAbbr;
    }

    public String getLink() {
        return link;
    }

    @Override
    public int getId() {
        return id;
    }
}