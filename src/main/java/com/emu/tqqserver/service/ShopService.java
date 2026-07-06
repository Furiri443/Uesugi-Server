package com.emu.tqqserver.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ShopService {
    private static final Logger log = LoggerFactory.getLogger(ShopService.class);
    private static final String MASTER_DIR = "gotopazu/master/";

    private static List<Map<String, Object>> shopData = new ArrayList<>();
    private static List<Map<String, Object>> rewardData = new ArrayList<>();
    private static Map<Integer, Map<String, Object>> shopItemMap = new HashMap<>();
    private static Map<Integer, List<Map<String, Object>>> rewardMap = new HashMap<>();

    static {
        loadData();
    }

    public static void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            File shopFile = new File(MASTER_DIR + "shop.json");
            if (shopFile.exists()) {
                shopData = mapper.readValue(shopFile, new TypeReference<List<Map<String, Object>>>() {});
                for (Map<String, Object> item : shopData) {
                    if (item.containsKey("id")) {
                        shopItemMap.put((Integer) item.get("id"), item);
                    }
                }
                log.info("Loaded {} shop items", shopData.size());
            }

            File rewardFile = new File(MASTER_DIR + "reward.json");
            if (rewardFile.exists()) {
                rewardData = mapper.readValue(rewardFile, new TypeReference<List<Map<String, Object>>>() {});
                for (Map<String, Object> r : rewardData) {
                    if (r.containsKey("id")) {
                        int id = (Integer) r.get("id");
                        rewardMap.computeIfAbsent(id, k -> new ArrayList<>()).add(r);
                    }
                }
                log.info("Loaded {} rewards", rewardData.size());
            }

        } catch (Exception e) {
            log.error("Failed to load shop master data", e);
        }
    }

    public Map<String, Object> getShopItem(int id) {
        return shopItemMap.get(id);
    }

    public List<Map<String, Object>> getRewards(int rewardId) {
        return rewardMap.getOrDefault(rewardId, new ArrayList<>());
    }
}
