package com.emu.tqqserver.game.gacha;


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
            java.util.Collection<com.emu.tqqserver.data.resources.GachaDef> gachas = com.emu.tqqserver.data.GameData.getGachaDataTable().values();
            if (gachas.isEmpty()) return fallbackDraw();
            
            com.emu.tqqserver.data.resources.GachaDef gacha = com.emu.tqqserver.data.GameData.getGachaDataTable().get(gachaId);
            if (gacha == null) return fallbackDraw();
            
            int lineupId = gacha.getLineupId();

            // 2. Select group
            java.util.Collection<com.emu.tqqserver.data.resources.GachaGroupWeightDef> groupWeights = com.emu.tqqserver.data.GameData.getGachaGroupWeightDataTable().values();
            if (groupWeights.isEmpty()) return fallbackDraw();
            
            List<com.emu.tqqserver.data.resources.GachaGroupWeightDef> validGroups = new ArrayList<>();
            int totalGroupWeight = 0;
            for (com.emu.tqqserver.data.resources.GachaGroupWeightDef gw : groupWeights) {
                if (gw.getGachaId() == gachaId) {
                    int weight = isBonus ? gw.getBonusWeight() : gw.getWeight();
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
            
            for (com.emu.tqqserver.data.resources.GachaGroupWeightDef gw : validGroups) {
                currentGroupWeight += isBonus ? gw.getBonusWeight() : gw.getWeight();
                if (rGroup <= currentGroupWeight) {
                    selectedGroupId = gw.getGroupId();
                    break;
                }
            }
            
            if (selectedGroupId == -1) return fallbackDraw();

            // 3. Select card from lineup
            java.util.Collection<com.emu.tqqserver.data.resources.GachaLineupDef> lineups = com.emu.tqqserver.data.GameData.getGachaLineupDataTable().values();
            if (lineups.isEmpty()) return fallbackDraw();
            
            List<com.emu.tqqserver.data.resources.GachaLineupDef> validLineups = new ArrayList<>();
            int totalLineupWeight = 0;
            for (com.emu.tqqserver.data.resources.GachaLineupDef lu : lineups) {
                if (lu.getLineupId() == lineupId && lu.getGroupId() == selectedGroupId) {
                    int weight = isBonus ? lu.getBonusWeight() : lu.getWeight();
                    if (weight > 0) {
                        validLineups.add(lu);
                        totalLineupWeight += weight;
                    }
                }
            }
            
            if (totalLineupWeight == 0) return fallbackDraw();
            
            int rCard = rand.nextInt(totalLineupWeight) + 1;
            int currentLineupWeight = 0;
            for (com.emu.tqqserver.data.resources.GachaLineupDef lu : validLineups) {
                currentLineupWeight += isBonus ? lu.getBonusWeight() : lu.getWeight();
                if (rCard <= currentLineupWeight) {
                    return lu.getCardId();
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
