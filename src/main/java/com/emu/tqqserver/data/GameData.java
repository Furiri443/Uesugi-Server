package com.emu.tqqserver.data;

import com.emu.tqqserver.data.resources.*;

public class GameData {

    private static DataTable<ActiveSkillDef> activeSkillDataTable = new DataTable<>();
    private static DataTable<ActiveSkillIconDef> activeSkillIconDataTable = new DataTable<>();
    private static DataTable<AdvertisingDef> advertisingDataTable = new DataTable<>();
    private static DataTable<ApDef> apDataTable = new DataTable<>();
    private static DataTable<AppointmentDef> appointmentDataTable = new DataTable<>();
    private static DataTable<AppointmentMessageDef> appointmentMessageDataTable = new DataTable<>();
    private static DataTable<AppointmentSurpriseDef> appointmentSurpriseDataTable = new DataTable<>();
    private static DataTable<AppointmentSurpriseReactionDef> appointmentSurpriseReactionDataTable = new DataTable<>();
    private static DataTable<AppIconDef> appIconDataTable = new DataTable<>();
    private static DataTable<AwakeningReceipeDef> awakeningReceipeDataTable = new DataTable<>();
    private static DataTable<BannerDef> bannerDataTable = new DataTable<>();
    private static DataTable<BgmDef> bgmDataTable = new DataTable<>();
    private static DataTable<BirthFestivalDef> birthFestivalDataTable = new DataTable<>();
    private static DataTable<BirthPlayerDef> birthPlayerDataTable = new DataTable<>();
    private static DataTable<BoostDef> boostDataTable = new DataTable<>();
    private static DataTable<BoostYellDef> boostYellDataTable = new DataTable<>();
    private static DataTable<CampaignDef> campaignDataTable = new DataTable<>();
    private static DataTable<CardDef> cardDataTable = new DataTable<>();
    private static DataTable<CardChibicharaDef> cardChibicharaDataTable = new DataTable<>();
    private static DataTable<CardDirectionDef> cardDirectionDataTable = new DataTable<>();
    private static DataTable<CardGrowthRewardDef> cardGrowthRewardDataTable = new DataTable<>();
    private static DataTable<CardLimitbreakDef> cardLimitbreakDataTable = new DataTable<>();
    private static DataTable<CardMaxLevelDef> cardMaxLevelDataTable = new DataTable<>();
    private static DataTable<CardPictureReleaseDef> cardPictureReleaseDataTable = new DataTable<>();
    private static DataTable<CardPremiumDef> cardPremiumDataTable = new DataTable<>();
    private static DataTable<CardPropertyDef> cardPropertyDataTable = new DataTable<>();
    private static DataTable<CardRarityDef> cardRarityDataTable = new DataTable<>();
    private static DataTable<CardScoreDef> cardScoreDataTable = new DataTable<>();
    private static DataTable<CardVoiceMapDef> cardVoiceMapDataTable = new DataTable<>();
    private static DataTable<ChallengeDef> challengeDataTable = new DataTable<>();
    private static DataTable<ChallengeGroupDef> challengeGroupDataTable = new DataTable<>();
    private static DataTable<ChallengeSectionDef> challengeSectionDataTable = new DataTable<>();
    private static DataTable<ChallengeTitleDef> challengeTitleDataTable = new DataTable<>();
    private static DataTable<ChapterDef> chapterDataTable = new DataTable<>();
    private static DataTable<ChapterTermDef> chapterTermDataTable = new DataTable<>();
    private static DataTable<CollectionBgmDef> collectionBgmDataTable = new DataTable<>();
    private static DataTable<CollectionLayoutDef> collectionLayoutDataTable = new DataTable<>();
    private static DataTable<CollectionLayoutDetailDef> collectionLayoutDetailDataTable = new DataTable<>();
    private static DataTable<CollectionRewardDef> collectionRewardDataTable = new DataTable<>();
    private static DataTable<CollectionThemeDef> collectionThemeDataTable = new DataTable<>();
    private static DataTable<CollectionTransitionDef> collectionTransitionDataTable = new DataTable<>();
    private static DataTable<ConfessionStepDef> confessionStepDataTable = new DataTable<>();
    private static DataTable<ConfessionStoryDef> confessionStoryDataTable = new DataTable<>();
    private static DataTable<ConstantDef> constantDataTable = new DataTable<>();
    private static DataTable<CookingProficiencyLevelDef> cookingProficiencyLevelDataTable = new DataTable<>();
    private static DataTable<CookingRankAchievementRewardDef> cookingRankAchievementRewardDataTable = new DataTable<>();
    private static DataTable<CookingRankProbabilityDef> cookingRankProbabilityDataTable = new DataTable<>();
    private static DataTable<CookingRankRewardDef> cookingRankRewardDataTable = new DataTable<>();
    private static DataTable<CookingRecipeDef> cookingRecipeDataTable = new DataTable<>();
    private static DataTable<CookingRecipeMaterialDef> cookingRecipeMaterialDataTable = new DataTable<>();
    private static DataTable<CookingSecretIngredientDef> cookingSecretIngredientDataTable = new DataTable<>();
    private static DataTable<CookingShortenPriceDef> cookingShortenPriceDataTable = new DataTable<>();
    private static DataTable<CookingTrayDef> cookingTrayDataTable = new DataTable<>();
    private static DataTable<CookingVisitingMemberClothDef> cookingVisitingMemberClothDataTable = new DataTable<>();
    private static DataTable<CookingVisitingMemberProbabilityDef> cookingVisitingMemberProbabilityDataTable = new DataTable<>();
    private static DataTable<CostumeDef> costumeDataTable = new DataTable<>();
    private static DataTable<CountGachaRewardDef> countGachaRewardDataTable = new DataTable<>();
    private static DataTable<EncoreDef> encoreDataTable = new DataTable<>();
    private static DataTable<EncoreFeverDef> encoreFeverDataTable = new DataTable<>();
    private static DataTable<EncoreStageDef> encoreStageDataTable = new DataTable<>();
    private static DataTable<EpFeverDef> epFeverDataTable = new DataTable<>();
    private static DataTable<EpFeverRatingDef> epFeverRatingDataTable = new DataTable<>();
    private static DataTable<FeatureDef> featureDataTable = new DataTable<>();
    private static DataTable<FeatureAppointmentDef> featureAppointmentDataTable = new DataTable<>();
    private static DataTable<FeatureAppointmentMessageDef> featureAppointmentMessageDataTable = new DataTable<>();
    private static DataTable<FeatureCouponDef> featureCouponDataTable = new DataTable<>();
    private static DataTable<FeatureEpRewardDef> featureEpRewardDataTable = new DataTable<>();
    private static DataTable<FeatureHowtoDef> featureHowtoDataTable = new DataTable<>();
    private static DataTable<FeatureItemDef> featureItemDataTable = new DataTable<>();
    private static DataTable<FeatureNakayoshiLevelDef> featureNakayoshiLevelDataTable = new DataTable<>();
    private static DataTable<FeatureNakayoshiLevelConditionDef> featureNakayoshiLevelConditionDataTable = new DataTable<>();
    private static DataTable<FeatureNakayoshiMemberSeqDef> featureNakayoshiMemberSeqDataTable = new DataTable<>();
    private static DataTable<FeatureNakayoshiTotalLevelRewardDef> featureNakayoshiTotalLevelRewardDataTable = new DataTable<>();
    private static DataTable<FeaturePeriodDef> featurePeriodDataTable = new DataTable<>();
    private static DataTable<FeaturePeriodMemberDef> featurePeriodMemberDataTable = new DataTable<>();
    private static DataTable<FeatureRankingRewardDef> featureRankingRewardDataTable = new DataTable<>();
    private static DataTable<FeatureSelectionDef> featureSelectionDataTable = new DataTable<>();
    private static DataTable<FeatureTalkDef> featureTalkDataTable = new DataTable<>();
    private static DataTable<FeatureTeamDef> featureTeamDataTable = new DataTable<>();
    private static DataTable<FeatureTermsDef> featureTermsDataTable = new DataTable<>();
    private static DataTable<FeatureTutorialDef> featureTutorialDataTable = new DataTable<>();
    private static DataTable<FeedDef> feedDataTable = new DataTable<>();
    private static DataTable<FuncIntroductionDef> funcIntroductionDataTable = new DataTable<>();
    private static DataTable<FuncNotifyDef> funcNotifyDataTable = new DataTable<>();
    private static DataTable<FuncTutorialDef> funcTutorialDataTable = new DataTable<>();
    private static DataTable<GachaDef> gachaDataTable = new DataTable<>();
    private static DataTable<GachaBonusChoiceDef> gachaBonusChoiceDataTable = new DataTable<>();
    private static DataTable<GachaBoxDef> gachaBoxDataTable = new DataTable<>();
    private static DataTable<GachaCardPremiumDef> gachaCardPremiumDataTable = new DataTable<>();
    private static DataTable<GachaChallengeDef> gachaChallengeDataTable = new DataTable<>();
    private static DataTable<GachaDiscountDef> gachaDiscountDataTable = new DataTable<>();
    private static DataTable<GachaGroupWeightDef> gachaGroupWeightDataTable = new DataTable<>();
    private static DataTable<GachaLineupDef> gachaLineupDataTable = new DataTable<>();
    private static DataTable<GachaLineupHomeMemberPictureDef> gachaLineupHomeMemberPictureDataTable = new DataTable<>();
    private static DataTable<GachaOptionDef> gachaOptionDataTable = new DataTable<>();
    private static DataTable<GachaPickupDef> gachaPickupDataTable = new DataTable<>();
    private static DataTable<GroupPhotoDef> groupPhotoDataTable = new DataTable<>();
    private static DataTable<HelpDef> helpDataTable = new DataTable<>();
    private static DataTable<HomeBackgroundDef> homeBackgroundDataTable = new DataTable<>();
    private static DataTable<HomeMemberClothesDef> homeMemberClothesDataTable = new DataTable<>();
    private static DataTable<HomeMemberPictureDef> homeMemberPictureDataTable = new DataTable<>();
    private static DataTable<HomeMemberTapMotionDef> homeMemberTapMotionDataTable = new DataTable<>();
    private static DataTable<HomeMessageDef> homeMessageDataTable = new DataTable<>();
    private static DataTable<InvitationDef> invitationDataTable = new DataTable<>();
    private static DataTable<InvitationRewardDef> invitationRewardDataTable = new DataTable<>();
    private static DataTable<ItemDef> itemDataTable = new DataTable<>();
    private static DataTable<ItemVipDef> itemVipDataTable = new DataTable<>();
    private static DataTable<LevelGrowthDef> levelGrowthDataTable = new DataTable<>();
    private static DataTable<LoginBonusDef> loginBonusDataTable = new DataTable<>();
    private static DataTable<LoginBonusSeqDef> loginBonusSeqDataTable = new DataTable<>();
    private static DataTable<LotteryDef> lotteryDataTable = new DataTable<>();
    private static DataTable<LotteryRewardDef> lotteryRewardDataTable = new DataTable<>();
    private static DataTable<LotterySelectionDef> lotterySelectionDataTable = new DataTable<>();
    private static DataTable<MemberDef> memberDataTable = new DataTable<>();
    private static DataTable<MembershipContinueRewardDef> membershipContinueRewardDataTable = new DataTable<>();
    private static DataTable<MemberDearlevelDef> memberDearlevelDataTable = new DataTable<>();
    private static DataTable<MemberDeartoolReactionDef> memberDeartoolReactionDataTable = new DataTable<>();
    private static DataTable<MemberGroupDef> memberGroupDataTable = new DataTable<>();
    private static DataTable<MemberLikabilitylevelDef> memberLikabilitylevelDataTable = new DataTable<>();
    private static DataTable<MemberLikabilitytextDef> memberLikabilitytextDataTable = new DataTable<>();
    private static DataTable<MemberMessageDef> memberMessageDataTable = new DataTable<>();
    private static DataTable<MemorialPhotoDef> memorialPhotoDataTable = new DataTable<>();
    private static DataTable<MileageDef> mileageDataTable = new DataTable<>();
    private static DataTable<MiniGameDef> miniGameDataTable = new DataTable<>();
    private static DataTable<OfferWallDef> offerWallDataTable = new DataTable<>();
    private static DataTable<OpeningShowDef> openingShowDataTable = new DataTable<>();
    private static DataTable<PassiveSkillDef> passiveSkillDataTable = new DataTable<>();
    private static DataTable<PhotoBoothDef> photoBoothDataTable = new DataTable<>();
    private static DataTable<PhotoStampDef> photoStampDataTable = new DataTable<>();
    private static DataTable<PlayerDef> playerDataTable = new DataTable<>();
    private static DataTable<PlayerTitleDef> playerTitleDataTable = new DataTable<>();
    private static DataTable<PlayerTitleBannerDef> playerTitleBannerDataTable = new DataTable<>();
    private static DataTable<PlayerTitleCategoryDef> playerTitleCategoryDataTable = new DataTable<>();
    private static DataTable<PlayerTitleSpecialDef> playerTitleSpecialDataTable = new DataTable<>();
    private static DataTable<PoolDef> poolDataTable = new DataTable<>();
    private static DataTable<PopRatioDef> popRatioDataTable = new DataTable<>();
    private static DataTable<PracticeExamDef> practiceExamDataTable = new DataTable<>();
    private static DataTable<PracticeExamEvaluationCommentDef> practiceExamEvaluationCommentDataTable = new DataTable<>();
    private static DataTable<PracticeExamPuzzleRuleDef> practiceExamPuzzleRuleDataTable = new DataTable<>();
    private static DataTable<PracticeExamPuzzleRuleStageDef> practiceExamPuzzleRuleStageDataTable = new DataTable<>();
    private static DataTable<PracticeExamStageDef> practiceExamStageDataTable = new DataTable<>();
    private static DataTable<PracticeExamStageDropRewardDef> practiceExamStageDropRewardDataTable = new DataTable<>();
    private static DataTable<PracticeExamStageRankingRewardDef> practiceExamStageRankingRewardDataTable = new DataTable<>();
    private static DataTable<PromotionDef> promotionDataTable = new DataTable<>();
    private static DataTable<QuestDef> questDataTable = new DataTable<>();
    private static DataTable<QuintupletGameDef> quintupletGameDataTable = new DataTable<>();
    private static DataTable<ReactionClothesDef> reactionClothesDataTable = new DataTable<>();
    private static DataTable<ReactionSceneDef> reactionSceneDataTable = new DataTable<>();
    private static DataTable<RelationshipDef> relationshipDataTable = new DataTable<>();
    private static DataTable<RetryableChapterDef> retryableChapterDataTable = new DataTable<>();
    private static DataTable<RetryableQuestDef> retryableQuestDataTable = new DataTable<>();
    private static DataTable<RetryableStageDef> retryableStageDataTable = new DataTable<>();
    private static DataTable<RetryableStageGroupDef> retryableStageGroupDataTable = new DataTable<>();
    private static DataTable<RewardDef> rewardDataTable = new DataTable<>();
    private static DataTable<RewardCardPropertyDef> rewardCardPropertyDataTable = new DataTable<>();
    private static DataTable<RoundDef> roundDataTable = new DataTable<>();
    private static DataTable<ScenarioChoicesDef> scenarioChoicesDataTable = new DataTable<>();
    private static DataTable<SeasonDef> seasonDataTable = new DataTable<>();
    private static DataTable<SeasonRankingRewardDef> seasonRankingRewardDataTable = new DataTable<>();
    private static DataTable<ShopDef> shopDataTable = new DataTable<>();
    private static DataTable<ShopChallengeDef> shopChallengeDataTable = new DataTable<>();
    private static DataTable<ShopDearpointDef> shopDearpointDataTable = new DataTable<>();
    private static DataTable<ShopExchangeDef> shopExchangeDataTable = new DataTable<>();
    private static DataTable<ShopExchangeGroupDef> shopExchangeGroupDataTable = new DataTable<>();
    private static DataTable<ShopSharingDef> shopSharingDataTable = new DataTable<>();
    private static DataTable<SituationDef> situationDataTable = new DataTable<>();
    private static DataTable<SpecialContentDef> specialContentDataTable = new DataTable<>();
    private static DataTable<StageDef> stageDataTable = new DataTable<>();
    private static DataTable<StageBoostCampaignDef> stageBoostCampaignDataTable = new DataTable<>();
    private static DataTable<StageDropReplacementDef> stageDropReplacementDataTable = new DataTable<>();
    private static DataTable<StageExDef> stageExDataTable = new DataTable<>();
    private static DataTable<StageExConditionsDef> stageExConditionsDataTable = new DataTable<>();
    private static DataTable<StagePlayConditionDef> stagePlayConditionDataTable = new DataTable<>();
    private static DataTable<StageUnlockReceipeDef> stageUnlockReceipeDataTable = new DataTable<>();
    private static DataTable<StillPictureDef> stillPictureDataTable = new DataTable<>();
    private static DataTable<StoryDef> storyDataTable = new DataTable<>();
    private static DataTable<StoryGroupDef> storyGroupDataTable = new DataTable<>();
    private static DataTable<StoryPlayingConditionDef> storyPlayingConditionDataTable = new DataTable<>();
    private static DataTable<SusumeRaihaConfigDef> susumeRaihaConfigDataTable = new DataTable<>();
    private static DataTable<SynthesisRecipeDef> synthesisRecipeDataTable = new DataTable<>();
    private static DataTable<SynthesisRecipeMaterialDef> synthesisRecipeMaterialDataTable = new DataTable<>();
    private static DataTable<TeamBattleBoostDef> teamBattleBoostDataTable = new DataTable<>();
    private static DataTable<TeamBattleDifficultyDef> teamBattleDifficultyDataTable = new DataTable<>();
    private static DataTable<TeamBattleEpRewardDef> teamBattleEpRewardDataTable = new DataTable<>();
    private static DataTable<TeamBattleEventDef> teamBattleEventDataTable = new DataTable<>();
    private static DataTable<TeamBattleHowtoDef> teamBattleHowtoDataTable = new DataTable<>();
    private static DataTable<TeamBattleMemberRankingRewardDef> teamBattleMemberRankingRewardDataTable = new DataTable<>();
    private static DataTable<TeamBattleSelectionDef> teamBattleSelectionDataTable = new DataTable<>();
    private static DataTable<TeamBattleStageDef> teamBattleStageDataTable = new DataTable<>();
    private static DataTable<TeamBattleTeamDef> teamBattleTeamDataTable = new DataTable<>();
    private static DataTable<TeamBattleTeamContributionRewardDef> teamBattleTeamContributionRewardDataTable = new DataTable<>();
    private static DataTable<TeamBattleTermsDef> teamBattleTermsDataTable = new DataTable<>();
    private static DataTable<TeamBattleTutorialDef> teamBattleTutorialDataTable = new DataTable<>();
    private static DataTable<TeamBattleWinnerRewardDef> teamBattleWinnerRewardDataTable = new DataTable<>();
    private static DataTable<TutorialDef> tutorialDataTable = new DataTable<>();
    private static DataTable<UnitSkillDef> unitSkillDataTable = new DataTable<>();
    private static DataTable<UnitSkillPassiveSkillPoolDef> unitSkillPassiveSkillPoolDataTable = new DataTable<>();
    private static DataTable<UnitSkillRarityDef> unitSkillRarityDataTable = new DataTable<>();
    private static DataTable<VersionDef> versionDataTable = new DataTable<>();
    private static DataTable<VipContinueRewardDef> vipContinueRewardDataTable = new DataTable<>();
    private static DataTable<VrChapterDef> vrChapterDataTable = new DataTable<>();
    private static DataTable<VrContentDef> vrContentDataTable = new DataTable<>();
    private static DataTable<WorkDef> workDataTable = new DataTable<>();

