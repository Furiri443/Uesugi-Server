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
                if (units.isEmpty()) {
                        com.emu.tqqserver.game.user.UserService us = new com.emu.tqqserver.game.user.UserService();
                        us.ensureDefaultUnit(user.getUserId());
                        units = unitDao.getUnits(user.getUserId());
                }

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

                com.emu.tqqserver.proto.pkg_puser.HomeActor homeActor1 = com.emu.tqqserver.proto.pkg_puser.HomeActor.newBuilder()
                                .setUid((int) user.getUserId()).setCharacterId(1).setModelKindId(1).setClothesId(1001).setFacialPresetId(0).setPosition(0).build();
                com.emu.tqqserver.proto.pkg_puser.HomeActor homeActor2 = com.emu.tqqserver.proto.pkg_puser.HomeActor.newBuilder()
                                .setUid((int) user.getUserId()).setCharacterId(2).setModelKindId(1).setClothesId(1002).setFacialPresetId(0).setPosition(1).build();
                com.emu.tqqserver.proto.pkg_puser.HomeActor homeActor3 = com.emu.tqqserver.proto.pkg_puser.HomeActor.newBuilder()
                                .setUid((int) user.getUserId()).setCharacterId(3).setModelKindId(1).setClothesId(1003).setFacialPresetId(0).setPosition(2).build();
                com.emu.tqqserver.proto.pkg_puser.HomeActor homeActor4 = com.emu.tqqserver.proto.pkg_puser.HomeActor.newBuilder()
                                .setUid((int) user.getUserId()).setCharacterId(4).setModelKindId(1).setClothesId(1004).setFacialPresetId(0).setPosition(3).build();
                com.emu.tqqserver.proto.pkg_puser.HomeActor homeActor5 = com.emu.tqqserver.proto.pkg_puser.HomeActor.newBuilder()
                                .setUid((int) user.getUserId()).setCharacterId(5).setModelKindId(1).setClothesId(1005).setFacialPresetId(0).setPosition(4).build();

                StoredData.Builder builder = StoredData.newBuilder()
                                .setUser(protoUser)
                                .setCurrency(currency)
                                .addAllUnit(units)
                                .addAllMember(members)
                                .addChapter(chapterBuilder.build())
                                .addStage(stageBuilder.build())
                                .setWork(work)
                                .setMileage(mileage)
                                .setOptions(options)
                                .addHomeBackground(homeBg)
                                                                .setReview(0)
                                .setPresentCount(0)
                                .setNewsLatestId(0)
                                .setNewsUnreadCount(0)
                                .setLoginBonusFlag(0)
                                .setReceivedGreetingCount(0)
                                .addAllHomeActor(homeService.getHomeActors(user.getUserId()))
                                .setFriendApprovalCount(0)
                                .setFriendCanGreetingCount(0);

                UserService userService = new UserService();
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

                // Populate clear array with only the fields that are actually present
                StoredData temp = builder.build();
                temp.getAllFields().keySet().forEach(f -> builder.addClear(f.getName()));

                return builder.build();
        }
}
