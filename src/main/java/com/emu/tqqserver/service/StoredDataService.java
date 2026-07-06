package com.emu.tqqserver.service;

import com.emu.tqqserver.model.entity.UserEntity;
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
                User protoUser = User.newBuilder()
                                .setUid((int) user.getUserId())
                                .setNickname(user.getNickname())
                                .setLevel(user.getRank())
                                .setExp(user.getExp())
                                .setRuby(user.getJewel())
                                .setTutorial((user.getTutorialStep() == 0 && user.getRank() > 1) ? 999 : user.getTutorialStep())
                                .setIsTutorialFinish(((user.getTutorialStep() == 0 && user.getRank() > 1) || user.getTutorialStep() >= 999) ? 1 : 0)
                                .setIsClearBeginner(0)
                                .setActiveUnit(1)
                                .setFavoriteCharacters(1)
                                .setMuid("muid-" + user.getUserId())
                                .setWalletId("wallet-" + user.getUserId())
                                .setStatus("open")
                                .setLastname("上杉")
                                .setFirstname("風太郎")
                                .setLastnameKana("ウエスギ")
                                .setFirstnameKana("フータロー")
                                .setComment("ごとぱずサーバー")
                                .setHomeBackgroundId(user.getProfileBackgroundId() > 0 ? user.getProfileBackgroundId()
                                                : 10001)
                                .setProfileBackgroundId(
                                                user.getProfileBackgroundId() > 0 ? user.getProfileBackgroundId()
                                                                : 10001)
                                .setPlayerTitleId(user.getPlayerTitleId() > 0 ? user.getPlayerTitleId() : 50301003)
                                .setPlayerTitleTargetId(user.getPlayerTitleTargetId())
                                .setLeaf(user.getCoin())
                                .setAp(140)
                                .build();

                Currency currency = Currency.newBuilder()
                                .setFreeCoin(user.getJewel())
                                .setPayCoin(user.getPayJewel())
                                .setTotalCoin(user.getJewel() + user.getPayJewel())
                                .build();

                com.emu.tqqserver.proto.pkg_puser.Unit unit = com.emu.tqqserver.proto.pkg_puser.Unit.newBuilder()
                                .setUid((int) user.getUserId())
                                .setIdx(1)
                                .setUnitName("Team 1")
                                .setMemberId1(1)
                                .setMemberId2(2)
                                .setMemberId3(3)
                                .setMemberId4(4)
                                .setMemberId5(5)
                                .setCardId1(99901)
                                .setCardId2(99902)
                                .setCardId3(99903)
                                .setCardId4(99904)
                                .setCardId5(99905)
                                .build();

                com.emu.tqqserver.proto.pkg_puser.Member member1 = com.emu.tqqserver.proto.pkg_puser.Member.newBuilder()
                                .setUid((int) user.getUserId()).setMemberId(1).setDearlevel(1).build();
                com.emu.tqqserver.proto.pkg_puser.Member member2 = com.emu.tqqserver.proto.pkg_puser.Member.newBuilder()
                                .setUid((int) user.getUserId()).setMemberId(2).setDearlevel(1).build();
                com.emu.tqqserver.proto.pkg_puser.Member member3 = com.emu.tqqserver.proto.pkg_puser.Member.newBuilder()
                                .setUid((int) user.getUserId()).setMemberId(3).setDearlevel(1).build();
                com.emu.tqqserver.proto.pkg_puser.Member member4 = com.emu.tqqserver.proto.pkg_puser.Member.newBuilder()
                                .setUid((int) user.getUserId()).setMemberId(4).setDearlevel(1).build();
                com.emu.tqqserver.proto.pkg_puser.Member member5 = com.emu.tqqserver.proto.pkg_puser.Member.newBuilder()
                                .setUid((int) user.getUserId()).setMemberId(5).setDearlevel(1).build();

                com.emu.tqqserver.proto.pkg_puser.Chapter chapter = com.emu.tqqserver.proto.pkg_puser.Chapter
                                .newBuilder()
                                .setUid((int) user.getUserId())
                                .setChapterId(1)
                                .setStatus(1)
                                .build();

                com.emu.tqqserver.proto.pkg_puser.Stage stage = com.emu.tqqserver.proto.pkg_puser.Stage.newBuilder()
                                .setUid((int) user.getUserId())
                                .setStageId(1)
                                .setRank(3)
                                .build();

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
                                .setBgm(10)
                                .setSe(10)
                                .setVoice(10)
                                .setProtectCardR6(1)
                                .setProtectCardR5(1)
                                .setProtectCardFirst(1)
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
                                .addUnit(unit)
                                .addMember(member1).addMember(member2).addMember(member3).addMember(member4).addMember(member5)
                                .addChapter(chapter)
                                .addStage(stage)
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
