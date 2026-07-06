package com.emu.tqqserver.game.user;

import com.emu.tqqserver.game.home.HomeService;


import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.proto.pkg_proto.StoredData;
import com.emu.tqqserver.proto.pkg_puser.Currency;
import com.emu.tqqserver.proto.pkg_puser.User;

/**
 * Chuyển trạng thái người chơi ({@link UserEntity}, lấy từ SQLite) thành
 * proto {@code pkg_proto.StoredData} thật — phần dữ liệu động đính kèm
 * mọi response (khác với master data tĩnh trong {@link MasterDataService}).
 */
public class StoredDataService {
    private final HomeService homeService = new HomeService();

        /** Build StoredData tối thiểu (user + currency) cho một user hiện tại. */
        public StoredData build(UserEntity user) {
                User protoUser = user.toProto();
                Currency currency = user.toCurrencyProto();

                com.emu.tqqserver.game.user.UnitDao unitDao = new com.emu.tqqserver.game.user.UnitDao();
                java.util.List<com.emu.tqqserver.proto.pkg_puser.Unit> units = unitDao.getUnits(user.getUserId());


                com.emu.tqqserver.game.user.MemberDao memberDao = new com.emu.tqqserver.game.user.MemberDao();
                java.util.List<com.emu.tqqserver.proto.pkg_puser.Member> members = memberDao.getMembers(user.getUserId());
                if (members.isEmpty()) {
                        memberDao.initializeMembers(user.getUserId());
                        members = memberDao.getMembers(user.getUserId());
                }

                com.emu.tqqserver.proto.pkg_puser.Chapter.Builder chapterBuilder = com.emu.tqqserver.proto.pkg_puser.Chapter
                                .newBuilder()
                                .setUid((int) user.getUserId())
                                .setStatus(1);
                                
                java.util.List<com.fasterxml.jackson.databind.JsonNode> chapters = com.emu.tqqserver.masterdata.MasterDataLoader.getList("chapter.json");
                if (!chapters.isEmpty()) {
                        chapterBuilder.setChapterId(chapters.get(0).get("id").asInt());
                } else {
                        chapterBuilder.setChapterId(1);
                }

                com.emu.tqqserver.proto.pkg_puser.Stage.Builder stageBuilder = com.emu.tqqserver.proto.pkg_puser.Stage.newBuilder()
                                .setUid((int) user.getUserId())
                                .setRank(3);
                                
                java.util.List<com.fasterxml.jackson.databind.JsonNode> stages = com.emu.tqqserver.masterdata.MasterDataLoader.getList("stage.json");
                if (!stages.isEmpty()) {
                        stageBuilder.setStageId(stages.get(0).get("id").asInt());
                } else {
                        stageBuilder.setStageId(1);
                }

                com.emu.tqqserver.proto.pkg_pmisc.Work work = com.emu.tqqserver.proto.pkg_pmisc.Work.newBuilder()
                                .setUid((int) user.getUserId())
                                .setLastWorkDate((int) (System.currentTimeMillis() / 1000))
                                .setLastLineupUpdateDate((int) (System.currentTimeMillis() / 1000))
                                .setLastResetDate((int) (System.currentTimeMillis() / 1000))
                                .build();

                com.emu.tqqserver.proto.pkg_puser.Mileage mileage = com.emu.tqqserver.proto.pkg_puser.Mileage
                                .newBuilder()
                                .setUid((int) user.getUserId())
                                .setPoint(0)
                                .setGrade(1)
                                .setDailyRewardReceivedAt(user.getDailyRewardReceivedAt())
                                .setPuzzleSkipCount(0)
                                .setPuzzleSkippedAt(0)
                                .build();

                com.emu.tqqserver.proto.pkg_puser.Options options = com.emu.tqqserver.proto.pkg_puser.Options
                                .newBuilder()
                                .setUid((int) user.getUserId())
                                .setBgm(user.getOptionBgm())
                                .setSe(user.getOptionSe())
                                .setVoice(user.getOptionVoice())
                                .setProtectCardR6(user.getOptionProtectCardR6())
                                .setProtectCardR5(user.getOptionProtectCardR5())
                                .setProtectCardFirst(user.getOptionProtectCardFirst())
                                .build();

                com.emu.tqqserver.proto.pkg_puser.HomeBackground homeBg = com.emu.tqqserver.proto.pkg_puser.HomeBackground
                                .newBuilder()
                                .setUid((int) user.getUserId())
                                .setHomeBackgroundId(user.getHomeBackgroundId() > 0 ? user.getHomeBackgroundId() : 10001)
                                .build();

                com.emu.tqqserver.game.home.HomeService homeService = new com.emu.tqqserver.game.home.HomeService();
                java.util.List<com.emu.tqqserver.proto.pkg_puser.HomeActor> homeActors = homeService.getHomeActors(user.getUserId());

                StoredData.Builder builder = StoredData.newBuilder()
                                .setUser(protoUser)
                                .addAllHomeActor(homeActors)
                                .setCurrency(currency)
                                .addAllUnit(units)
                                .addAllMember(members)
                                .addChapter(chapterBuilder.build())
                                .addStage(stageBuilder.build())
                                .setWork(work)
                                .setMileage(mileage)
                                .setOptions(options);
                                
                UserService userService = new UserService();
                java.util.List<Integer> bgIds = userService.getHomeBackgrounds(user.getUserId());
                if (bgIds.isEmpty()) {
                    builder.addHomeBackground(com.emu.tqqserver.proto.pkg_puser.HomeBackground.newBuilder()
                            .setUid((int) user.getUserId())
                            .setHomeBackgroundId(user.getHomeBackgroundId() > 0 ? user.getHomeBackgroundId() : 10001)
                            .build());
                } else {
                    for (int bgId : bgIds) {
                        builder.addHomeBackground(com.emu.tqqserver.proto.pkg_puser.HomeBackground.newBuilder()
                                .setUid((int) user.getUserId())
                                .setHomeBackgroundId(bgId)
                                .build());
                    }
                }

                java.util.List<Integer> clothesIds = userService.getClothes(user.getUserId());
                java.util.List<com.fasterxml.jackson.databind.JsonNode> masterClothes = com.emu.tqqserver.masterdata.MasterDataLoader.getList("home_member_clothes.json");
                java.util.Map<Integer, com.fasterxml.jackson.databind.JsonNode> clothesMap = new java.util.HashMap<>();
                for (com.fasterxml.jackson.databind.JsonNode node : masterClothes) {
                    clothesMap.put(node.path("id").asInt(), node);
                }
                for (int clothesId : clothesIds) {
                    com.fasterxml.jackson.databind.JsonNode node = clothesMap.get(clothesId);
                    if (node != null) {
                        builder.addHomeMemberClothes(com.emu.tqqserver.proto.pkg_pmaster.HomeMemberClothes.newBuilder()
                                .setId(clothesId)
                                .setMemberId(node.path("member_id").asInt())
                                .setCardId(node.path("card_id").asInt())
                                .setName(node.path("name").asText())
                                .setType(node.path("type").asInt())
                                .setThumbnailResourceId(node.path("thumbnail_resource_id").asInt())
                                .setIsDefault(node.path("is_default").asInt())
                                .build());
                    }
                }

                // Load all BGMs
                java.util.List<com.fasterxml.jackson.databind.JsonNode> bgms = com.emu.tqqserver.masterdata.MasterDataLoader.getList("bgm.json");
                if (bgms != null) {
                    for (com.fasterxml.jackson.databind.JsonNode node : bgms) {
                        builder.addBgm(com.emu.tqqserver.proto.pkg_pmaster.Bgm.newBuilder()
                                .setId(node.path("id").asInt())
                                .setMediaId(node.path("media_id").asInt())
                                .setTitle(node.path("title").asText())
                                .setFilename(node.path("filename").asText())
                                .setHomeBgm(node.path("home_bgm").asInt(0))
                                .build());
                    }
                }

                java.util.List<Integer> funcTutorialIds = userService.getFuncTutorials(user.getUserId());
                builder.addAllFuncTutorialIds(funcTutorialIds);
                builder.setReview(0)
                                .setPresentCount(0)
                                .setNewsLatestId(0)
                                .setNewsUnreadCount(0)
                                .setLoginBonusFlag(0)
                                .setReceivedGreetingCount(0)
                                .setFriendApprovalCount(0)
                                .setFriendCanGreetingCount(0);


                java.util.List<Integer> userCards = userService.getUserCards(user.getUserId());
                for (int cardId : userCards) {
                    builder.addCard(com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                            .setUid((int) user.getUserId())
                            .setCardId(cardId)
                            .setExp(1)
                            .setLevel(1)
                            .build());
                    builder.addBook(com.emu.tqqserver.proto.pkg_puser.Book.newBuilder()
                            .setUid((int) user.getUserId())
                            .setCardId(cardId)
                            .build());
                }
                
                builder.addAllItem(userService.getItems(user.getUserId()));

                java.util.List<com.fasterxml.jackson.databind.JsonNode> groupPhotos = com.emu.tqqserver.masterdata.MasterDataLoader.getList("group_photo.json");
                if (groupPhotos != null) {
                    for (com.fasterxml.jackson.databind.JsonNode node : groupPhotos) {
                        builder.addGroupPhoto(com.emu.tqqserver.proto.pkg_pmaster.GroupPhoto.newBuilder()
                                .setId(node.path("id").asInt())
                                .setDirection(node.path("direction").asInt())
                                .setName(node.path("name").asText())
                                .setComment(node.path("comment").asText())
                                .build());
                    }
                }
                // Populate clear array with only the fields that are actually present
                StoredData temp = builder.build();
                temp.getAllFields().keySet().forEach(f -> builder.addClear(f.getName()));

                return builder.build();
        }
}
