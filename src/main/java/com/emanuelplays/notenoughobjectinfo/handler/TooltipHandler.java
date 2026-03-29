package com.emanuelplays.notenoughobjectinfo.handler;

import com.emanuelplays.notenoughobjectinfo.config.NEOIConfig;
import com.emanuelplays.notenoughobjectinfo.util.ItemInfoProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

        ItemInfoProvider.ItemData data = ItemInfoProvider.from(stack);
        if (data == null) return;

        List<Component> tooltip = event.getToolTip();

        // ── Registry ID ──────────────────────────────────────────────
        if (NEOIConfig.TOOLTIP_SHOW_ID.get()) {
            tooltip.add(Component.empty());
            tooltip.add(Component.literal("§8ID: §7" + data.registryId()));
        }

        // ── Mod ──────────────────────────────────────────────────────
        if (NEOIConfig.TOOLTIP_SHOW_MOD.get()) {
            tooltip.add(Component.literal("§8Mod: §5" + formatMod(data.modId())));
        }

        // ── Durability ───────────────────────────────────────────────
        if (NEOIConfig.TOOLTIP_SHOW_DURABILITY.get() && data.maxDurability() > 0) {
            float pct = (float) data.currentDurability() / data.maxDurability() * 100f;
            String col = pct > 60 ? "§a" : pct > 30 ? "§e" : "§c";
            tooltip.add(Component.literal("§8Durability: " + col
                    + data.currentDurability() + " §7/ §f" + data.maxDurability()
                    + " §8(" + String.format("%.0f", pct) + "%)"));
        }

        // ── Enchantability ───────────────────────────────────────────
        if (NEOIConfig.TOOLTIP_SHOW_ENCHANTABILITY.get() && data.enchantability() > 0) {
            tooltip.add(Component.literal("§8Enchantability: §9" + data.enchantability()));
        }

        // ── Max Stack ────────────────────────────────────────────────
        if (NEOIConfig.TOOLTIP_SHOW_MAX_STACK.get()) {
            tooltip.add(Component.literal("§8Max Stack: §7" + data.maxStackSize()));
        }

        // ── Burn Time ────────────────────────────────────────────────
        if (NEOIConfig.TOOLTIP_SHOW_BURN_TIME.get() && data.burnTime() > 0) {
            float seconds = data.burnTime() / 20f;
            tooltip.add(Component.literal("§8Fuel: §6" + data.burnTime()
                    + " ticks §8(" + formatSeconds(seconds) + ")"));
        }

        // ── Food ─────────────────────────────────────────────────────
        if (NEOIConfig.TOOLTIP_SHOW_FOOD.get() && data.isFood()) {
            tooltip.add(Component.literal("§8Food: §a+" + data.foodValue()
                    + " 🍖  §8Saturation: §e"
                    + String.format("%.1f", data.saturation() * 2)));
            if (data.canAlwaysEat())
                tooltip.add(Component.literal("§8  §7Can always eat"));
        }

        // ── Repair ───────────────────────────────────────────────────
        if (NEOIConfig.TOOLTIP_SHOW_REPAIR_ITEM.get() && data.maxDurability() > 0) {
            tooltip.add(Component.literal("§8Repair: §7" + data.repairIngredient()));
        }

        // ── Tags ─────────────────────────────────────────────────────
        if (NEOIConfig.TOOLTIP_SHOW_TAGS.get() && !data.tags().isEmpty()) {
            tooltip.add(Component.literal("§8Tags §7(" + data.tags().size() + "):"));
            // Only show first 5 tags to keep tooltip manageable
            int shown = 0;
            for (String tag : data.tags()) {
                if (shown++ >= 5) {
                    tooltip.add(Component.literal("§8  ... §7(" + (data.tags().size() - 5) + " more)"));
                    break;
                }
                tooltip.add(Component.literal("§8  " + tag));
            }
        }

        // ── NBT ──────────────────────────────────────────────────────
        if (NEOIConfig.TOOLTIP_SHOW_NBT.get() && !data.nbtSummary().equals("None")) {
            tooltip.add(Component.literal("§8NBT: §8" + truncate(data.nbtSummary(), 80)));
        }
    }

    private static String formatMod(String modId) {
        if (modId.equals("minecraft")) return "Minecraft";
        return modId.substring(0, 1).toUpperCase() + modId.substring(1);
    }

    private static String formatSeconds(float s) {
        if (s >= 60) return String.format("%.0fm %.0fs", Math.floor(s / 60), s % 60);
        return String.format("%.1fs", s);
    }

    private static String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max) + "…";
    }
}
