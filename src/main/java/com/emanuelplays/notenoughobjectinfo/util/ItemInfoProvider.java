package com.emanuelplays.notenoughobjectinfo.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class ItemInfoProvider {

    public record ItemData(
            String displayName, String registryId, String modId,
            int maxDurability, int currentDurability,
            int enchantability, int burnTime,
            boolean isFood, int foodValue, float saturation, boolean canAlwaysEat,
            int maxStackSize, String repairIngredient,
            String harvestTier,
            List<String> enchants,
            List<String> tags,
            String nbtSummary
    ) {}

    public static ItemData from(ItemStack stack) {
        if (stack.isEmpty()) return null;
        Item item = stack.getItem();
        ResourceLocation rl = ForgeRegistries.ITEMS.getKey(item);

        String registryId  = rl != null ? rl.toString() : "unknown:unknown";
        String modId       = rl != null ? rl.getNamespace() : "unknown";
        String displayName = stack.getHoverName().getString();

        int maxDurability     = item.getMaxDamage(stack);
        int currentDurability = maxDurability > 0 ? maxDurability - stack.getDamageValue() : -1;
        int enchantability    = item.getEnchantmentValue(stack);
        int burnTime          = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);

        boolean isFood  = item.isEdible();
        int  foodValue  = 0;
        float saturation= 0f;
        boolean alwaysEat = false;
        if (isFood && item.getFoodProperties(stack, null) != null) {
            var fp = item.getFoodProperties(stack, null);
            foodValue  = fp.getNutrition();
            saturation = fp.getSaturationModifier();
            alwaysEat  = fp.canAlwaysEat();
        }

        int    maxStackSize     = item.getMaxStackSize(stack);
        String repairIngredient = item.isRepairable(stack) ? "Has repair recipe" : "None";

        // Harvest tier for tools
        String harvestTier = "";
        if (item instanceof TieredItem ti) harvestTier = ti.getTier().toString();
        else if (item instanceof DiggerItem) harvestTier = "N/A";

        // Active enchantments
        List<String> enchants = new ArrayList<>();
        EnchantmentHelper.getEnchantments(stack).forEach((enc, lvl) ->
            enchants.add(enc.getFullname(lvl).getString()));

        // Tags
        List<String> tags = new ArrayList<>();
        stack.getTags().map(t -> "#" + t.location()).forEach(tags::add);

        // NBT
        String nbtSummary = "None";
        if (stack.hasTag() && stack.getTag() != null)
            nbtSummary = BlockInfoProvider.compactNbt(stack.getTag(), 256);

        return new ItemData(
                displayName, registryId, modId,
                maxDurability, currentDurability,
                enchantability, burnTime,
                isFood, foodValue, saturation, alwaysEat,
                maxStackSize, repairIngredient,
                harvestTier, enchants, tags, nbtSummary
        );
    }
}