    public static DataTable<ActiveSkillDef> getActiveSkillDataTable() {
        return activeSkillDataTable;
    }

    public static DataTable<ActiveSkillIconDef> getActiveSkillIconDataTable() {
        return activeSkillIconDataTable;
    }

    public static DataTable<AdvertisingDef> getAdvertisingDataTable() {
        return advertisingDataTable;
    }

    public static DataTable<ApDef> getApDataTable() {
        return apDataTable;
    }

    public static DataTable<AppointmentDef> getAppointmentDataTable() {
        return appointmentDataTable;
    }

    public static DataTable<AppointmentMessageDef> getAppointmentMessageDataTable() {
        return appointmentMessageDataTable;
    }

    public static DataTable<AppointmentSurpriseDef> getAppointmentSurpriseDataTable() {
        return appointmentSurpriseDataTable;
    }

    public static DataTable<AppointmentSurpriseReactionDef> getAppointmentSurpriseReactionDataTable() {
        return appointmentSurpriseReactionDataTable;
    }

    public static DataTable<AppIconDef> getAppIconDataTable() {
        return appIconDataTable;
    }

    public static DataTable<AwakeningReceipeDef> getAwakeningReceipeDataTable() {
        return awakeningReceipeDataTable;
    }

    public static DataTable<BannerDef> getBannerDataTable() {
        return bannerDataTable;
    }

