package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "shop.json")
public class ShopDef extends BaseDef {
    private int id;

    @SerializedName("sort_id")
    private int sortId;

    @SerializedName("product_id")
    private String productId;

    private int type;

    private int pay;

    @SerializedName("group_id")
    private int groupId;

    @SerializedName("group_seq")
    private int groupSeq;

    @SerializedName("name_text_id")
    private int nameTextId;

    @SerializedName("description_text_id")
    private int descriptionTextId;

    @SerializedName("detail_text_id")
    private int detailTextId;

    @SerializedName("img_id")
    private int imgId;

    private int price;

    private int stock;

    @SerializedName("restock_date")
    private long restockDate;

    @SerializedName("open_date")
    private long openDate;

    @SerializedName("close_date")
    private long closeDate;

    @SerializedName("reward_id")
    private int rewardId;

    @SerializedName("pay_item_id")
    private int payItemId;

    @SerializedName("restock_type")
    private int restockType;

    @SerializedName("detail_img_id")
    private int detailImgId;

    @SerializedName("restock_interval")
    private int restockInterval;

    @SerializedName("purchased_shop_id")
    private int purchasedShopId;

    @SerializedName("mileage_grade")
    private int mileageGrade;

    public int getSortId() {
        return sortId;
    }

    public String getProductId() {
        return productId;
    }

    public int getType() {
        return type;
    }

    public int getPay() {
        return pay;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getGroupSeq() {
        return groupSeq;
    }

    public int getNameTextId() {
        return nameTextId;
    }

    public int getDescriptionTextId() {
        return descriptionTextId;
    }

    public int getDetailTextId() {
        return detailTextId;
    }

    public int getImgId() {
        return imgId;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public long getRestockDate() {
        return restockDate;
    }

    public long getOpenDate() {
        return openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public int getRewardId() {
        return rewardId;
    }

    public int getPayItemId() {
        return payItemId;
    }

    public int getRestockType() {
        return restockType;
    }

    public int getDetailImgId() {
        return detailImgId;
    }

    public int getRestockInterval() {
        return restockInterval;
    }

    public int getPurchasedShopId() {
        return purchasedShopId;
    }

    public int getMileageGrade() {
        return mileageGrade;
    }

    @Override
    public int getId() {
        return id;
    }
}