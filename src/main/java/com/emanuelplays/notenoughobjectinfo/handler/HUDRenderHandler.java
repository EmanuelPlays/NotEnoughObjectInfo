package com.emanuelplays.notenoughobjectinfo.handler;

import com.emanuelplays.notenoughobjectinfo.config.NEOIConfig;
import com.emanuelplays.notenoughobjectinfo.render.RenderHelper;
import com.emanuelplays.notenoughobjectinfo.util.BlockInfoProvider;
import com.emanuelplays.notenoughobjectinfo.util.EntityInfoProvider;
import com.emanuelplays.notenoughobjectinfo.util.ItemInfoProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class HUDRenderHandler {

    // Runtime state toggled by key bindings
    public static boolean hudVisible = true;
    public static boolean showNbt    = false;

    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.CROSSHAIR.type()) return;
        if (!hudVisible) return;
        if (NEOIConfig.SPEC == null || !NEOIConfig.HUD_ENABLED.get()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        if (mc.options.renderDebug) return; // Don't show alongside F3

        GuiGraphics gui   = event.getGuiGraphics();
        int x = NEOIConfig.HUD_X.get();
        int y = NEOIConfig.HUD_Y.get();
        int alpha = NEOIConfig.HUD_BACKGROUND_ALPHA.get();
        boolean shadow = NEOIConfig.HUD_SHADOW.get();
        boolean drawBg = NEOIConfig.HUD_BACKGROUND.get();
        NEOIConfig.DisplayMode mode = NEOIConfig.DISPLAY_MODE.get();

        HitResult hit = mc.hitResult;

        if (hit instanceof BlockHitResult blockHit && blockHit.getType() != HitResult.Type.MISS) {
            y = renderBlockHUD(gui, mc, blockHit.getBlockPos(), x, y, alpha, shadow, drawBg, mode);
        } else if (hit instanceof EntityHitResult entityHit) {
            y = renderEntityHUD(gui, mc, entityHit.getEntity(), x, y, alpha, shadow, drawBg, mode);
        }

        // Always show held item info if configured
        if (NEOIConfig.SHOW_HELD_ITEM_INFO.get()) {
            renderHeldItemHUD(gui, mc, x, y + 4, alpha, shadow, drawBg, mode);
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    //  BLOCK HUD
    // ──────────────────────────────────────────────────────────────────────────
    private int renderBlockHUD(GuiGraphics gui, Minecraft mc, BlockPos pos,
                                int x, int y, int alpha, boolean shadow, boolean bg,
                                NEOIConfig.DisplayMode mode) {
        Level level = mc.level;
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) return y;

        BlockInfoProvider.BlockData data = BlockInfoProvider.fromState(level, pos, state);

        List<String> lines = new ArrayList<>();
        boolean verbose = mode == NEOIConfig.DisplayMode.VERBOSE;
        boolean compact = mode == NEOIConfig.DisplayMode.COMPACT;

        if (NEOIConfig.SHOW_BLOCK_NAME.get())
            lines.add("§7Name: §f" + data.displayName());

        if (NEOIConfig.SHOW_BLOCK_ID.get())
            lines.add("§7ID: §b" + data.registryId());

        if (NEOIConfig.SHOW_BLOCK_MOD.get())
            lines.add("§7Mod: §d" + formatModName(data.modId()));

        if (!compact) {
            if (NEOIConfig.SHOW_BLOCK_HARDNESS.get()) {
                String hardStr = data.hardness() < 0 ? "§4Unbreakable" : "§e" + RenderHelper.fmt(data.hardness());
                lines.add("§7Hardness: " + hardStr);
            }

            if (NEOIConfig.SHOW_BLOCK_RESISTANCE.get())
                lines.add("§7Blast Resistance: §e" + RenderHelper.fmt(data.blastResistance()));

            if (NEOIConfig.SHOW_BLOCK_TOOL.get())
                lines.add("§7Harvest Tool: §a" + data.harvestTool());
        }

        if (NEOIConfig.SHOW_BLOCK_LIGHT.get())
            lines.add("§7Light: §e" + data.blockLight() + " §7(sky §b" + data.skyLight() + "§7)");

        if (NEOIConfig.SHOW_BLOCK_REDSTONE.get() && data.redstonePower() > 0)
            lines.add("§7Redstone Power: §c" + data.redstonePower());

        if (!compact) {
            if (NEOIConfig.SHOW_BLOCK_FLAMMABLE.get()) {
                if (data.isFlammable()) {
                    lines.add("§7Flammable: §cYes §7(Spread: §e" + data.fireSpreadSpeed() +
                            "§7, Enc: §e" + data.flammability() + "§7)");
                } else {
                    lines.add("§7Flammable: §aNo");
                }
            }

            if (verbose && NEOIConfig.SHOW_BLOCK_REPLACEABLE.get())
                lines.add("§7Replaceable: " + boolColour(data.isReplaceable()));

            if (verbose && NEOIConfig.SHOW_BLOCK_SOLID.get())
                lines.add("§7Solid: " + boolColour(data.isSolid()));

            if (NEOIConfig.SHOW_BLOCK_FRICTION.get())
                lines.add("§7Friction: §e" + RenderHelper.fmt(data.friction()));

            if (verbose && NEOIConfig.SHOW_BLOCK_SPEED_FACTOR.get())
                lines.add("§7Speed Factor: §e" + RenderHelper.fmt(data.speedFactor()));

            if (verbose && NEOIConfig.SHOW_BLOCK_JUMP_FACTOR.get())
                lines.add("§7Jump Factor: §e" + RenderHelper.fmt(data.jumpFactor()));

            if (NEOIConfig.SHOW_BLOCK_STATE.get() && !data.blockStateProperties().isEmpty()) {
                lines.add("§7State: §8" + String.join(", ", data.blockStateProperties()));
            }
        }

        if (verbose && NEOIConfig.SHOW_BLOCK_TAGS.get() && !data.tags().isEmpty()) {
            lines.add("§7Tags §8(" + data.tags().size() + ")§7:");
            for (String tag : data.tags()) {
                lines.add("  §8" + tag);
            }
        } else if (NEOIConfig.SHOW_BLOCK_TAGS.get() && !data.tags().isEmpty()) {
            lines.add("§7Tags: §8" + data.tags().size() + " tag(s)");
        }

        if (verbose && NEOIConfig.SHOW_BLOCK_MAP_COLOR.get())
            lines.add("§7Map Color: §f" + data.mapColor());

        if (NEOIConfig.SHOW_TILE_ENTITY_DATA.get() && data.hasTileEntity()) {
            lines.add("§7Block Entity: §a" + data.tileEntityType());
            if ((verbose || showNbt) && !data.compactNbt().isEmpty()) {
                lines.add("§7NBT: §8" + truncate(data.compactNbt(), 60));
            }
        }

        // Position
        lines.add("§7Pos: §f" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());

        return RenderHelper.drawInfoPanel(
                gui, mc.font, x, y, lines,
                "§6§l✦ BLOCK INFO", bg ? alpha : 0, shadow
        );
    }

    // ──────────────────────────────────────────────────────────────────────────
    //  ENTITY HUD
    // ──────────────────────────────────────────────────────────────────────────
    private int renderEntityHUD(GuiGraphics gui, Minecraft mc, Entity entity,
                                 int x, int y, int alpha, boolean shadow, boolean bg,
                                 NEOIConfig.DisplayMode mode) {
        EntityInfoProvider.EntityData data = EntityInfoProvider.from(entity);

        List<String> lines = new ArrayList<>();
        boolean verbose = mode == NEOIConfig.DisplayMode.VERBOSE;
        boolean compact = mode == NEOIConfig.DisplayMode.COMPACT;

        if (NEOIConfig.SHOW_ENTITY_NAME.get())
            lines.add("§7Name: §f" + data.displayName());

        if (NEOIConfig.SHOW_ENTITY_ID.get())
            lines.add("§7ID: §b" + data.registryId());

        if (NEOIConfig.SHOW_ENTITY_MOD.get())
            lines.add("§7Mod: §d" + formatModName(data.modId()));

        if (NEOIConfig.SHOW_ENTITY_TYPE.get())
            lines.add("§7Category: §f" + data.categoryName());

        if (data.isLiving()) {
            if (NEOIConfig.SHOW_ENTITY_HEALTH.get()) {
                String hpColor = RenderHelper.fractionColour(data.health(), data.maxHealth());
                lines.add("§7Health: " + hpColor + RenderHelper.fmt(data.health())
                        + " §7/ §c" + RenderHelper.fmt(data.maxHealth()));
            }

            if (!compact) {
                if (NEOIConfig.SHOW_ENTITY_ARMOR.get())
                    lines.add("§7Armor: §7" + data.armor() + " ✦");

                if (NEOIConfig.SHOW_ENTITY_SPEED.get())
                    lines.add("§7Speed: §a" + RenderHelper.fmt(data.speed()));

                if (NEOIConfig.SHOW_ENTITY_ATTACK_DAMAGE.get() && data.attackDamage() > 0)
                    lines.add("§7Attack: §c" + RenderHelper.fmt(data.attackDamage()) + " ⚔");

                if (verbose && data.knockbackResistance() > 0)
                    lines.add("§7KB Resist: §7" + RenderHelper.fmt((float) data.knockbackResistance() * 100) + "%");
            }

            if (NEOIConfig.SHOW_ENTITY_EFFECTS.get() && !data.activeEffects().isEmpty()) {
                lines.add("§7Effects §8(" + data.activeEffects().size() + ")§7:");
                for (String eff : data.activeEffects()) {
                    lines.add("  §5" + eff);
                }
            }
        }

        if (entity.isOnFire())
            lines.add("§c🔥 On Fire!");

        if (!compact) {
            if (NEOIConfig.SHOW_ENTITY_POSITION.get())
                lines.add("§7Pos: §f" + data.positionStr());

            if (verbose && NEOIConfig.SHOW_ENTITY_DIMENSION.get())
                lines.add("§7Dim: §8" + data.dimensionId());
        }

        if (verbose && NEOIConfig.SHOW_ENTITY_TAGS.get() && !data.tags().isEmpty()) {
            lines.add("§7Tags: §8" + data.tags().size() + " tag(s)");
        }

        if (data.hasPassengers())
            lines.add("§7Passengers: §e" + entity.getPassengers().size());

        if (!data.vehicle().isEmpty())
            lines.add("§7Vehicle: §f" + data.vehicle());

        if ((verbose || showNbt) && NEOIConfig.SHOW_ENTITY_NBT.get()) {
            lines.add("§7NBT: §8" + truncate(data.compactNbt(), 60));
        }

        return RenderHelper.drawInfoPanel(
                gui, mc.font, x, y, lines,
                "§a§l✦ ENTITY INFO", drawBg(bg, alpha), shadow
        );
    }

    // ──────────────────────────────────────────────────────────────────────────
    //  HELD ITEM HUD
    // ──────────────────────────────────────────────────────────────────────────
    private void renderHeldItemHUD(GuiGraphics gui, Minecraft mc,
                                    int x, int y, int alpha, boolean shadow, boolean bg,
                                    NEOIConfig.DisplayMode mode) {
        Player player = mc.player;
        if (player == null) return;
        ItemStack held = player.getMainHandItem();
        if (held.isEmpty()) {
            held = player.getOffhandItem();
            if (held.isEmpty()) return;
        }

        ItemInfoProvider.ItemData data = ItemInfoProvider.from(held);
        if (data == null) return;

        List<String> lines = new ArrayList<>();
        boolean verbose = mode == NEOIConfig.DisplayMode.VERBOSE;
        boolean compact = mode == NEOIConfig.DisplayMode.COMPACT;

        if (NEOIConfig.SHOW_HELD_ITEM_ID.get())
            lines.add("§7ID: §b" + data.registryId());

        if (NEOIConfig.SHOW_HELD_ITEM_MOD.get())
            lines.add("§7Mod: §d" + formatModName(data.modId()));

        if (NEOIConfig.SHOW_HELD_ITEM_DURABILITY.get() && data.maxDurability() > 0) {
            float frac = (float) data.currentDurability() / data.maxDurability();
            String col = RenderHelper.fractionColour(data.currentDurability(), data.maxDurability());
            lines.add("§7Durability: " + col + data.currentDurability() + " §7/ " + data.maxDurability());
        }

        if (!compact) {
            if (NEOIConfig.SHOW_HELD_ITEM_ENCHANTABILITY.get() && data.enchantability() > 0)
                lines.add("§7Enchantability: §e" + data.enchantability());

            if (NEOIConfig.SHOW_HELD_ITEM_BURN_TIME.get() && data.burnTime() > 0)
                lines.add("§7Burn Time: §6" + data.burnTime() + " ticks ("
                        + RenderHelper.fmt(data.burnTime() / 20f) + "s)");

            if (NEOIConfig.SHOW_HELD_ITEM_FOOD.get() && data.isFood())
                lines.add("§7Food: §a+" + data.foodValue() + " 🍖  Sat: §e"
                        + RenderHelper.fmt(data.saturation() * 2));

            if (NEOIConfig.SHOW_HELD_ITEM_MAX_STACK.get())
                lines.add("§7Max Stack: §f" + data.maxStackSize());
        }

        if (verbose && NEOIConfig.SHOW_HELD_ITEM_TAGS.get() && !data.tags().isEmpty()) {
            lines.add("§7Tags: §8" + data.tags().size() + " tag(s)");
        }

        if (!data.nbtSummary().equals("None") && (verbose || showNbt)) {
            lines.add("§7NBT: §8" + truncate(data.nbtSummary(), 60));
        }

        if (lines.isEmpty()) return;

        // Place item HUD below block/entity HUD
        RenderHelper.drawInfoPanel(
                gui, mc.font, x, y, lines,
                "§e§l✦ HELD ITEM: §f" + data.displayName(),
                drawBg(bg, alpha), shadow
        );
    }

    // ──────────────────────────────────────────────────────────────────────────
    //  HELPERS
    // ──────────────────────────────────────────────────────────────────────────
    private static String boolColour(boolean v) {
        return v ? "§aYes" : "§cNo";
    }

    private static String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max) + "…";
    }

    private static String formatModName(String modId) {
        if (modId.equals("minecraft")) return "§fMinecraft";
        // Capitalise first letter
        return modId.substring(0, 1).toUpperCase() + modId.substring(1);
    }

    private static int drawBg(boolean bg, int alpha) {
        return bg ? alpha : 0;
    }
}