    public static DataTable<BgmDef> getBgmDataTable() {
        return bgmDataTable;
    }

    public static DataTable<BirthFestivalDef> getBirthFestivalDataTable() {
        return birthFestivalDataTable;
    }

    public static DataTable<BirthPlayerDef> getBirthPlayerDataTable() {
        return birthPlayerDataTable;
    }

    public static DataTable<BoostDef> getBoostDataTable() {
        return boostDataTable;
    }

    public static DataTable<BoostYellDef> getBoostYellDataTable() {
        return boostYellDataTable;
    }

    public static DataTable<CampaignDef> getCampaignDataTable() {
        return campaignDataTable;
    }

    public static DataTable<CardDef> getCardDataTable() {
        return cardDataTable;
    }

    public static DataTable<CardChibicharaDef> getCardChibicharaDataTable() {
        return cardChibicharaDataTable;
    }

    public static DataTable<CardDirectionDef> getCardDirectionDataTable() {
        return cardDirectionDataTable;
    }

    public static DataTable<CardGrowthRewardDef> getCardGrowthRewardDataTable() {
        return cardGrowthRewardDataTable;
    }

    public static DataTable<CardLimitbreakDef> getCardLimitbreakDataTable() {
        return cardLimitbreakDataTable;
    }

    public static DataTable<CardMaxLevelDef> getCardMaxLevelDataTable() {
        return cardMaxLevelDataTable;
    }

