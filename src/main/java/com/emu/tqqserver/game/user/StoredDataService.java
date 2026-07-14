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

    /**
     * Compute safe exp and level for a card, ensuring the client never sees level 0.
     * The client computes level from exp using LevelGrowth data. If exp is below the
     * threshold for level 1, the client computes level=0, which causes KeyNotFoundException.
     * 
     * @return int[2] where [0] = safe exp, [1] = safe level
     */
    private static int[] computeSafeExpAndLevel(int cardId, int rawExp, int rawLevel) {
        com.emu.tqqserver.data.resources.CardDef cardDef = com.emu.tqqserver.data.GameData.getCardDataTable().get(cardId);
        if (cardDef == null) {
            // Unknown card, just ensure level >= 1
            return new int[]{ Math.max(rawExp, 1), Math.max(rawLevel, 1) };
        }

        int levelGrowthId = cardDef.getLevelGrowthId();

        // Find the exp threshold for level 1 (minimum exp to be considered level 1)
        int level1Exp = 0;
        for (com.emu.tqqserver.data.resources.LevelGrowthDef g : com.emu.tqqserver.data.GameData.getLevelGrowthDataTable().values()) {
            if (g.getId() == levelGrowthId && g.getLevel() == 1) {
                level1Exp = g.getValue();
                break;
            }
        }

        // Ensure exp is at least level1Exp + 1 (so client computes level >= 1)
        int safeExp = rawExp;
        if (safeExp <= level1Exp) {
            safeExp = level1Exp + 1;
        }

        // Compute the correct level from exp using the same algorithm the client uses
        int computedLevel = 1;
        for (com.emu.tqqserver.data.resources.LevelGrowthDef g : com.emu.tqqserver.data.GameData.getLevelGrowthDataTable().values()) {
            if (g.getId() == levelGrowthId && safeExp >= g.getValue() && g.getLevel() > computedLevel) {
                computedLevel = g.getLevel();
            }
        }

        return new int[]{ safeExp, computedLevel };
    }

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

                java.util.List<com.emu.tqqserver.proto.pkg_puser.Stage> userStages = new java.util.ArrayList<>();
                String sqlStage = "SELECT stage_id, stars, best_score, clear_count, cleared_at FROM user_stages WHERE user_id = ?";
                try (java.sql.Connection conn = com.emu.tqqserver.db.DatabaseManager.getInstance().getConnection();
                     java.sql.PreparedStatement ps = com.emu.tqqserver.db.DatabaseManager.getInstance().prepareStatement(conn, sqlStage)) {
                    ps.setLong(1, user.getUserId());
                    try (java.sql.ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            userStages.add(com.emu.tqqserver.proto.pkg_puser.Stage.newBuilder()
                                .setUid((int) user.getUserId())
                                .setStageId(rs.getInt("stage_id"))
                                .setRank(rs.getInt("stars"))
                                .setScore(rs.getInt("best_score"))
                                .setStatus(3)
                                .setUpdatedAt(rs.getInt("cleared_at"))
                                .setUnitIdx(1) // Default unit index
                                .build());
                        }
                    }
                } catch (java.sql.SQLException e) {
                    System.err.println("Failed to load user stages: " + e.getMessage());
                }

                if (userStages.isEmpty()) {
                    com.emu.tqqserver.proto.pkg_puser.Stage.Builder stageBuilder = com.emu.tqqserver.proto.pkg_puser.Stage.newBuilder()
                                    .setUid((int) user.getUserId())
                                    .setRank(3);
                                    
                    java.util.Collection<com.emu.tqqserver.data.resources.StageDef> stages = com.emu.tqqserver.data.GameData.getStageDataTable().values();
                    if (!stages.isEmpty()) {
                            stageBuilder.setStageId(stages.iterator().next().getId());
                    } else {
                            stageBuilder.setStageId(1);
                    }
                    userStages.add(stageBuilder.build());
                }

                com.emu.tqqserver.proto.pkg_pmisc.Work work = com.emu.tqqserver.proto.pkg_pmisc.Work.newBuilder()
                                .setUid((int) user.getUserId())
                                .setLastWorkDate((int) (System.currentTimeMillis() / 1000))
                                .setLastLineupUpdateDate((int) (System.currentTimeMillis() / 1000))
                                .setLastResetDate((int) (System.currentTimeMillis() / 1000))
                                .build();

                // Find active unit (Unit 1)
                com.emu.tqqserver.proto.pkg_puser.Unit activeUnit = null;
                for (com.emu.tqqserver.proto.pkg_puser.Unit u : units) {
                        if (u.getIdx() == 1) {
                                activeUnit = u;
                                break;
                        }
                }

                // Default leader card
                long mainCardId = 1823880390L;
                if (activeUnit != null && activeUnit.getCardId1() > 0) {
                        mainCardId = activeUnit.getCardId1();
                }

                com.emu.tqqserver.game.card.CardService cardService = new com.emu.tqqserver.game.card.CardService();
                com.emu.tqqserver.proto.pkg_puser.Card leaderCard;
                com.emu.tqqserver.game.user.CardEntity leaderCardEntity = cardService.getUserCardsMap(user.getUserId()).get(mainCardId);
                if (leaderCardEntity != null) {
                        int[] safeLeader = computeSafeExpAndLevel(leaderCardEntity.getCardId(), leaderCardEntity.getExp(), leaderCardEntity.getLevel());
                        int leaderPropertyId = leaderCardEntity.getCardId() * 10 + 1;
                        leaderCard = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                            .setId(leaderCardEntity.getId())
                            .setCardId(leaderCardEntity.getCardId())
                            .setCardPropertyId(leaderPropertyId)
                            .setCardPropertyId2(leaderPropertyId)
                            .setLevel(safeLeader[1])
                            .setExp(safeLeader[0])
                            .setLimitbreakRank(leaderCardEntity.getLimitbreakRank())
                            .setActiveSkillLevel(Math.max(1, leaderCardEntity.getActiveSkillLevel()))
                            .setPassiveSkillLevel1(Math.max(1, leaderCardEntity.getActiveSkillLevel()))
                            .setPassiveSkillLevel2(Math.max(1, leaderCardEntity.getActiveSkillLevel()))
                            .setPassiveSkillLevel3(Math.max(1, leaderCardEntity.getActiveSkillLevel()))
                            .build();
                } else {
                        leaderCard = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                            .setId(mainCardId)
                            .setCardId(10651)
                            .setCardPropertyId(106511)
                            .setCardPropertyId2(106511)
                            .setLevel(50)
                            .setActiveSkillLevel(5)
                            .setPassiveSkillLevel1(5)
                            .setPassiveSkillLevel2(5)
                            .setPassiveSkillLevel3(5)
                            .build();
                }

                com.emu.tqqserver.proto.pkg_pmisc.FeatureTeamMember featureTeamMember = com.emu.tqqserver.proto.pkg_pmisc.FeatureTeamMember.newBuilder()
                        .setUid((int) user.getUserId())
                        .setFeatureId(55)
                        .setTeamId(1)
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

                java.util.List<com.emu.tqqserver.proto.pkg_pmisc.MemberDearpoint> dearpoints = new java.util.ArrayList<>();
                java.util.List<com.emu.tqqserver.proto.pkg_pmisc.MemberLikabilitypoint> likabilitypoints = new java.util.ArrayList<>();
                for (com.emu.tqqserver.proto.pkg_puser.Member m : members) {
                    dearpoints.add(com.emu.tqqserver.proto.pkg_pmisc.MemberDearpoint.newBuilder()
                        .setUid(m.getUid())
                        .setMemberId(m.getMemberId())
                        .setDearpoint(m.getDearpoint())
                        .build());
                    likabilitypoints.add(com.emu.tqqserver.proto.pkg_pmisc.MemberLikabilitypoint.newBuilder()
                        .setUid(m.getUid())
                        .setMemberId(m.getMemberId())
                        .setLikabilitypoint(m.getDearpoint()) // Use dearpoint or some dummy value
                        .build());
                }

                StoredData.Builder builder = StoredData.newBuilder()
                                .addClear("chapter_expire")
                                .setUser(protoUser)
                                .addAllHomeActor(homeActors)
                                .setCurrency(currency)
                                .addAllUnit(units)
                                .addAllMember(members)
                                .addAllMemberDearpoint(dearpoints)
                                .addAllMemberLikabilitypoint(likabilitypoints)
                                .addAllStage(userStages)
                                .setWork(work)
                                .setMileage(mileage)
                                .setFeatureTeamMember(featureTeamMember)
                                .putFeatureTeamCreate(55, 1)
                                .setOptions(options);

                java.util.List<com.emu.tqqserver.game.user.CardEntity> cards = cardService.getUserCards(user.getUserId());
                for (com.emu.tqqserver.game.user.CardEntity c : cards) {
                    int propertyId = c.getCardId() * 10 + 1;
                    builder.addCard(com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                        .setId(c.getId())
                        .setUid((int) user.getUserId())
                        .setCardId(c.getCardId())
                        .setCardPropertyId(propertyId)
                        .setCardPropertyId2(propertyId)
                        .setExp(c.getExp())
                        .setLevel(c.getLevel())
                        .setLevelAwake(0)
                        .setActiveSkillLevel(Math.max(1, c.getActiveSkillLevel()))
                        .setPassiveSkillLevel1(Math.max(1, c.getActiveSkillLevel()))
                        .setPassiveSkillLevel2(Math.max(1, c.getActiveSkillLevel()))
                        .setPassiveSkillLevel3(Math.max(1, c.getActiveSkillLevel()))
                        .setLimitbreakRank(c.getLimitbreakRank())
                        .setAwakePriority(0)
                        .build());
                }
                                
                UserService userService = new UserService();
                java.util.List<Integer> chapterIds = userService.getUnlockedChapters(user.getUserId());
                if (chapterIds.isEmpty()) {
                    builder.addChapter(com.emu.tqqserver.proto.pkg_puser.Chapter.newBuilder()
                            .setUid((int) user.getUserId())
                            .setStatus(1)
                            .setChapterId(1)
                            .build());
                } else {
                    for (int chId : chapterIds) {
                        builder.addChapter(com.emu.tqqserver.proto.pkg_puser.Chapter.newBuilder()
                                .setUid((int) user.getUserId())
                                .setStatus(1)
                                .setChapterId(chId)
                                .build());
                    }
                }
                
                java.util.Map<Integer, Long> chapterExpires = userService.getChapterExpires(user.getUserId());
                for (java.util.Map.Entry<Integer, Long> entry : chapterExpires.entrySet()) {
                    builder.addChapterExpire(com.emu.tqqserver.proto.pkg_puser.ChapterExpire.newBuilder()
                            .setUid((int) user.getUserId())
                            .setChapterGroupId(entry.getKey())
                            .setExpiresAt(entry.getValue().intValue())
                            .build());
                }
                
                builder.addAllItem(userService.getItems(user.getUserId()));
                
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
                java.util.Collection<com.emu.tqqserver.data.resources.HomeMemberClothesDef> masterClothes = com.emu.tqqserver.data.GameData.getHomeMemberClothesDataTable().values();
                for (com.emu.tqqserver.data.resources.HomeMemberClothesDef def : masterClothes) {
                    if (clothesIds.contains(def.getId())) {
                        builder.addHomeMemberClothes(com.emu.tqqserver.proto.pkg_pmaster.HomeMemberClothes.newBuilder()
                                .setId(def.getId())
                                .setMemberId(def.getMemberId())
                                .setCardId(def.getCardId())
                                .setName(def.getName())
                                .setType(def.getType())
                                .setThumbnailResourceId(def.getThumbnailResourceId())
                                .setIsDefault(def.getIsDefault())
                                .build());
                    }
                }

                // Load all BGMs
                java.util.Collection<com.emu.tqqserver.data.resources.BgmDef> bgms = com.emu.tqqserver.data.GameData.getBgmDataTable().values();
                for (com.emu.tqqserver.data.resources.BgmDef def : bgms) {
                    builder.addBgm(com.emu.tqqserver.proto.pkg_pmaster.Bgm.newBuilder()
                            .setId(def.getId())
                            .setMediaId(def.getMediaId())
                            .setTitle(def.getTitle())
                            .setFilename(def.getFilename())
                            .setHomeBgm(0)
                            .build());
                }

                java.util.List<Integer> funcTutorialIds = userService.getFuncTutorials(user.getUserId());
                builder.addAllFuncTutorialIds(funcTutorialIds);
                
                com.emu.tqqserver.game.gacha.GachaDao gachaDao = new com.emu.tqqserver.game.gacha.GachaDao();
                builder.addAllGachaHistory(gachaDao.getAllHistory(user.getUserId()));
                
                builder.setReview(0)
                                .setPresentCount(0)
                                .setNewsLatestId(0)
                                .setNewsUnreadCount(0)
                                .setLoginBonusFlag(0)
                                .setReceivedGreetingCount(0)
                                .setFriendApprovalCount(0)
                                .setFriendCanGreetingCount(0);


                java.util.List<com.emu.tqqserver.game.user.CardEntity> userCards = userService.getUserCards(user.getUserId());
                for (com.emu.tqqserver.game.user.CardEntity card : userCards) {
                    int propertyId = card.getCardId() * 10 + 1;
                    int[] safe = computeSafeExpAndLevel(card.getCardId(), card.getExp(), card.getLevel());
                    builder.addCard(com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                            .setId(card.getId())
                            .setUid((int) user.getUserId())
                            .setCardId(card.getCardId())
                            .setCardPropertyId(propertyId)
                            .setCardPropertyId2(propertyId)
                            .setExp(safe[0])
                            .setLevel(safe[1])
                            .setInterludeVoice1(1)
                            .setInterludeVoice2(1)
                            .setInterludeVoice3(1)
                            .setInterludeVoice4(1)
                            .setInterludeVoice5(1)
                            .setLevelAwake(0)
                            .setActiveSkillLevel(Math.max(1, card.getActiveSkillLevel()))
                            .setPassiveSkillLevel1(Math.max(1, card.getActiveSkillLevel()))
                            .setPassiveSkillLevel2(Math.max(1, card.getActiveSkillLevel()))
                            .setPassiveSkillLevel3(Math.max(1, card.getActiveSkillLevel()))
                            .setLimitbreakRank(card.getLimitbreakRank())
                            .setAwakePriority(0)
                            .build());
                    builder.addBook(com.emu.tqqserver.proto.pkg_puser.Book.newBuilder()
                            .setId(card.getId())
                            .setUid((int) user.getUserId())
                            .setCardId(card.getCardId())
                            .build());
                }
                java.util.Collection<com.emu.tqqserver.data.resources.GroupPhotoDef> groupPhotos = com.emu.tqqserver.data.GameData.getGroupPhotoDataTable().values();
                for (com.emu.tqqserver.data.resources.GroupPhotoDef def : groupPhotos) {
                    builder.addGroupPhoto(com.emu.tqqserver.proto.pkg_pmaster.GroupPhoto.newBuilder()
                            .setId(def.getId())
                            .setDirection(def.getDirection())
                            .setName(def.getName())
                            .setComment(def.getComment())
                            .build());
                }
                // Add active puzzle session state if present
                com.emu.tqqserver.game.puzzle.PuzzleService.PuzzleSession activeSession = new com.emu.tqqserver.game.puzzle.PuzzleService().getActiveSession(user.getUserId());
                if (activeSession != null) {
                    builder.setPuzzle(com.emu.tqqserver.proto.pkg_puser.Puzzle.newBuilder()
                        .setPuid(activeSession.puid)
                        .setStageId(activeSession.stageId)
                        .setStatus(1)
                        .build());
                }

                // Populate clear array with only the fields that are actually present
                StoredData temp = builder.build();
                temp.getAllFields().keySet().forEach(f -> builder.addClear(f.getName()));

                return builder.build();
        }
}
