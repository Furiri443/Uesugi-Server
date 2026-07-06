package com.emu.tqqserver.game.user;

import com.emu.tqqserver.proto.pkg_puser.Currency;
import com.emu.tqqserver.proto.pkg_puser.User;

/**
 * Represents a player account row in the {@code users} table.
 */
public class UserEntity {

    private long userId;
    private String platformType;
    private String platformId;
    private String deviceId;
    private String nickname;
    private String comment;
    private int rank;
    private int exp;
    private int jewel;
    private int payJewel;
    private int coin;
    private int tutorialStep;
    private int profileBackgroundId;
    private int homeBackgroundId;
    private int playerTitleId;
    private int playerTitleTargetId;
    private boolean isNewUser;
    private int dailyRewardReceivedAt;
    
    private int optionBgm = 10;
    private int optionSe = 10;
    private int optionVoice = 10;
    private int optionProtectCardR6 = 1;
    private int optionProtectCardR5 = 1;
    private int optionProtectCardFirst = 1;

    public UserEntity() {}

    public long getUserId()        { return userId; }
    public void setUserId(long v)  { this.userId = v; }

    public String getPlatformType()          { return platformType; }
    public void setPlatformType(String v)    { this.platformType = v; }

    public String getPlatformId()            { return platformId; }
    public void setPlatformId(String v)      { this.platformId = v; }

    public String getDeviceId()              { return deviceId; }
    public void setDeviceId(String v)        { this.deviceId = v; }

    public String getNickname()              { return nickname; }
    public void setNickname(String v)        { this.nickname = v; }

    public String getComment()               { return comment; }
    public void setComment(String v)         { this.comment = v; }

    public int getRank()                     { return rank; }
    public void setRank(int v)               { this.rank = v; }

    public int getExp()                      { return exp; }
    public void setExp(int v)                { this.exp = v; }

    public int getJewel()                    { return jewel; }
    public void setJewel(int v)              { this.jewel = v; }
    public int getPayJewel()                 { return payJewel; }
    public void setPayJewel(int v)           { this.payJewel = v; }
    public int getCoin()                     { return coin; }
    public void setCoin(int v)               { this.coin = v; }

    public int getTutorialStep()             { return tutorialStep; }
    public void setTutorialStep(int v)       { this.tutorialStep = v; }

    public int getProfileBackgroundId()      { return profileBackgroundId; }
    public void setProfileBackgroundId(int v){ this.profileBackgroundId = v; }

    public int getHomeBackgroundId()         { return homeBackgroundId; }
    public void setHomeBackgroundId(int v)   { this.homeBackgroundId = v; }

    public int getPlayerTitleId()            { return playerTitleId; }
    public void setPlayerTitleId(int v)       { this.playerTitleId = v; }

    public int getPlayerTitleTargetId()      { return playerTitleTargetId; }
    public void setPlayerTitleTargetId(int v) { this.playerTitleTargetId = v; }

    public int getDailyRewardReceivedAt()    { return dailyRewardReceivedAt; }
    public void setDailyRewardReceivedAt(int v){ this.dailyRewardReceivedAt = v; }

    public int getOptionBgm()                { return optionBgm; }
    public void setOptionBgm(int v)          { this.optionBgm = v; }

    public int getOptionSe()                 { return optionSe; }
    public void setOptionSe(int v)           { this.optionSe = v; }

    public int getOptionVoice()              { return optionVoice; }
    public void setOptionVoice(int v)        { this.optionVoice = v; }

    public int getOptionProtectCardR6()      { return optionProtectCardR6; }
    public void setOptionProtectCardR6(int v) { this.optionProtectCardR6 = v; }

    public int getOptionProtectCardR5()      { return optionProtectCardR5; }
    public void setOptionProtectCardR5(int v) { this.optionProtectCardR5 = v; }

    public int getOptionProtectCardFirst()   { return optionProtectCardFirst; }
    public void setOptionProtectCardFirst(int v){ this.optionProtectCardFirst = v; }

    public boolean isNewUser()               { return isNewUser; }
    public void setNewUser(boolean newUser)  { isNewUser = newUser; }

    public User toProto() {
        com.emu.tqqserver.server.ServerConfig.GameDefaultsConfig defaults = 
            com.emu.tqqserver.game.GameContext.getInstance().getConfig().getGameDefaults();

        return User.newBuilder()
            .setUid((int) this.getUserId())
            .setNickname(this.getNickname())
            .setLevel(this.getRank())
            .setExp(this.getExp())
            .setRuby(this.getJewel())
            .setTutorial((this.getTutorialStep() == 0 && this.getRank() > 1) ? 999 : this.getTutorialStep())
            .setIsTutorialFinish(((this.getTutorialStep() == 0 && this.getRank() > 1) || this.getTutorialStep() >= 999) ? 1 : 0)
            .setIsClearBeginner(0)
            .setActiveUnit(1)
            .setFavoriteCharacters(1)
            .setMuid("muid-" + this.getUserId())
            .setWalletId("wallet-" + this.getUserId())
            .setStatus("open")
            .setLastname(defaults.getDefaultLastname())
            .setFirstname(defaults.getDefaultFirstname())
            .setLastnameKana(defaults.getDefaultLastnameKana())
            .setFirstnameKana(defaults.getDefaultFirstnameKana())
            .setComment(defaults.getDefaultComment())
            .setHomeBackgroundId(this.getProfileBackgroundId() > 0 ? this.getProfileBackgroundId() : defaults.getDefaultBackgroundId())
            .setProfileBackgroundId(this.getProfileBackgroundId() > 0 ? this.getProfileBackgroundId() : defaults.getDefaultBackgroundId())
            .setPlayerTitleId(this.getPlayerTitleId() > 0 ? this.getPlayerTitleId() : defaults.getDefaultPlayerTitleId())
            .setPlayerTitleTargetId(this.getPlayerTitleTargetId())
            .setLeaf(this.getCoin())
            .setAp(defaults.getDefaultAp())
            .build();
    }

    public Currency toCurrencyProto() {
        return Currency.newBuilder()
            .setFreeCoin(this.getJewel())
            .setPayCoin(this.getPayJewel())
            .setTotalCoin(this.getJewel() + this.getPayJewel())
            .build();
    }
}