    public static DataTable<CardPictureReleaseDef> getCardPictureReleaseDataTable() {
        return cardPictureReleaseDataTable;
    }

    public static DataTable<CardPremiumDef> getCardPremiumDataTable() {
        return cardPremiumDataTable;
    }

    public static DataTable<CardPropertyDef> getCardPropertyDataTable() {
        return cardPropertyDataTable;
    }

    public static DataTable<CardRarityDef> getCardRarityDataTable() {
        return cardRarityDataTable;
    }

    public static DataTable<CardScoreDef> getCardScoreDataTable() {
        return cardScoreDataTable;
    }

    public static DataTable<CardVoiceMapDef> getCardVoiceMapDataTable() {
        return cardVoiceMapDataTable;
    }

    public static DataTable<ChallengeDef> getChallengeDataTable() {
        return challengeDataTable;
    }

    public static DataTable<ChallengeGroupDef> getChallengeGroupDataTable() {
        return challengeGroupDataTable;
    }

    public static DataTable<ChallengeSectionDef> getChallengeSectionDataTable() {
        return challengeSectionDataTable;
    }

    public static DataTable<ChallengeTitleDef> getChallengeTitleDataTable() {
        return challengeTitleDataTable;
    }

    public static DataTable<ChapterDef> getChapterDataTable() {
        return chapterDataTable;
    }

