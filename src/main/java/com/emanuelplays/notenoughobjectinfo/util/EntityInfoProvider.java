package com.emanuelplays.notenoughobjectinfo.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntityInfoProvider {

    public record EntityData(
            String displayName,
            String registryId,
            String modId,
            String categoryName,
            boolean isLiving,
            float health,
            float maxHealth,
            float armor,
            double speed,
            double attackDamage,
            double followRange,
            double knockbackResistance,
            boolean isBurning,
            List<String> activeEffects,
            String positionStr,
            String dimensionId,
            List<String> tags,
            boolean hasPassengers,
            String vehicle,
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
        float health = 0, maxHealth = 0, armor = 0;
        double speed = 0, attackDamage = 0, followRange = 0, knockbackResistance = 0;
        List<String> effects = new ArrayList<>();

        if (isLiving) {
            LivingEntity living = (LivingEntity) entity;
            health    = living.getHealth();
            maxHealth = living.getMaxHealth();
            armor     = living.getArmorValue();

            if (living.getAttribute(Attributes.MOVEMENT_SPEED) != null)
                speed = living.getAttributeValue(Attributes.MOVEMENT_SPEED);
            if (living.getAttribute(Attributes.ATTACK_DAMAGE) != null)
                attackDamage = living.getAttributeValue(Attributes.ATTACK_DAMAGE);
            if (living.getAttribute(Attributes.FOLLOW_RANGE) != null)
                followRange = living.getAttributeValue(Attributes.FOLLOW_RANGE);
            if (living.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null)
                knockbackResistance = living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);

            Collection<MobEffectInstance> activeEffects = living.getActiveEffects();
            for (MobEffectInstance effect : activeEffects) {
                String effectName = effect.getEffect().getDescriptionId();
                effects.add(effectName + " (Lv." + (effect.getAmplifier() + 1) + ", " + formatDuration(effect.getDuration()) + ")");
            }
        }

        String posStr = String.format("%.1f, %.1f, %.1f",
                entity.getX(), entity.getY(), entity.getZ());

        String dimId = entity.level().dimension().location().toString();

        // Tags
        List<String> tagList = new ArrayList<>();
        for (TagKey<EntityType<?>> tag : type.getTags().toList()) {
            tagList.add("#" + tag.location());
        }

        // Passengers
        boolean hasPassengers = !entity.getPassengers().isEmpty();
        String vehicle = entity.getVehicle() != null
                ? entity.getVehicle().getType().getDescriptionId()
                : "";

        // Compact NBT
        CompoundTag nbt = new CompoundTag();
        entity.saveWithoutId(nbt);
        String compactNbt = BlockInfoProvider.compactNbt(nbt, 256);

        return new EntityData(
                displayName, registryId, modId, category, isLiving,
                health, maxHealth, armor, speed, attackDamage,
                followRange, knockbackResistance,
                entity.isOnFire(),
                effects, posStr, dimId, tagList,
                hasPassengers, vehicle, compactNbt
        );
    }

    private static String formatDuration(int ticks) {
        int totalSeconds = ticks / 20;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        if (minutes > 0) return minutes + "m " + seconds + "s";
        return seconds + "s";
    }
}
