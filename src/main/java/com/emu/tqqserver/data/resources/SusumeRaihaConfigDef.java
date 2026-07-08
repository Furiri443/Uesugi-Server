package com.emu.tqqserver.data.resources;

import com.emu.tqqserver.data.BaseDef;
import com.emu.tqqserver.data.ResourceType;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@ResourceType(name = "susume_raiha_config.json")
public class SusumeRaihaConfigDef extends BaseDef {
    @SerializedName("mini_game_id")
    private int miniGameId;

    @SerializedName("start_wait_init_time")
    private float startWaitInitTime;

    @SerializedName("object_create_distance")
    private float objectCreateDistance;

    @SerializedName("time_decrease")
    private float timeDecrease;

    @SerializedName("levelup_time_decrease_rate")
    private float levelupTimeDecreaseRate;

    @SerializedName("initial_speed")
    private float initialSpeed;

    @SerializedName("first_speed")
    private float firstSpeed;

    @SerializedName("first_acceleration")
    private float firstAcceleration;

    private float acceleration;

    @SerializedName("first_max_speed")
    private float firstMaxSpeed;

    @SerializedName("levelup_max_speed")
    private float levelupMaxSpeed;

    @SerializedName("speed_down_rate")
    private float speedDownRate;

    @SerializedName("score_up_rate")
    private int scoreUpRate;

    @SerializedName("trophy_get_score")
    private float trophyGetScore;

    @SerializedName("game_clear_level")
    private int gameClearLevel;

    @SerializedName("trophy_appear_score")
    private float trophyAppearScore;

    @SerializedName("next_level_score")
    private float nextLevelScore;

    @SerializedName("power_duration")
    private float powerDuration;

    @SerializedName("power_imminent")
    private float powerImminent;

    @SerializedName("apron_rate")
    private float apronRate;

    @SerializedName("milk_rate")
    private float milkRate;

    @SerializedName("sugar_rate")
    private float sugarRate;

    @SerializedName("flour_rate")
    private float flourRate;

    @SerializedName("fruit_rate")
    private float fruitRate;

    @SerializedName("egg_rate")
    private float eggRate;

    @SerializedName("milk_stone_rate")
    private float milkStoneRate;

    @SerializedName("sugar_stone_rate")
    private float sugarStoneRate;

    @SerializedName("flour_stone_rate")
    private float flourStoneRate;

    @SerializedName("fruit_stone_rate")
    private float fruitStoneRate;

    @SerializedName("egg_stone_rate")
    private float eggStoneRate;

    @SerializedName("milk_speed_rate")
    private float milkSpeedRate;

    @SerializedName("sugar_speed_rate")
    private float sugarSpeedRate;

    @SerializedName("flour_speed_rate")
    private float flourSpeedRate;

    @SerializedName("fruit_speed_rate")
    private float fruitSpeedRate;

    @SerializedName("egg_speed_rate")
    private float eggSpeedRate;

    @SerializedName("milk_score_rate")
    private float milkScoreRate;

    @SerializedName("sugar_score_rate")
    private float sugarScoreRate;

    @SerializedName("flour_score_rate")
    private float flourScoreRate;

    @SerializedName("fruit_score_rate")
    private float fruitScoreRate;

    @SerializedName("egg_score_rate")
    private float eggScoreRate;

    @SerializedName("clear_score")
    private int clearScore;

    public int getMiniGameId() {
        return miniGameId;
    }

    public float getStartWaitInitTime() {
        return startWaitInitTime;
    }

    public float getObjectCreateDistance() {
        return objectCreateDistance;
    }

    public float getTimeDecrease() {
        return timeDecrease;
    }

    public float getLevelupTimeDecreaseRate() {
        return levelupTimeDecreaseRate;
    }

    public float getInitialSpeed() {
        return initialSpeed;
    }

    public float getFirstSpeed() {
        return firstSpeed;
    }

    public float getFirstAcceleration() {
        return firstAcceleration;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getFirstMaxSpeed() {
        return firstMaxSpeed;
    }

    public float getLevelupMaxSpeed() {
        return levelupMaxSpeed;
    }

    public float getSpeedDownRate() {
        return speedDownRate;
    }

    public int getScoreUpRate() {
        return scoreUpRate;
    }

    public float getTrophyGetScore() {
        return trophyGetScore;
    }

    public int getGameClearLevel() {
        return gameClearLevel;
    }

    public float getTrophyAppearScore() {
        return trophyAppearScore;
    }

    public float getNextLevelScore() {
        return nextLevelScore;
    }

    public float getPowerDuration() {
        return powerDuration;
    }

    public float getPowerImminent() {
        return powerImminent;
    }

    public float getApronRate() {
        return apronRate;
    }

    public float getMilkRate() {
        return milkRate;
    }

    public float getSugarRate() {
        return sugarRate;
    }

    public float getFlourRate() {
        return flourRate;
    }

    public float getFruitRate() {
        return fruitRate;
    }

    public float getEggRate() {
        return eggRate;
    }

    public float getMilkStoneRate() {
        return milkStoneRate;
    }

    public float getSugarStoneRate() {
        return sugarStoneRate;
    }

    public float getFlourStoneRate() {
        return flourStoneRate;
    }

    public float getFruitStoneRate() {
        return fruitStoneRate;
    }

    public float getEggStoneRate() {
        return eggStoneRate;
    }

    public float getMilkSpeedRate() {
        return milkSpeedRate;
    }

    public float getSugarSpeedRate() {
        return sugarSpeedRate;
    }

    public float getFlourSpeedRate() {
        return flourSpeedRate;
    }

    public float getFruitSpeedRate() {
        return fruitSpeedRate;
    }

    public float getEggSpeedRate() {
        return eggSpeedRate;
    }

    public float getMilkScoreRate() {
        return milkScoreRate;
    }

    public float getSugarScoreRate() {
        return sugarScoreRate;
    }

    public float getFlourScoreRate() {
        return flourScoreRate;
    }

    public float getFruitScoreRate() {
        return fruitScoreRate;
    }

    public float getEggScoreRate() {
        return eggScoreRate;
    }

    public int getClearScore() {
        return clearScore;
    }

    @Override
    public int getId() {
        return 0;
    }
}