    public static DataTable<ChapterTermDef> getChapterTermDataTable() {
        return chapterTermDataTable;
    }

    public static DataTable<CollectionBgmDef> getCollectionBgmDataTable() {
        return collectionBgmDataTable;
    }

    public static DataTable<CollectionLayoutDef> getCollectionLayoutDataTable() {
        return collectionLayoutDataTable;
    }

    public static DataTable<CollectionLayoutDetailDef> getCollectionLayoutDetailDataTable() {
        return collectionLayoutDetailDataTable;
    }

    public static DataTable<CollectionRewardDef> getCollectionRewardDataTable() {
        return collectionRewardDataTable;
    }

    public static DataTable<CollectionThemeDef> getCollectionThemeDataTable() {
        return collectionThemeDataTable;
    }

    public static DataTable<CollectionTransitionDef> getCollectionTransitionDataTable() {
        return collectionTransitionDataTable;
    }

    public static DataTable<ConfessionStepDef> getConfessionStepDataTable() {
        return confessionStepDataTable;
    }

    public static DataTable<ConfessionStoryDef> getConfessionStoryDataTable() {
        return confessionStoryDataTable;
    }

    public static DataTable<ConstantDef> getConstantDataTable() {
        return constantDataTable;
    }

    public static DataTable<CookingProficiencyLevelDef> getCookingProficiencyLevelDataTable() {
        return cookingProficiencyLevelDataTable;
    }

    public static DataTable<CookingRankAchievementRewardDef> getCookingRankAchievementRewardDataTable() {
        return cookingRankAchievementRewardDataTable;
    }

    public static DataTable<CookingRankProbabilityDef> getCookingRankProbabilityDataTable() {
        return cookingRankProbabilityDataTable;
    }

    public static DataTable<CookingRankRewardDef> getCookingRankRewardDataTable() {
        return cookingRankRewardDataTable;
    }

    public static DataTable<CookingRecipeDef> getCookingRecipeDataTable() {
        return cookingRecipeDataTable;
    }

    public static DataTable<CookingRecipeMaterialDef> getCookingRecipeMaterialDataTable() {
        return cookingRecipeMaterialDataTable;
    }

    public static DataTable<CookingSecretIngredientDef> getCookingSecretIngredientDataTable() {
        return cookingSecretIngredientDataTable;
    }

    public static DataTable<CookingShortenPriceDef> getCookingShortenPriceDataTable() {
        return cookingShortenPriceDataTable;
    }

    public static DataTable<CookingTrayDef> getCookingTrayDataTable() {
        return cookingTrayDataTable;
    }

    public static DataTable<CookingVisitingMemberClothDef> getCookingVisitingMemberClothDataTable() {
        return cookingVisitingMemberClothDataTable;
    }

    public static DataTable<CookingVisitingMemberProbabilityDef> getCookingVisitingMemberProbabilityDataTable() {
        return cookingVisitingMemberProbabilityDataTable;
    }

    public static DataTable<CostumeDef> getCostumeDataTable() {
        return costumeDataTable;
    }

    public static DataTable<CountGachaRewardDef> getCountGachaRewardDataTable() {
        return countGachaRewardDataTable;
    }

    public static DataTable<EncoreDef> getEncoreDataTable() {
        return encoreDataTable;
    }

    public static DataTable<EncoreFeverDef> getEncoreFeverDataTable() {
        return encoreFeverDataTable;
    }

    public static DataTable<EncoreStageDef> getEncoreStageDataTable() {
        return encoreStageDataTable;
    }

    public static DataTable<EpFeverDef> getEpFeverDataTable() {
        return epFeverDataTable;
    }

    public static DataTable<EpFeverRatingDef> getEpFeverRatingDataTable() {
        return epFeverRatingDataTable;
    }

    public static DataTable<FeatureDef> getFeatureDataTable() {
        return featureDataTable;
    }

    public static DataTable<FeatureAppointmentDef> getFeatureAppointmentDataTable() {
        return featureAppointmentDataTable;
    }

    public static DataTable<FeatureAppointmentMessageDef> getFeatureAppointmentMessageDataTable() {
        return featureAppointmentMessageDataTable;
    }

    public static DataTable<FeatureCouponDef> getFeatureCouponDataTable() {
        return featureCouponDataTable;
    }

    public static DataTable<FeatureEpRewardDef> getFeatureEpRewardDataTable() {
        return featureEpRewardDataTable;
    }

    public static DataTable<FeatureHowtoDef> getFeatureHowtoDataTable() {
        return featureHowtoDataTable;
    }

    public static DataTable<FeatureItemDef> getFeatureItemDataTable() {
        return featureItemDataTable;
    }

    public static DataTable<FeatureNakayoshiLevelDef> getFeatureNakayoshiLevelDataTable() {
        return featureNakayoshiLevelDataTable;
    }

