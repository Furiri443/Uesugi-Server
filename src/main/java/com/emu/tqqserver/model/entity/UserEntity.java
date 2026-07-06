package com.emu.tqqserver.model.entity;

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

    public int getPlayerTitleTargetId()       { return playerTitleTargetId; }
    public void setPlayerTitleTargetId(int v) { this.playerTitleTargetId = v; }

    public boolean isNewUser()               { return isNewUser; }
    public void setNewUser(boolean v)        { this.isNewUser = v; }

    public int getDailyRewardReceivedAt()           { return dailyRewardReceivedAt; }
    public void setDailyRewardReceivedAt(int v)     { this.dailyRewardReceivedAt = v; }
}
