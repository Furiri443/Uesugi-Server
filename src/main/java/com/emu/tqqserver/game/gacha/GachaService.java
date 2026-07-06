package com.emu.tqqserver.game.gacha;

import com.emu.tqqserver.masterdata.MasterDataLoader;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GachaService {
    private static final Logger log = LoggerFactory.getLogger(GachaService.class);
    private final Random rand = new Random();

    public int drawCard(int gachaId, boolean isBonus) {
        try {
            // 1. Get gacha details
            List<JsonNode> gachas = MasterDataLoader.getList("gacha.json");
            if (gachas == null) return fallbackDraw();
            
            JsonNode gacha = null;
            for (JsonNode g : gachas) {
                if (g.path("id").asInt() == gachaId) {
                    gacha = g;
                    break;
                }
            }
            if (gacha == null) return fallbackDraw();
            
            int lineupId = gacha.path("lineup_id").asInt();

            // 2. Select group
            List<JsonNode> groupWeights = MasterDataLoader.getList("gacha_group_weight.json");
            if (groupWeights == null) return fallbackDraw();
            
            List<JsonNode> validGroups = new ArrayList<>();
            int totalGroupWeight = 0;
            for (JsonNode gw : groupWeights) {
                if (gw.path("gacha_id").asInt() == gachaId) {
                    int weight = isBonus ? gw.path("bonus_weight").asInt(0) : gw.path("weight").asInt(0);
                    if (weight > 0) {
                        validGroups.add(gw);
                        totalGroupWeight += weight;
                    }
                }
            }
            
            if (totalGroupWeight == 0) return fallbackDraw();
            
            int rGroup = rand.nextInt(totalGroupWeight) + 1;
            int currentGroupWeight = 0;
            int selectedGroupId = -1;
            
            for (JsonNode gw : validGroups) {
                currentGroupWeight += isBonus ? gw.path("bonus_weight").asInt(0) : gw.path("weight").asInt(0);
                if (rGroup <= currentGroupWeight) {
                    selectedGroupId = gw.path("group_id").asInt();
                    break;
                }
            }
            
            if (selectedGroupId == -1) return fallbackDraw();

            // 3. Select card from lineup
            List<JsonNode> lineups = MasterDataLoader.getList("gacha_lineup.json");
            if (lineups == null) return fallbackDraw();
            
            List<JsonNode> validLineups = new ArrayList<>();
            int totalLineupWeight = 0;
            for (JsonNode lu : lineups) {
                if (lu.path("lineup_id").asInt() == lineupId && lu.path("group_id").asInt() == selectedGroupId) {
                    int weight = isBonus ? lu.path("bonus_weight").asInt(0) : lu.path("weight").asInt(0);
                    if (weight > 0) {
                        validLineups.add(lu);
                        totalLineupWeight += weight;
                    }
                }
            }
            
            if (totalLineupWeight == 0) return fallbackDraw();
            
            int rCard = rand.nextInt(totalLineupWeight) + 1;
            int currentLineupWeight = 0;
            for (JsonNode lu : validLineups) {
                currentLineupWeight += isBonus ? lu.path("bonus_weight").asInt(0) : lu.path("weight").asInt(0);
                if (rCard <= currentLineupWeight) {
                    return lu.path("card_id").asInt();
                }
            }

            return fallbackDraw();
        } catch (Exception e) {
            log.error("Error during gacha draw", e);
            return fallbackDraw();
        }
    }
    
    private int fallbackDraw() {
        return 10001 + rand.nextInt(20);
    }
}