    public static DataTable<FeatureNakayoshiLevelConditionDef> getFeatureNakayoshiLevelConditionDataTable() {
        return featureNakayoshiLevelConditionDataTable;
    }

    public static DataTable<FeatureNakayoshiMemberSeqDef> getFeatureNakayoshiMemberSeqDataTable() {
        return featureNakayoshiMemberSeqDataTable;
    }

    public static DataTable<FeatureNakayoshiTotalLevelRewardDef> getFeatureNakayoshiTotalLevelRewardDataTable() {
        return featureNakayoshiTotalLevelRewardDataTable;
    }

    public static DataTable<FeaturePeriodDef> getFeaturePeriodDataTable() {
        return featurePeriodDataTable;
    }

    public static DataTable<FeaturePeriodMemberDef> getFeaturePeriodMemberDataTable() {
        return featurePeriodMemberDataTable;
    }

    public static DataTable<FeatureRankingRewardDef> getFeatureRankingRewardDataTable() {
        return featureRankingRewardDataTable;
    }

    public static DataTable<FeatureSelectionDef> getFeatureSelectionDataTable() {
        return featureSelectionDataTable;
    }

    public static DataTable<FeatureTalkDef> getFeatureTalkDataTable() {
        return featureTalkDataTable;
    }

    public static DataTable<FeatureTeamDef> getFeatureTeamDataTable() {
        return featureTeamDataTable;
    }

    public static DataTable<FeatureTermsDef> getFeatureTermsDataTable() {
        return featureTermsDataTable;
    }

    public static DataTable<FeatureTutorialDef> getFeatureTutorialDataTable() {
        return featureTutorialDataTable;
    }

    public static DataTable<FeedDef> getFeedDataTable() {
        return feedDataTable;
    }

    public static DataTable<FuncIntroductionDef> getFuncIntroductionDataTable() {
        return funcIntroductionDataTable;
    }

    public static DataTable<FuncNotifyDef> getFuncNotifyDataTable() {
        return funcNotifyDataTable;
    }

    public static DataTable<FuncTutorialDef> getFuncTutorialDataTable() {
        return funcTutorialDataTable;
    }

    public static DataTable<GachaDef> getGachaDataTable() {
        return gachaDataTable;
    }

    public static DataTable<GachaBonusChoiceDef> getGachaBonusChoiceDataTable() {
        return gachaBonusChoiceDataTable;
    }

    public static DataTable<GachaBoxDef> getGachaBoxDataTable() {
        return gachaBoxDataTable;
    }

    public static DataTable<GachaCardPremiumDef> getGachaCardPremiumDataTable() {
        return gachaCardPremiumDataTable;
    }

    public static DataTable<GachaChallengeDef> getGachaChallengeDataTable() {
        return gachaChallengeDataTable;
    }

    public static DataTable<GachaDiscountDef> getGachaDiscountDataTable() {
        return gachaDiscountDataTable;
    }

    public static DataTable<GachaGroupWeightDef> getGachaGroupWeightDataTable() {
        return gachaGroupWeightDataTable;
    }

    public static DataTable<GachaLineupDef> getGachaLineupDataTable() {
        return gachaLineupDataTable;
    }

    public static DataTable<GachaLineupHomeMemberPictureDef> getGachaLineupHomeMemberPictureDataTable() {
        return gachaLineupHomeMemberPictureDataTable;
    }

    public static DataTable<GachaOptionDef> getGachaOptionDataTable() {
        return gachaOptionDataTable;
    }

    public static DataTable<GachaPickupDef> getGachaPickupDataTable() {
        return gachaPickupDataTable;
    }

    public static DataTable<GroupPhotoDef> getGroupPhotoDataTable() {
        return groupPhotoDataTable;
    }

    public static DataTable<HelpDef> getHelpDataTable() {
        return helpDataTable;
    }

    public static DataTable<HomeBackgroundDef> getHomeBackgroundDataTable() {
        return homeBackgroundDataTable;
    }

    public static DataTable<HomeMemberClothesDef> getHomeMemberClothesDataTable() {
        return homeMemberClothesDataTable;
    }

    public static DataTable<HomeMemberPictureDef> getHomeMemberPictureDataTable() {
        return homeMemberPictureDataTable;
    }

    public static DataTable<HomeMemberTapMotionDef> getHomeMemberTapMotionDataTable() {
        return homeMemberTapMotionDataTable;
    }

    public static DataTable<HomeMessageDef> getHomeMessageDataTable() {
        return homeMessageDataTable;
    }

    public static DataTable<InvitationDef> getInvitationDataTable() {
        return invitationDataTable;
    }

    public static DataTable<InvitationRewardDef> getInvitationRewardDataTable() {
        return invitationRewardDataTable;
    }

    public static DataTable<ItemDef> getItemDataTable() {
        return itemDataTable;
    }

    public static DataTable<ItemVipDef> getItemVipDataTable() {
        return itemVipDataTable;
    }

    public static DataTable<LevelGrowthDef> getLevelGrowthDataTable() {
        return levelGrowthDataTable;
    }

    public static DataTable<LoginBonusDef> getLoginBonusDataTable() {
        return loginBonusDataTable;
    }

    public static DataTable<LoginBonusSeqDef> getLoginBonusSeqDataTable() {
        return loginBonusSeqDataTable;
    }

    public static DataTable<LotteryDef> getLotteryDataTable() {
        return lotteryDataTable;
    }

    public static DataTable<LotteryRewardDef> getLotteryRewardDataTable() {
        return lotteryRewardDataTable;
    }

    public static DataTable<LotterySelectionDef> getLotterySelectionDataTable() {
        return lotterySelectionDataTable;
    }

    public static DataTable<MemberDef> getMemberDataTable() {
        return memberDataTable;
    }

