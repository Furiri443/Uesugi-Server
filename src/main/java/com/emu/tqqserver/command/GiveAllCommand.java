package com.emu.tqqserver.command;

import com.emu.tqqserver.game.user.UserDao;
import com.emu.tqqserver.game.user.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GiveAllCommand implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(GiveAllCommand.class);
    private final UserService userService = new UserService();
    private final UserDao userDao = new UserDao();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String getName() {
        return "give_all";
    }

    @Override
    public String getDescription() {
        return "Gives all cards and items to the specified user. Usage: /give_all <userId> [type: cards|items|backgrounds|clothes|all]";
    }

    @Override
    public void execute(long executorId, String[] args) {
        if (args.length < 2) {
            log.warn(getDescription());
            return;
        }

        long targetUserId;
        try {
            targetUserId = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            log.warn("Invalid userId: {}", args[1]);
            return;
        }

        String type = "all";
        if (args.length >= 3) {
            type = args[2].toLowerCase();
        }

        String masterDir = System.getProperty("gotopazu.resource.list.dir", "gotopazu");
        File cardFile = new File(masterDir, "master/card.json");
        File itemFile = new File(masterDir, "master/item.json");

        try {
            // Give all cards
            if (type.equals("all") || type.equals("cards")) {
                if (cardFile.exists()) {
                    JsonNode cardArray = mapper.readTree(cardFile);
                    List<Integer> cardIds = new ArrayList<>();
                    if (cardArray.isArray()) {
                        for (JsonNode cardNode : cardArray) {
                            cardIds.add(cardNode.get("id").asInt());
                        }
                    }
                    
                    int[] cards = cardIds.stream().mapToInt(i -> i).toArray();
                    userDao.ensureDefaultCards(targetUserId, cards);
                    log.info("Successfully gave {} cards to user {}", cards.length, targetUserId);
                } else {
                    log.warn("card.json not found in {}", masterDir);
                }
            }

            // Give all items
            if (type.equals("all") || type.equals("items")) {
                if (itemFile.exists()) {
                    JsonNode itemArray = mapper.readTree(itemFile);
                    if (itemArray.isArray()) {
                        for (JsonNode itemNode : itemArray) {
                            int itemId = itemNode.get("id").asInt();
                            userService.addItem(targetUserId, itemId, 999);
                        }
                    }
                    log.info("Successfully gave all items to user {}", targetUserId);
                } else {
                    log.warn("item.json not found in {}", masterDir);
                }
            }

            // Give all backgrounds
            if (type.equals("all") || type.equals("backgrounds")) {
                File bgFile = new File(masterDir, "master/home_background.json");
                if (bgFile.exists()) {
                    JsonNode bgArray = mapper.readTree(bgFile);
                    if (bgArray.isArray()) {
                        for (JsonNode bgNode : bgArray) {
                            int bgId = bgNode.get("id").asInt();
                            userService.addHomeBackground(targetUserId, bgId);
                        }
                    }
                    log.info("Successfully gave all home backgrounds to user {}", targetUserId);
                }
            }

            // Give all clothes
            if (type.equals("all") || type.equals("clothes")) {
                File clothesFile = new File(masterDir, "master/home_member_clothes.json");
                if (clothesFile.exists()) {
                    JsonNode clothesArray = mapper.readTree(clothesFile);
                    if (clothesArray.isArray()) {
                        for (JsonNode clothesNode : clothesArray) {
                            int clothesId = clothesNode.get("id").asInt();
                            userService.addClothes(targetUserId, clothesId);
                        }
                    }
                    log.info("Successfully gave all clothes to user {}", targetUserId);
                }
            }

            // Force reload for this user if they are online
            com.emu.tqqserver.network.websocket.GameSession session = com.emu.tqqserver.network.websocket.GameWebSocketHandler.getSessionByUserId(targetUserId);
            if (session != null) {
                com.emu.tqqserver.proto.pkg_prealtime.Error error = com.emu.tqqserver.proto.pkg_prealtime.Error.newBuilder()
                    .setCode(10401)
                    .setMsg("Đã add thành công. Đang tải lại dữ liệu...")
                    .build();
                session.sendPush(1, error.toByteArray());
            }

        } catch (Exception e) {
            log.error("Failed to execute give_all", e);
        }
    }
}
