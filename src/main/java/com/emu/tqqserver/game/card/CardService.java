package com.emu.tqqserver.game.card;

import com.emu.tqqserver.data.GameData;
import com.emu.tqqserver.data.resources.CardDef;
import com.emu.tqqserver.game.user.CardEntity;
import com.emu.tqqserver.game.user.UserDao;
import com.emu.tqqserver.proto.pkg_pmaster.Card;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardService {
    private final UserDao userDao = new UserDao();

    public List<CardEntity> getUserCards(long userId) {
        return userDao.getUserCards(userId);
    }

    public Map<Long, CardEntity> getUserCardsMap(long userId) {
        return getUserCards(userId).stream()
                .collect(Collectors.toMap(CardEntity::getId, c -> c));
    }

    public Card mergeToMasterCard(CardEntity entity) {
        CardDef def = GameData.getCardDataTable().get(entity.getCardId());
        if (def == null) return Card.getDefaultInstance();

        return Card.newBuilder()
                .setId((int) entity.getId())
                .setMemberId(def.getMemberId())
                .setCostumeId(def.getCostumeId())
                .setClothesId(def.getClothesId())
                .setReactionSceneId(def.getReactionSceneId())
                .setNamePrefixTextId(def.getNamePrefixTextId())
                .setRarity(def.getRarity())
                .setMaxLevelId(def.getMaxLevelId())
                .setAnimeSeason(def.getAnimeSeason())
                .setFeatureId(def.getFeatureId())
                .setPictureReleaseId(def.getPictureReleaseId())
                .setLimitbreakId(def.getLimitbreakId())
                .setLimitbreakTargetIds(def.getLimitbreakTargetIds() != null ? def.getLimitbreakTargetIds() : "")
                .setLevelGrowthId(def.getLevelGrowthId())
                .setKiramekiAwakeningReceipeId(def.getKiramekiAwakeningReceipeId())
                .setTokimekiAwakeningReceipeId(def.getTokimekiAwakeningReceipeId())
                .setReawakeningReceipeId(def.getReawakeningReceipeId())
                .setIsLimitbreakMaterial(def.getIsLimitbreakMaterial())
                .setIsSpecialLimitbreakMaterial(def.getIsSpecialLimitbreakMaterial())
                .setExp(entity.getExp())
                .setInterludeVoiceNo1(def.getInterludeVoiceNo1() != null ? def.getInterludeVoiceNo1() : "")
                .build();
    }
}
