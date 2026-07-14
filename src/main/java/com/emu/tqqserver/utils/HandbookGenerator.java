package com.emu.tqqserver.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class HandbookGenerator {
    private static final Logger log = LoggerFactory.getLogger(HandbookGenerator.class);

    public static void generateHandbook() {
        try {
            File masterDir = new File("gotopazu/master");
            if (!masterDir.exists()) {
                masterDir = new File("Uesugi-Resources/master");
            }
            if (!masterDir.exists()) {
                masterDir = new File(com.emu.tqqserver.game.GameContext.getInstance().getConfig().getResourceListDir(), "master");
            }
            if (!masterDir.exists()) {
                log.warn("Master data directory not found, skipping handbook generation.");
                return;
            }

            File textFile = new File(masterDir, "text.json");
            File itemFile = new File(masterDir, "item.json");
            File cardFile = new File(masterDir, "card.json");
            File titleFile = new File(masterDir, "player_title.json");
            File bgFile = new File(masterDir, "home_background.json");

            if (!textFile.exists()) return;

            ObjectMapper mapper = new ObjectMapper();
            JsonNode textRoot = mapper.readTree(textFile);
            JsonNode itemText = textRoot.path("ItemText").path("map_");
            JsonNode cardText = textRoot.path("CardText").path("map_");
            JsonNode titleText = textRoot.path("PlayerTitleText").path("map_");
            JsonNode homeText = textRoot.path("HomeText").path("map_");

            File outFile = new File("handbook.txt");
            try (PrintWriter writer = new PrintWriter(new FileWriter(outFile, java.nio.charset.StandardCharsets.UTF_8))) {
                writer.println("=== GOTOPAZU PRIVATE SERVER HANDBOOK ===");
                writer.println();
                writer.println("--- Commands ---");
                writer.println("Run commands in the server console like this:");
                writer.println("/addCoin <amount> <userId> - Add coins");
                writer.println("/addItem <itemId> <amount> <userId> - Add item by ID");
                writer.println("/addJewel <amount> <userId> - Add free jewels");
                writer.println("/addPayJewel <amount> <userId> - Add paid jewels");
                writer.println("/giveAll <userId> - Give all resources/items");
                writer.println("/give <itemId> <amount> <userId> - Synonym for addItem");
                writer.println("/setLevel <level> <userId> - Set player level");
                writer.println();

                if (itemFile.exists()) {
                    writer.println("--- Item IDs ---");
                    JsonNode items = mapper.readTree(itemFile);
                    for (JsonNode item : items) {
                        String id = item.has("itemId") ? item.get("itemId").asText() : item.path("id").asText("N/A");
                        String nameId = item.path("name_text_id").asText("");
                        String descId = item.path("description_text_id").asText("");
                        String name = itemText.path(nameId).asText("N/A");
                        String desc = itemText.path(descId).asText("").replace("\n", " ");
                        writer.println("[" + id + "] " + name + " - " + desc);
                    }
                    writer.println();
                }

                if (cardFile.exists()) {
                    writer.println("--- Card IDs ---");
                    JsonNode cards = mapper.readTree(cardFile);
                    for (JsonNode card : cards) {
                        String id = card.has("cardId") ? card.get("cardId").asText() : card.path("id").asText("N/A");
                        String nameId = card.path("name_text_id").asText("");
                        String name = cardText.path(nameId).asText("N/A");
                        writer.println("[" + id + "] " + name);
                    }
                    writer.println();
                }

                if (titleFile.exists()) {
                    writer.println("--- Title IDs ---");
                    JsonNode titles = mapper.readTree(titleFile);
                    for (JsonNode title : titles) {
                        String id = title.has("playerTitleId") ? title.get("playerTitleId").asText() : title.path("id").asText("N/A");
                        String nameId = title.path("name_text_id").asText("");
                        String name = titleText.path(nameId).asText("N/A");
                        writer.println("[" + id + "] " + name);
                    }
                    writer.println();
                }

                if (bgFile.exists()) {
                    writer.println("--- Background IDs ---");
                    JsonNode bgs = mapper.readTree(bgFile);
                    for (JsonNode bg : bgs) {
                        String id = bg.has("homeBackgroundId") ? bg.get("homeBackgroundId").asText() : bg.path("id").asText("N/A");
                        String nameId = bg.path("name_text_id").asText("");
                        String name = homeText.path(nameId).asText("N/A");
                        writer.println("[" + id + "] " + name);
                    }
                    writer.println();
                }
            }
            log.info("Handbook generated at handbook.txt");
        } catch (Exception e) {
            log.warn("Failed to generate handbook", e);
        }
    }
}
