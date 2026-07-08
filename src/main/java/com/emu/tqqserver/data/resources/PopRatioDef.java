package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "pop_ratio.json")
public class PopRatioDef extends BaseDef {
    private int id;

    private int red;

    private int green;

    private int blue;

    private int yellow;

    private int normal;

    private int purple;

    private int obstacle;

    @SerializedName("obstacle_life_min")
    private int obstacleLifeMin;

    @SerializedName("obstacle_life_max")
    private int obstacleLifeMax;

    private int chest;

    private int ameba;

    @SerializedName("normal_level1")
    private int normalLevel1;

    @SerializedName("normal_level2")
    private int normalLevel2;

    @SerializedName("normal_level3")
    private int normalLevel3;

    @SerializedName("universal_ball")
    private int universalBall;

    private int block;

    @SerializedName("block_life_min")
    private int blockLifeMin;

    @SerializedName("block_life_max")
    private int blockLifeMax;

    @SerializedName("event_small")
    private int eventSmall;

    @SerializedName("event_medium")
    private int eventMedium;

    @SerializedName("event_large")
    private int eventLarge;

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getYellow() {
        return yellow;
    }

    public int getNormal() {
        return normal;
    }

    public int getPurple() {
        return purple;
    }

    public int getObstacle() {
        return obstacle;
    }

    public int getObstacleLifeMin() {
        return obstacleLifeMin;
    }

    public int getObstacleLifeMax() {
        return obstacleLifeMax;
    }

    public int getChest() {
        return chest;
    }

    public int getAmeba() {
        return ameba;
    }

    public int getNormalLevel1() {
        return normalLevel1;
    }

    public int getNormalLevel2() {
        return normalLevel2;
    }

    public int getNormalLevel3() {
        return normalLevel3;
    }

    public int getUniversalBall() {
        return universalBall;
    }

    public int getBlock() {
        return block;
    }

    public int getBlockLifeMin() {
        return blockLifeMin;
    }

    public int getBlockLifeMax() {
        return blockLifeMax;
    }

    public int getEventSmall() {
        return eventSmall;
    }

    public int getEventMedium() {
        return eventMedium;
    }

    public int getEventLarge() {
        return eventLarge;
    }

    @Override
    public int getId() {
        return id;
    }
}