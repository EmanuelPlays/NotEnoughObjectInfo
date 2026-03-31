package com.emanuelplays.notenoughobjectinfo.util;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodData;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatsProvider {

    public record PlayerStats(
            float health, float maxHealth,
            int armor, float armorToughness,
            int food, float saturation,
            float speed, double reach,
            float luck,
            int xpLevel, float xpProgress,
            int totalXP,
            List<String> effects,
            boolean isFlying, boolean isSprinting, boolean isCrouching,
            float fallDistance
    ) {}

    public static PlayerStats from(LocalPlayer player) {
        float health    = player.getHealth();
        float maxHealth = player.getMaxHealth();
        int   armor     = player.getArmorValue();
        float toughness = (float) player.getAttributeValue(Attributes.ARMOR_TOUGHNESS);

        FoodData food  = player.getFoodData();
        int  foodLevel = food.getFoodLevel();
        float saturation= food.getSaturationLevel();

        double speed  = player.getAttributeValue(Attributes.MOVEMENT_SPEED);
        double reach  = player.getAttributeValue(net.minecraftforge.common.ForgeMod.BLOCK_REACH.get());
        float  luck   = (float) player.getAttributeValue(Attributes.LUCK);

        int   xpLevel   = player.experienceLevel;
        float xpProgress= player.experienceProgress;
        int   totalXP   = player.totalExperience;

        List<String> effects = new ArrayList<>();
        for (MobEffectInstance e : player.getActiveEffects()) {
            String name = e.getEffect().getDescriptionId().replaceAll("effect\\.minecraft\\.", "");
            effects.add("§5" + name + " §7(Lv." + (e.getAmplifier() + 1) + ", " + fmtDuration(e.getDuration()) + ")");
        }

        return new PlayerStats(
                health, maxHealth,
                armor, toughness,
                foodLevel, saturation,
                (float) speed, reach, luck,
                xpLevel, xpProgress, totalXP,
                effects,
                player.getAbilities().flying,
                player.isSprinting(),
                player.isCrouching(),
                player.fallDistance
        );
    }

    private static String fmtDuration(int ticks) {
        int s = ticks / 20, m = s / 60; s %= 60;
        return m > 0 ? m + "m " + s + "s" : s + "s";
    }
}
