package com.emanuelplays.notenoughobjectinfo.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class EntityInfoProvider {

    public record EntityData(
            String displayName, String registryId, String modId,
            String categoryName, boolean isLiving, boolean isBaby,
            float health, float maxHealth, float armor, float armorToughness,
            double speed, double attackDamage, double attackSpeed,
            double followRange, double knockbackResistance,
            boolean isBurning, boolean isInvisible, boolean isSilent,
            List<String> activeEffects,
            String positionStr, String dimensionId,
            List<String> tags,
            int passengerCount, String vehicle,
            String uuid,
            String compactNbt
    ) {}

    public static EntityData from(Entity entity) {
        EntityType<?> type = entity.getType();
        ResourceLocation rl = ForgeRegistries.ENTITY_TYPES.getKey(type);

        String registryId  = rl != null ? rl.toString() : "unknown:unknown";
        String modId       = rl != null ? rl.getNamespace() : "unknown";
        String displayName = entity.getDisplayName().getString();
        String category    = type.getCategory().getName();

        boolean isLiving = entity instanceof LivingEntity;
        boolean isBaby   = entity instanceof AgeableMob am && am.isBaby();
        float health = 0, maxHealth = 0, armor = 0, armorToughness = 0;
        double speed = 0, attackDamage = 0, attackSpeed = 0, followRange = 0, kbRes = 0;
        List<String> effects = new ArrayList<>();

        if (isLiving) {
            LivingEntity living = (LivingEntity) entity;
            health        = living.getHealth();
            maxHealth     = living.getMaxHealth();
            armor         = living.getArmorValue();

            if (living.getAttribute(Attributes.MOVEMENT_SPEED)    != null) speed        = living.getAttributeValue(Attributes.MOVEMENT_SPEED);
            if (living.getAttribute(Attributes.ATTACK_DAMAGE)      != null) attackDamage = living.getAttributeValue(Attributes.ATTACK_DAMAGE);
            if (living.getAttribute(Attributes.ATTACK_SPEED)       != null) attackSpeed  = living.getAttributeValue(Attributes.ATTACK_SPEED);
            if (living.getAttribute(Attributes.FOLLOW_RANGE)       != null) followRange  = living.getAttributeValue(Attributes.FOLLOW_RANGE);
            if (living.getAttribute(Attributes.KNOCKBACK_RESISTANCE)!= null) kbRes       = living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            if (living.getAttribute(Attributes.ARMOR_TOUGHNESS)    != null) armorToughness = (float)living.getAttributeValue(Attributes.ARMOR_TOUGHNESS);

            for (MobEffectInstance e : living.getActiveEffects()) {
                String name = e.getEffect().getDescriptionId().replaceAll("effect\\.minecraft\\.", "");
                effects.add("§5" + name + " §7(Lv." + (e.getAmplifier() + 1) + ", " + fmtDuration(e.getDuration()) + ")");
            }
        }

        String posStr = String.format("%.1f, %.1f, %.1f", entity.getX(), entity.getY(), entity.getZ());
        String dimId  = entity.level().dimension().location().toString();

        List<String> tags = new ArrayList<>();
        type.getTags().map(t -> "#" + t.location()).forEach(tags::add);

        int    passengerCount = entity.getPassengers().size();
        String vehicle        = entity.getVehicle() != null ? entity.getVehicle().getType().getDescriptionId() : "";
        String uuid           = entity.getStringUUID();

        CompoundTag nbt = new CompoundTag();
        entity.saveWithoutId(nbt);
        String compactNbt = BlockInfoProvider.compactNbt(nbt, 512);

        return new EntityData(
                displayName, registryId, modId,
                category, isLiving, isBaby,
                health, maxHealth, armor, armorToughness,
                speed, attackDamage, attackSpeed, followRange, kbRes,
                entity.isOnFire(), entity.isInvisible(), entity.isSilent(),
                effects, posStr, dimId, tags,
                passengerCount, vehicle, uuid, compactNbt
        );
    }

    private static String fmtDuration(int ticks) {
        int s = ticks / 20, m = s / 60; s %= 60;
        return m > 0 ? m + "m " + s + "s" : s + "s";
    }
}
