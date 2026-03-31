package com.emanuelplays.notenoughobjectinfo.handler;

import com.emanuelplays.notenoughobjectinfo.config.NEOIConfig;
import com.emanuelplays.notenoughobjectinfo.render.RenderHelper;
import com.emanuelplays.notenoughobjectinfo.util.ItemInfoProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class TooltipHandler {

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (NEOIConfig.SPEC == null) return;
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;

        ItemInfoProvider.ItemData d = ItemInfoProvider.from(stack);
        if (d == null) return;

        List<Component> tip = event.getToolTip();

        // Separator
        tip.add(Component.literal("§8────────────────────"));

        // Registry ID
        if (NEOIConfig.TOOLTIP_SHOW_ID.get())
            tip.add(Component.literal("§8ID:          §7" + d.registryId()));

        // Mod
        if (NEOIConfig.TOOLTIP_SHOW_MOD.get())
            tip.add(Component.literal("§8Mod:         §5" + RenderHelper.modName(d.modId())));

        // Durability
        if (NEOIConfig.TOOLTIP_SHOW_DURABILITY.get() && d.maxDurability() > 0) {
            float pct = (float) d.currentDurability() / d.maxDurability() * 100f;
            String col = pct > 60 ? "§a" : pct > 30 ? "§e" : "§c";
            tip.add(Component.literal("§8Durability:  " + col + d.currentDurability()
                    + " §7/ §f" + d.maxDurability()
                    + " §8(" + String.format("%.0f%%", pct) + ")"));
        }

        // Enchantability
        if (NEOIConfig.TOOLTIP_SHOW_ENCHANTABILITY.get() && d.enchantability() > 0)
            tip.add(Component.literal("§8Enchantability: §9" + d.enchantability()));

        // Harvest tier
        if (NEOIConfig.TOOLTIP_SHOW_HARVEST_TIER.get() && !d.harvestTier().isEmpty())
            tip.add(Component.literal("§8Harvest Tier: §a" + d.harvestTier()));

        // Max stack
        if (NEOIConfig.TOOLTIP_SHOW_MAX_STACK.get())
            tip.add(Component.literal("§8Max Stack:   §7" + d.maxStackSize()));

        // Burn time
        if (NEOIConfig.TOOLTIP_SHOW_BURN_TIME.get() && d.burnTime() > 0)
            tip.add(Component.literal("§8Fuel:        §6"
                    + d.burnTime() + "t §8(" + RenderHelper.ticks(d.burnTime()) + ")"));

        // Food
        if (NEOIConfig.TOOLTIP_SHOW_FOOD.get() && d.isFood()) {
            tip.add(Component.literal("§8Food:        §a+" + d.foodValue()
                    + " 🍖  §8Saturation: §e"
                    + RenderHelper.fmt(d.saturation() * 2)));
            if (d.canAlwaysEat())
                tip.add(Component.literal("§8              §7Can always eat"));
        }

        // Repair
        if (NEOIConfig.TOOLTIP_SHOW_REPAIR_ITEM.get() && d.maxDurability() > 0)
            tip.add(Component.literal("§8Repair:      §7" + d.repairIngredient()));

        // NBT summary
        if (NEOIConfig.TOOLTIP_SHOW_NBT.get() && !"None".equals(d.nbtSummary()))
            tip.add(Component.literal("§8NBT: §8" + RenderHelper.trunc(d.nbtSummary(), 80)));

        // Tags
        if (NEOIConfig.TOOLTIP_SHOW_TAGS.get() && !d.tags().isEmpty()) {
            tip.add(Component.literal("§8Tags §7(" + d.tags().size() + "):"));
            d.tags().stream().limit(5)
                    .forEach(t -> tip.add(Component.literal("§8  " + t)));
            if (d.tags().size() > 5)
                tip.add(Component.literal("§8  … §7+" + (d.tags().size() - 5) + " more"));
        }
    }
}