    public static DataTable<MembershipContinueRewardDef> getMembershipContinueRewardDataTable() {
        return membershipContinueRewardDataTable;
    }

    public static DataTable<MemberDearlevelDef> getMemberDearlevelDataTable() {
        return memberDearlevelDataTable;
    }

    public static DataTable<MemberDeartoolReactionDef> getMemberDeartoolReactionDataTable() {
        return memberDeartoolReactionDataTable;
    }

    public static DataTable<MemberGroupDef> getMemberGroupDataTable() {
        return memberGroupDataTable;
    }

    public static DataTable<MemberLikabilitylevelDef> getMemberLikabilitylevelDataTable() {
        return memberLikabilitylevelDataTable;
    }

    public static DataTable<MemberLikabilitytextDef> getMemberLikabilitytextDataTable() {
        return memberLikabilitytextDataTable;
    }

    public static DataTable<MemberMessageDef> getMemberMessageDataTable() {
        return memberMessageDataTable;
    }

    public static DataTable<MemorialPhotoDef> getMemorialPhotoDataTable() {
        return memorialPhotoDataTable;
    }

    public static DataTable<MileageDef> getMileageDataTable() {
        return mileageDataTable;
    }

    public static DataTable<MiniGameDef> getMiniGameDataTable() {
        return miniGameDataTable;
    }

    public static DataTable<OfferWallDef> getOfferWallDataTable() {
        return offerWallDataTable;
    }

    public static DataTable<OpeningShowDef> getOpeningShowDataTable() {
        return openingShowDataTable;
    }

    public static DataTable<PassiveSkillDef> getPassiveSkillDataTable() {
        return passiveSkillDataTable;
    }

    public static DataTable<PhotoBoothDef> getPhotoBoothDataTable() {
        return photoBoothDataTable;
    }

    public static DataTable<PhotoStampDef> getPhotoStampDataTable() {
        return photoStampDataTable;
    }

    public static DataTable<PlayerDef> getPlayerDataTable() {
        return playerDataTable;
    }

    public static DataTable<PlayerTitleDef> getPlayerTitleDataTable() {
        return playerTitleDataTable;
    }

    public static DataTable<PlayerTitleBannerDef> getPlayerTitleBannerDataTable() {
        return playerTitleBannerDataTable;
    }

    public static DataTable<PlayerTitleCategoryDef> getPlayerTitleCategoryDataTable() {
        return playerTitleCategoryDataTable;
    }

    public static DataTable<PlayerTitleSpecialDef> getPlayerTitleSpecialDataTable() {
        return playerTitleSpecialDataTable;
    }

    public static DataTable<PoolDef> getPoolDataTable() {
        return poolDataTable;
    }

    public static DataTable<PopRatioDef> getPopRatioDataTable() {
        return popRatioDataTable;
    }

    public static DataTable<PracticeExamDef> getPracticeExamDataTable() {
        return practiceExamDataTable;
    }

    public static DataTable<PracticeExamEvaluationCommentDef> getPracticeExamEvaluationCommentDataTable() {
        return practiceExamEvaluationCommentDataTable;
    }

    public static DataTable<PracticeExamPuzzleRuleDef> getPracticeExamPuzzleRuleDataTable() {
        return practiceExamPuzzleRuleDataTable;
    }

    public static DataTable<PracticeExamPuzzleRuleStageDef> getPracticeExamPuzzleRuleStageDataTable() {
        return practiceExamPuzzleRuleStageDataTable;
    }

    public static DataTable<PracticeExamStageDef> getPracticeExamStageDataTable() {
        return practiceExamStageDataTable;
    }

    public static DataTable<PracticeExamStageDropRewardDef> getPracticeExamStageDropRewardDataTable() {
        return practiceExamStageDropRewardDataTable;
    }

    public static DataTable<PracticeExamStageRankingRewardDef> getPracticeExamStageRankingRewardDataTable() {
        return practiceExamStageRankingRewardDataTable;
    }

    public static DataTable<PromotionDef> getPromotionDataTable() {
        return promotionDataTable;
    }

    public static DataTable<QuestDef> getQuestDataTable() {
        return questDataTable;
    }

    public static DataTable<QuintupletGameDef> getQuintupletGameDataTable() {
        return quintupletGameDataTable;
    }

    public static DataTable<ReactionClothesDef> getReactionClothesDataTable() {
        return reactionClothesDataTable;
    }

    public static DataTable<ReactionSceneDef> getReactionSceneDataTable() {
        return reactionSceneDataTable;
    }

    public static DataTable<RelationshipDef> getRelationshipDataTable() {
        return relationshipDataTable;
    }

    public static DataTable<RetryableChapterDef> getRetryableChapterDataTable() {
        return retryableChapterDataTable;
    }

    public static DataTable<RetryableQuestDef> getRetryableQuestDataTable() {
        return retryableQuestDataTable;
    }

    public static DataTable<RetryableStageDef> getRetryableStageDataTable() {
        return retryableStageDataTable;
    }

    public static DataTable<RetryableStageGroupDef> getRetryableStageGroupDataTable() {
        return retryableStageGroupDataTable;
    }

    public static DataTable<RewardDef> getRewardDataTable() {
        return rewardDataTable;
    }

    public static DataTable<RewardCardPropertyDef> getRewardCardPropertyDataTable() {
        return rewardCardPropertyDataTable;
    }

    public static DataTable<RoundDef> getRoundDataTable() {
        return roundDataTable;
    }

    public static DataTable<ScenarioChoicesDef> getScenarioChoicesDataTable() {
        return scenarioChoicesDataTable;
    }

    public static DataTable<SeasonDef> getSeasonDataTable() {
        return seasonDataTable;
    }

    public static DataTable<SeasonRankingRewardDef> getSeasonRankingRewardDataTable() {
        return seasonRankingRewardDataTable;
    }

