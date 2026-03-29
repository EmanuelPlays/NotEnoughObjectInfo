package com.emanuelplays.notenoughobjectinfo.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ItemInfoProvider {

    public record ItemData(
            String displayName,
            String registryId,
            String modId,
            int maxDurability,
            int currentDurability,
            int enchantability,
            int burnTime,
            boolean isFood,
            int foodValue,
            float saturation,
            boolean canAlwaysEat,
            int maxStackSize,
            String repairIngredient,
            List<String> tags,
            String nbtSummary
    ) {}

    public static ItemData from(ItemStack stack) {
        if (stack.isEmpty()) return null;
        Item item = stack.getItem();
        ResourceLocation rl = ForgeRegistries.ITEMS.getKey(item);

        String registryId = rl != null ? rl.toString() : "unknown:unknown";
        String modId      = rl != null ? rl.getNamespace() : "unknown";
        String displayName = stack.getHoverName().getString();

        int maxDurability = item.getMaxDamage(stack);
        int currentDurability = maxDurability > 0 ? maxDurability - stack.getDamageValue() : -1;
        int enchantability = item.getEnchantmentValue(stack);
        int burnTime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);

        boolean isFood = item.isEdible();
        int foodValue = 0;
        float saturation = 0f;
        boolean canAlwaysEat = false;
        if (isFood && item.getFoodProperties(stack, null) != null) {
            var food = item.getFoodProperties(stack, null);
            foodValue = food.getNutrition();
            saturation = food.getSaturationModifier();
            canAlwaysEat = food.canAlwaysEat();
        }

        int maxStackSize = item.getMaxStackSize(stack);

        // Repair ingredient
        String repairIngredient = "None";
        if (item.isRepairable(stack)) {
            repairIngredient = "Has repair recipe";
        }

        // Tags
        List<String> tagList = new ArrayList<>();
        for (TagKey<Item> tag : stack.getTags().toList()) {
            tagList.add("#" + tag.location());
        }

        // NBT
        String nbtSummary = "None";
        if (stack.hasTag() && stack.getTag() != null) {
            nbtSummary = BlockInfoProvider.compactNbt(stack.getTag(), 256);
        }

        return new ItemData(
                displayName, registryId, modId,
                maxDurability, currentDurability,
                enchantability, burnTime,
                isFood, foodValue, saturation, canAlwaysEat,
                maxStackSize, repairIngredient, tagList, nbtSummary
        );
    }
}
