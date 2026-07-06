package com.emu.tqqserver.game.shop;

import com.emu.tqqserver.proto.pkg_proto.IAPProduct;
import com.emu.tqqserver.proto.pkg_proto.IAPProductList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IAPProductService {

    private static final Logger log = LoggerFactory.getLogger(IAPProductService.class);
    private static volatile IAPProductService instance;

    private final IAPProductList productList;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShopItemJson {
        public int id;
        @JsonProperty("storeid")
        public String storeId;
        public String name;
        public String type;
        @JsonProperty("type2")
        public String type2;
        public String receive;
        public double yen;
        public int goldcoin;
        public int limit;
    }

    private IAPProductService(String resourceListDir) {
        this.productList = loadProducts(resourceListDir);
    }

    public static void initialize(String resourceListDir) {
        instance = new IAPProductService(resourceListDir);
    }

    public static IAPProductService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("IAPProductService not initialized");
        }
        return instance;
    }

    public IAPProductList getProductList() {
        return productList;
    }

    private IAPProductList loadProducts(String resourceListDir) {
        File file = new File(resourceListDir, "shop.json");
        if (!file.exists()) {
            log.warn("Shop JSON file not found at: {} - using empty product list", file.getAbsolutePath());
            return IAPProductList.getDefaultInstance();
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            ShopItemJson[] items = mapper.readValue(file, ShopItemJson[].class);

            List<IAPProduct> list = new ArrayList<>();
            for (ShopItemJson item : items) {
                if (item.storeId == null || item.storeId.isEmpty()) {
                    continue; // Skip items without store product ID
                }
                IAPProduct.Builder builder = IAPProduct.newBuilder()
                    .setId(item.id)
                    .setProductId(item.storeId)
                    .setName(item.name != null ? item.name : "")
                    .setConsumeType(item.type != null ? item.type : "consumable")
                    .setSellingType(item.type2 != null ? item.type2 : "normal")
                    .setPurchaseType(item.receive != null ? item.receive : "normal")
                    .setPrice(item.yen)
                    .setCoin(item.goldcoin)
                    .setEnabled(true)
                    .setPurchaseCountMax(item.limit > 0 ? item.limit : 99);
                list.add(builder.build());
            }

            log.info("Loaded {} products from shop.json", list.size());
            return IAPProductList.newBuilder().addAllList(list).build();

        } catch (IOException e) {
            log.error("Failed to read shop.json", e);
            return IAPProductList.getDefaultInstance();
        }
    }
}