    public static DataTable<ShopDef> getShopDataTable() {
        return shopDataTable;
    }

    public static DataTable<ShopChallengeDef> getShopChallengeDataTable() {
        return shopChallengeDataTable;
    }

    public static DataTable<ShopDearpointDef> getShopDearpointDataTable() {
        return shopDearpointDataTable;
    }

    public static DataTable<ShopExchangeDef> getShopExchangeDataTable() {
        return shopExchangeDataTable;
    }

    public static DataTable<ShopExchangeGroupDef> getShopExchangeGroupDataTable() {
        return shopExchangeGroupDataTable;
    }

    public static DataTable<ShopSharingDef> getShopSharingDataTable() {
        return shopSharingDataTable;
    }

    public static DataTable<SituationDef> getSituationDataTable() {
        return situationDataTable;
    }

    public static DataTable<SpecialContentDef> getSpecialContentDataTable() {
        return specialContentDataTable;
    }

    public static DataTable<StageDef> getStageDataTable() {
        return stageDataTable;
    }

    public static DataTable<StageBoostCampaignDef> getStageBoostCampaignDataTable() {
        return stageBoostCampaignDataTable;
    }

    public static DataTable<StageDropReplacementDef> getStageDropReplacementDataTable() {
        return stageDropReplacementDataTable;
    }

    public static DataTable<StageExDef> getStageExDataTable() {
        return stageExDataTable;
    }

    public static DataTable<StageExConditionsDef> getStageExConditionsDataTable() {
        return stageExConditionsDataTable;
    }

    public static DataTable<StagePlayConditionDef> getStagePlayConditionDataTable() {
        return stagePlayConditionDataTable;
    }

    public static DataTable<StageUnlockReceipeDef> getStageUnlockReceipeDataTable() {
        return stageUnlockReceipeDataTable;
    }

    public static DataTable<StillPictureDef> getStillPictureDataTable() {
        return stillPictureDataTable;
    }

    public static DataTable<StoryDef> getStoryDataTable() {
        return storyDataTable;
    }

    public static DataTable<StoryGroupDef> getStoryGroupDataTable() {
        return storyGroupDataTable;
    }

    public static DataTable<StoryPlayingConditionDef> getStoryPlayingConditionDataTable() {
        return storyPlayingConditionDataTable;
    }

    public static DataTable<SusumeRaihaConfigDef> getSusumeRaihaConfigDataTable() {
        return susumeRaihaConfigDataTable;
    }

    public static DataTable<SynthesisRecipeDef> getSynthesisRecipeDataTable() {
        return synthesisRecipeDataTable;
    }

    public static DataTable<SynthesisRecipeMaterialDef> getSynthesisRecipeMaterialDataTable() {
        return synthesisRecipeMaterialDataTable;
    }

    public static DataTable<TeamBattleBoostDef> getTeamBattleBoostDataTable() {
        return teamBattleBoostDataTable;
    }

    public static DataTable<TeamBattleDifficultyDef> getTeamBattleDifficultyDataTable() {
        return teamBattleDifficultyDataTable;
    }

    public static DataTable<TeamBattleEpRewardDef> getTeamBattleEpRewardDataTable() {
        return teamBattleEpRewardDataTable;
    }

    public static DataTable<TeamBattleEventDef> getTeamBattleEventDataTable() {
        return teamBattleEventDataTable;
    }

    public static DataTable<TeamBattleHowtoDef> getTeamBattleHowtoDataTable() {
        return teamBattleHowtoDataTable;
    }

    public static DataTable<TeamBattleMemberRankingRewardDef> getTeamBattleMemberRankingRewardDataTable() {
        return teamBattleMemberRankingRewardDataTable;
    }

    public static DataTable<TeamBattleSelectionDef> getTeamBattleSelectionDataTable() {
        return teamBattleSelectionDataTable;
    }

    public static DataTable<TeamBattleStageDef> getTeamBattleStageDataTable() {
        return teamBattleStageDataTable;
    }

    public static DataTable<TeamBattleTeamDef> getTeamBattleTeamDataTable() {
        return teamBattleTeamDataTable;
    }

    public static DataTable<TeamBattleTeamContributionRewardDef> getTeamBattleTeamContributionRewardDataTable() {
        return teamBattleTeamContributionRewardDataTable;
    }

    public static DataTable<TeamBattleTermsDef> getTeamBattleTermsDataTable() {
        return teamBattleTermsDataTable;
    }

    public static DataTable<TeamBattleTutorialDef> getTeamBattleTutorialDataTable() {
        return teamBattleTutorialDataTable;
    }

    public static DataTable<TeamBattleWinnerRewardDef> getTeamBattleWinnerRewardDataTable() {
        return teamBattleWinnerRewardDataTable;
    }

    public static DataTable<TutorialDef> getTutorialDataTable() {
        return tutorialDataTable;
    }

    public static DataTable<UnitSkillDef> getUnitSkillDataTable() {
        return unitSkillDataTable;
    }

    public static DataTable<UnitSkillPassiveSkillPoolDef> getUnitSkillPassiveSkillPoolDataTable() {
        return unitSkillPassiveSkillPoolDataTable;
    }

    public static DataTable<UnitSkillRarityDef> getUnitSkillRarityDataTable() {
        return unitSkillRarityDataTable;
    }

    public static DataTable<VersionDef> getVersionDataTable() {
        return versionDataTable;
    }

    public static DataTable<VipContinueRewardDef> getVipContinueRewardDataTable() {
        return vipContinueRewardDataTable;
    }

    public static DataTable<VrChapterDef> getVrChapterDataTable() {
        return vrChapterDataTable;
    }

    public static DataTable<VrContentDef> getVrContentDataTable() {
        return vrContentDataTable;
    }

    public static DataTable<WorkDef> getWorkDataTable() {
        return workDataTable;
    }

}