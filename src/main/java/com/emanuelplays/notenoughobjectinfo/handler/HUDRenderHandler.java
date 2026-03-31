package com.emanuelplays.notenoughobjectinfo.handler;

import com.emanuelplays.notenoughobjectinfo.config.NEOIConfig;
import com.emanuelplays.notenoughobjectinfo.render.RenderHelper;
import com.emanuelplays.notenoughobjectinfo.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class HUDRenderHandler {

    // ── Runtime toggle state ──────────────────────────────────────────
    public static boolean hudVisible    = true;
    public static boolean showNbt       = false;
    public static boolean showBiome     = false;
    public static boolean showCoords    = false;
    public static boolean showPlayer    = false;
    public static boolean showFluid     = true;

    // Panel header colours (ARGB without alpha — alpha added in drawPanel)
    private static final int COL_BLOCK  = 0xFFAA00;
    private static final int COL_ENTITY = 0x00CCAA;
    private static final int COL_ITEM   = 0xFFDD44;
    private static final int COL_FLUID  = 0x44AAFF;
    private static final int COL_BIOME  = 0xCC88FF;
    private static final int COL_COORD  = 0xAAAAAA;
    private static final int COL_PLAYER = 0xFF6655;

    @SubscribeEvent
    public void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.CROSSHAIR.type()) return;
        if (!hudVisible) return;
        if (NEOIConfig.SPEC == null || !NEOIConfig.HUD_ENABLED.get()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        if (mc.options.renderDebug) return; // hide behind F3

        GuiGraphics gui    = event.getGuiGraphics();
        int  x             = NEOIConfig.HUD_X.get();
        int  y             = NEOIConfig.HUD_Y.get();
        int  alpha         = NEOIConfig.HUD_BACKGROUND_ALPHA.get();
        boolean shadow     = NEOIConfig.HUD_SHADOW.get();
        boolean drawBg     = NEOIConfig.HUD_BACKGROUND.get();
        int  gap           = NEOIConfig.HUD_PANEL_GAP.get();
        NEOIConfig.DisplayMode mode = NEOIConfig.DISPLAY_MODE.get();

        HitResult hit = mc.hitResult;

        // ── Block / Fluid panel ──────────────────────────────────────
        if (hit instanceof BlockHitResult bhr && bhr.getType() != HitResult.Type.MISS) {
            BlockPos   pos   = bhr.getBlockPos();
            Level      level = mc.level;
            BlockState state = level.getBlockState(pos);

            if (!state.getFluidState().isEmpty() && showFluid && NEOIConfig.SHOW_FLUID_PANEL.get()) {
                FluidInfoProvider.FluidData fd = FluidInfoProvider.fromState(level, pos, state);
                if (fd != null) {
                    y = renderFluidPanel(gui, mc, fd, x, y, alpha, shadow, drawBg, mode) + gap;
                }
            } else if (!state.isAir()) {
                BlockInfoProvider.BlockData bd = BlockInfoProvider.fromState(level, pos, state);
                y = renderBlockPanel(gui, mc, bd, x, y, alpha, shadow, drawBg, mode) + gap;
            }
        }

        // ── Entity panel ─────────────────────────────────────────────
        if (hit instanceof EntityHitResult ehr) {
            EntityInfoProvider.EntityData ed = EntityInfoProvider.from(ehr.getEntity());
            y = renderEntityPanel(gui, mc, ed, x, y, alpha, shadow, drawBg, mode) + gap;
        }

        // ── Held item panel ──────────────────────────────────────────
        if (NEOIConfig.SHOW_HELD_ITEM_INFO.get()) {
            int nextY = renderHeldItemPanel(gui, mc, x, y, alpha, shadow, drawBg, mode);
            if (nextY > y) y = nextY + gap;
        }

        // ── Biome panel ──────────────────────────────────────────────
        if (showBiome && NEOIConfig.SHOW_BIOME_PANEL.get()) {
            BlockPos fp = mc.player.blockPosition();
            BiomeInfoProvider.BiomeData bd = BiomeInfoProvider.fromPos(mc.level, fp);
            if (bd != null) y = renderBiomePanel(gui, mc, bd, x, y, alpha, shadow, drawBg, mode) + gap;
        }

        // ── Coordinate overlay ───────────────────────────────────────
        if (showCoords && NEOIConfig.SHOW_COORD_PANEL.get()) {
            int cx = NEOIConfig.COORD_X.get();
            int cy = NEOIConfig.COORD_Y.get();
            CoordinateInfoProvider.CoordData cd = CoordinateInfoProvider.from(mc.player);
            renderCoordPanel(gui, mc, cd, cx, cy, alpha, shadow, drawBg);
        }

        // ── Player stats panel ───────────────────────────────────────
        if (showPlayer && NEOIConfig.SHOW_PLAYER_PANEL.get()) {
            PlayerStatsProvider.PlayerStats ps = PlayerStatsProvider.from(mc.player);
            // Place to the right of the main HUD
            int px = x + 200;
            renderPlayerPanel(gui, mc, ps, px, NEOIConfig.HUD_Y.get(), alpha, shadow, drawBg, mode);
        }
    }

    // ─────────────────────────────────────────────────────────────────
    //  BLOCK PANEL
    // ─────────────────────────────────────────────────────────────────
    private int renderBlockPanel(GuiGraphics gui, Minecraft mc,
                                  BlockInfoProvider.BlockData d,
                                  int x, int y, int alpha, boolean shadow,
                                  boolean bg, NEOIConfig.DisplayMode mode) {
        List<String> lines = new ArrayList<>();
        boolean norm    = mode != NEOIConfig.DisplayMode.COMPACT;
        boolean verbose = mode == NEOIConfig.DisplayMode.VERBOSE;

        if (NEOIConfig.SHOW_BLOCK_NAME.get())     lines.add("§7Name:       §f" + d.displayName());
        if (NEOIConfig.SHOW_BLOCK_ID.get())       lines.add("§7ID:         §b" + d.registryId());
        if (NEOIConfig.SHOW_BLOCK_MOD.get())      lines.add("§7Mod:        §d" + RenderHelper.modName(d.modId()));

        if (norm) {
            if (NEOIConfig.SHOW_BLOCK_HARDNESS.get()) {
                String hStr = d.hardness() < 0 ? "§4Unbreakable" : "§e" + RenderHelper.fmt(d.hardness());
                lines.add("§7Hardness:   " + hStr);
            }
            if (NEOIConfig.SHOW_BLOCK_RESISTANCE.get())
                lines.add("§7Resistance: §e" + RenderHelper.fmt(d.blastResistance()));
            if (NEOIConfig.SHOW_BLOCK_TOOL.get())
                lines.add("§7Tool:       §a" + d.harvestTool());
        }

        if (NEOIConfig.SHOW_BLOCK_LIGHT.get())
            lines.add("§7Light:      §e" + d.blockLight() + " §7/ sky §b" + d.skyLight()
                    + (d.emittedLight() > 0 ? " §7emit §6" + d.emittedLight() : ""));

        if (NEOIConfig.SHOW_BLOCK_REDSTONE.get() && d.redstonePower() > 0)
            lines.add("§7Redstone:   §c" + d.redstonePower());

        if (norm && NEOIConfig.SHOW_BLOCK_FLAMMABLE.get()) {
            if (d.isFlammable())
                lines.add("§7Flammable:  §cYes §7(enc §e" + d.flammability() + "§7, spd §e" + d.fireSpreadSpeed() + "§7)");
            else
                lines.add("§7Flammable:  §aNo");
        }

        if (norm && NEOIConfig.SHOW_BLOCK_FRICTION.get())
            lines.add("§7Friction:   §e" + RenderHelper.fmt(d.friction()));

        if (verbose) {
            if (NEOIConfig.SHOW_BLOCK_SPEED_FACTOR.get())
                lines.add("§7SpeedFactor:§e" + RenderHelper.fmt(d.speedFactor()));
            if (NEOIConfig.SHOW_BLOCK_JUMP_FACTOR.get())
                lines.add("§7JumpFactor: §e" + RenderHelper.fmt(d.jumpFactor()));
            if (NEOIConfig.SHOW_BLOCK_REPLACEABLE.get())
                lines.add("§7Replaceable:"+  RenderHelper.bool(d.isReplaceable()));
            if (NEOIConfig.SHOW_BLOCK_SOLID.get())
                lines.add("§7Solid:      "+ RenderHelper.bool(d.isSolid()));
            if (NEOIConfig.SHOW_BLOCK_PUSH_REACTION.get())
                lines.add("§7PistonPush: §7" + d.pushReaction());
            if (NEOIConfig.SHOW_BLOCK_MAP_COLOR.get())
                lines.add("§7MapColor:   §7" + d.mapColor());
        }

        if (norm && NEOIConfig.SHOW_BLOCK_STATE.get() && !d.blockStateProperties().isEmpty()) {
            if (verbose) {
                lines.add("§7State:");
                d.blockStateProperties().forEach(p -> lines.add("  §8" + p));
            } else {
                lines.add("§7State: §8" + String.join(", ", d.blockStateProperties()));
            }
        }

        if (NEOIConfig.SHOW_BLOCK_TAGS.get() && !d.tags().isEmpty()) {
            int max = NEOIConfig.HUD_MAX_TAGS.get();
            if (verbose) {
                lines.add("§7Tags §8(" + d.tags().size() + "):");
                int shown = 0;
                for (String t : d.tags()) {
                    if (max > 0 && shown++ >= max) { lines.add("  §8… +" + (d.tags().size() - max) + " more"); break; }
                    lines.add("  §8" + t);
                }
            } else {
                lines.add("§7Tags: §8" + d.tags().size() + " tag(s)");
            }
        }

        if (NEOIConfig.SHOW_TILE_ENTITY_DATA.get() && d.hasTileEntity()) {
            lines.add("§7BlockEntity: §a" + d.tileEntityType());
            if ((verbose || showNbt) && !d.compactNbt().isEmpty())
                lines.add("§7NBT: §8" + RenderHelper.trunc(d.compactNbt(), NEOIConfig.HUD_NBT_MAX_CHARS.get()));
        }

        if (NEOIConfig.SHOW_BLOCK_POS.get()) {
            // Shown in all modes
        }

        return RenderHelper.drawPanel(gui, mc.font, x, y, lines,
                "§6§l✦ BLOCK INFO", COL_BLOCK, bg ? alpha : 0, shadow);
    }

    // ─────────────────────────────────────────────────────────────────
    //  ENTITY PANEL
    // ─────────────────────────────────────────────────────────────────
    private int renderEntityPanel(GuiGraphics gui, Minecraft mc,
                                   EntityInfoProvider.EntityData d,
                                   int x, int y, int alpha, boolean shadow,
                                   boolean bg, NEOIConfig.DisplayMode mode) {
        List<String> lines = new ArrayList<>();
        boolean norm    = mode != NEOIConfig.DisplayMode.COMPACT;
        boolean verbose = mode == NEOIConfig.DisplayMode.VERBOSE;

        if (NEOIConfig.SHOW_ENTITY_NAME.get())   lines.add("§7Name:   §f" + d.displayName());
        if (NEOIConfig.SHOW_ENTITY_ID.get())     lines.add("§7ID:     §b" + d.registryId());
        if (NEOIConfig.SHOW_ENTITY_MOD.get())    lines.add("§7Mod:    §d" + RenderHelper.modName(d.modId()));
        if (NEOIConfig.SHOW_ENTITY_TYPE.get())   lines.add("§7Cat:    §7" + d.categoryName());

        if (d.isBaby() && NEOIConfig.SHOW_ENTITY_BABY.get())
            lines.add("§e  ♦ Baby");

        if (d.isLiving()) {
            if (NEOIConfig.SHOW_ENTITY_HEALTH.get()) {
                String col = RenderHelper.fractionCode(d.health() / Math.max(d.maxHealth(), 1));
                lines.add("§7Health: " + col + RenderHelper.fmt(d.health())
                        + " §7/ §c" + RenderHelper.fmt(d.maxHealth())
                        + " §8(" + RenderHelper.pct(d.health(), d.maxHealth()) + ")");
            }
            if (norm) {
                if (NEOIConfig.SHOW_ENTITY_ARMOR.get())
                    lines.add("§7Armor:  §7" + d.armor() + " ✦"
                            + (d.armorToughness() > 0 ? " §8(tough " + RenderHelper.fmt(d.armorToughness()) + ")" : ""));
                if (NEOIConfig.SHOW_ENTITY_SPEED.get())
                    lines.add("§7Speed:  §a" + RenderHelper.fmt(d.speed()));
                if (NEOIConfig.SHOW_ENTITY_ATTACK_DAMAGE.get() && d.attackDamage() > 0)
                    lines.add("§7Attack: §c" + RenderHelper.fmt(d.attackDamage()) + " ⚔");
            }
            if (verbose) {
                if (NEOIConfig.SHOW_ENTITY_ATTACK_SPEED.get() && d.attackSpeed() > 0)
                    lines.add("§7AtkSpd: §e" + RenderHelper.fmt(d.attackSpeed()));
                if (NEOIConfig.SHOW_ENTITY_FOLLOW_RANGE.get() && d.followRange() > 0)
                    lines.add("§7Range:  §e" + RenderHelper.fmt(d.followRange()));
                if (NEOIConfig.SHOW_ENTITY_KB_RESISTANCE.get() && d.knockbackResistance() > 0)
                    lines.add("§7KBRes:  §7" + RenderHelper.fmt((float)(d.knockbackResistance() * 100)) + "%");
            }
            if (NEOIConfig.SHOW_ENTITY_EFFECTS.get() && !d.activeEffects().isEmpty()) {
                lines.add("§7Effects §8(" + d.activeEffects().size() + "):");
                d.activeEffects().forEach(e -> lines.add("  " + e));
            }
        }

        if (NEOIConfig.SHOW_ENTITY_ON_FIRE.get() && d.isBurning())  lines.add("§c🔥 On Fire!");
        if (d.isInvisible())  lines.add("§8  [Invisible]");
        if (d.isSilent())     lines.add("§8  [Silent]");

        if (norm) {
            if (NEOIConfig.SHOW_ENTITY_POSITION.get())
                lines.add("§7Pos:    §f" + d.positionStr());
            if (verbose && NEOIConfig.SHOW_ENTITY_DIMENSION.get())
                lines.add("§7Dim:    §8" + d.dimensionId());
            if (NEOIConfig.SHOW_ENTITY_PASSENGERS.get() && d.passengerCount() > 0)
                lines.add("§7Passengers: §e" + d.passengerCount());
            if (NEOIConfig.SHOW_ENTITY_VEHICLE.get() && !d.vehicle().isEmpty())
                lines.add("§7Vehicle: §f" + d.vehicle());
        }

        if (verbose && NEOIConfig.SHOW_ENTITY_TAGS.get() && !d.tags().isEmpty())
            lines.add("§7Tags: §8" + d.tags().size() + " tag(s)");

        if (verbose && NEOIConfig.SHOW_ENTITY_UUID.get())
            lines.add("§7UUID: §8" + RenderHelper.trunc(d.uuid(), 20) + "…");

        if ((verbose || showNbt) && NEOIConfig.SHOW_ENTITY_NBT.get())
            lines.add("§7NBT: §8" + RenderHelper.trunc(d.compactNbt(), NEOIConfig.HUD_NBT_MAX_CHARS.get()));

        return RenderHelper.drawPanel(gui, mc.font, x, y, lines,
                "§a§l✦ ENTITY INFO", COL_ENTITY, bg ? alpha : 0, shadow);
    }

    // ─────────────────────────────────────────────────────────────────
    //  FLUID PANEL
    // ─────────────────────────────────────────────────────────────────
    private int renderFluidPanel(GuiGraphics gui, Minecraft mc,
                                  FluidInfoProvider.FluidData d,
                                  int x, int y, int alpha, boolean shadow,
                                  boolean bg, NEOIConfig.DisplayMode mode) {
        List<String> lines = new ArrayList<>();
        boolean norm    = mode != NEOIConfig.DisplayMode.COMPACT;
        boolean verbose = mode == NEOIConfig.DisplayMode.VERBOSE;

        if (NEOIConfig.SHOW_FLUID_NAME.get())     lines.add("§7Name:   §f" + d.displayName());
        if (NEOIConfig.SHOW_FLUID_ID.get())       lines.add("§7ID:     §b" + d.registryId());
        if (NEOIConfig.SHOW_FLUID_MOD.get())      lines.add("§7Mod:    §d" + RenderHelper.modName(d.modId()));

        if (NEOIConfig.SHOW_FLUID_IS_SOURCE.get())
            lines.add("§7Source: " + RenderHelper.bool(d.isSource()));
        if (NEOIConfig.SHOW_FLUID_LEVEL.get() && !d.isSource())
            lines.add("§7Level:  §e" + d.level() + "§7/8");

        if (norm) {
            if (NEOIConfig.SHOW_FLUID_DENSITY.get())
                lines.add("§7Density:  §e" + d.density());
            if (NEOIConfig.SHOW_FLUID_VISCOSITY.get())
                lines.add("§7Viscosity:§e" + d.viscosity());
            if (NEOIConfig.SHOW_FLUID_TEMPERATURE.get())
                lines.add("§7Temp:     §e" + d.temperature() + "K");

        }

        if (d.isLava())  lines.add("§c🌋 Lava");
        if (d.isWater()) lines.add("§b🌊 Water");

        return RenderHelper.drawPanel(gui, mc.font, x, y, lines,
                "§b§l✦ FLUID INFO", COL_FLUID, bg ? alpha : 0, shadow);
    }

    // ─────────────────────────────────────────────────────────────────
    //  HELD ITEM PANEL
    // ─────────────────────────────────────────────────────────────────
    private int renderHeldItemPanel(GuiGraphics gui, Minecraft mc,
                                     int x, int y, int alpha, boolean shadow,
                                     boolean bg, NEOIConfig.DisplayMode mode) {
        LocalPlayer player = mc.player;
        if (player == null) return y;
        ItemStack held = player.getMainHandItem();
        if (held.isEmpty()) held = player.getOffhandItem();
        if (held.isEmpty()) return y;

        ItemInfoProvider.ItemData d = ItemInfoProvider.from(held);
        if (d == null) return y;

        List<String> lines = new ArrayList<>();
        boolean norm    = mode != NEOIConfig.DisplayMode.COMPACT;
        boolean verbose = mode == NEOIConfig.DisplayMode.VERBOSE;

        if (NEOIConfig.SHOW_HELD_ITEM_ID.get())   lines.add("§7ID:   §b" + d.registryId());
        if (NEOIConfig.SHOW_HELD_ITEM_MOD.get())  lines.add("§7Mod:  §d" + RenderHelper.modName(d.modId()));

        if (NEOIConfig.SHOW_HELD_ITEM_DURABILITY.get() && d.maxDurability() > 0) {
            String col = RenderHelper.fractionCode((float) d.currentDurability() / d.maxDurability());
            lines.add("§7Dur:  " + col + d.currentDurability() + " §7/ " + d.maxDurability()
                    + " §8(" + RenderHelper.pct(d.currentDurability(), d.maxDurability()) + ")");
        }

        if (norm) {
            if (NEOIConfig.SHOW_HELD_ITEM_ENCHANTABILITY.get() && d.enchantability() > 0)
                lines.add("§7Enchant: §e" + d.enchantability());
            if (!d.harvestTier().isEmpty())
                lines.add("§7Tier:    §a" + d.harvestTier());
            if (NEOIConfig.SHOW_HELD_ITEM_BURN_TIME.get() && d.burnTime() > 0)
                lines.add("§7Fuel:    §6" + d.burnTime() + "t §8(" + RenderHelper.ticks(d.burnTime()) + ")");
            if (NEOIConfig.SHOW_HELD_ITEM_FOOD.get() && d.isFood())
                lines.add("§7Food:    §a+" + d.foodValue() + " 🍖 §7sat §e"
                        + RenderHelper.fmt(d.saturation() * 2)
                        + (d.canAlwaysEat() ? " §8(always)" : ""));
            if (NEOIConfig.SHOW_HELD_ITEM_MAX_STACK.get())
                lines.add("§7Stack:   §f" + d.maxStackSize());
            if (NEOIConfig.SHOW_HELD_ITEM_ENCHANTS.get() && !d.enchants().isEmpty()) {
                lines.add("§7Enchants §8(" + d.enchants().size() + "):");
                d.enchants().forEach(e -> lines.add("  §9" + e));
            }
        }

        if (verbose && NEOIConfig.SHOW_HELD_ITEM_TAGS.get() && !d.tags().isEmpty())
            lines.add("§7Tags: §8" + d.tags().size() + " tag(s)");

        if ((verbose || showNbt) && !"None".equals(d.nbtSummary()))
            lines.add("§7NBT: §8" + RenderHelper.trunc(d.nbtSummary(), NEOIConfig.HUD_NBT_MAX_CHARS.get()));

        if (lines.isEmpty()) return y;

        return RenderHelper.drawPanel(gui, mc.font, x, y, lines,
                "§e§l✦ HELD: §f" + d.displayName(), COL_ITEM, bg ? alpha : 0, shadow);
    }

    // ─────────────────────────────────────────────────────────────────
    //  BIOME PANEL
    // ─────────────────────────────────────────────────────────────────
    private int renderBiomePanel(GuiGraphics gui, Minecraft mc,
                                  BiomeInfoProvider.BiomeData d,
                                  int x, int y, int alpha, boolean shadow,
                                  boolean bg, NEOIConfig.DisplayMode mode) {
        List<String> lines = new ArrayList<>();
        boolean norm    = mode != NEOIConfig.DisplayMode.COMPACT;
        boolean verbose = mode == NEOIConfig.DisplayMode.VERBOSE;

        if (NEOIConfig.SHOW_BIOME_NAME.get())    lines.add("§7Name:   §f" + d.displayName());
        if (NEOIConfig.SHOW_BIOME_ID.get())      lines.add("§7ID:     §b" + d.registryId());
        if (NEOIConfig.SHOW_BIOME_MOD.get())     lines.add("§7Mod:    §d" + RenderHelper.modName(d.modId()));

        if (norm) {
            if (NEOIConfig.SHOW_BIOME_TEMPERATURE.get())
                lines.add("§7Temp:   " + d.temperatureCategory() + " §8(" + RenderHelper.fmt(d.temperature()) + ")");
            if (NEOIConfig.SHOW_BIOME_PRECIPITATION.get())
                lines.add("§7Precip: §b" + d.precipitation());
            if (NEOIConfig.SHOW_BIOME_DOWNFALL.get())
                lines.add("§7Downfall:§e" + RenderHelper.fmt(d.downfall()));
        }

        if (verbose && NEOIConfig.SHOW_BIOME_TAGS.get() && !d.tags().isEmpty()) {
            lines.add("§7Tags §8(" + d.tags().size() + "):");
            d.tags().stream().limit(NEOIConfig.HUD_MAX_TAGS.get())
                    .forEach(t -> lines.add("  §8" + t));
        }

        return RenderHelper.drawPanel(gui, mc.font, x, y, lines,
                "§d§l✦ BIOME INFO", COL_BIOME, bg ? alpha : 0, shadow);
    }

    // ─────────────────────────────────────────────────────────────────
    //  COORDINATE PANEL
    // ─────────────────────────────────────────────────────────────────
    private void renderCoordPanel(GuiGraphics gui, Minecraft mc,
                                   CoordinateInfoProvider.CoordData d,
                                   int x, int y, int alpha, boolean shadow, boolean bg) {
        List<String> lines = new ArrayList<>();

        if (NEOIConfig.SHOW_COORD_XYZ.get())
            lines.add(String.format("§7XYZ:    §f%d, %d, %d", d.x(), d.y(), d.z()));
        if (NEOIConfig.SHOW_COORD_CHUNK.get())
            lines.add(String.format("§7Chunk:  §e%d, %d §8[%d, %d]",
                    d.chunkX(), d.chunkZ(), d.chunkLocalX(), d.chunkLocalZ()));
        if (NEOIConfig.SHOW_COORD_REGION.get())
            lines.add(String.format("§7Region: §8r.%d.%d.mca", d.regionX(), d.regionZ()));
        if (NEOIConfig.SHOW_COORD_FACING.get())
            lines.add("§7Facing: §a" + d.facing());
        if (NEOIConfig.SHOW_COORD_NETHER_PORTAL.get()) {
            if (d.isInNether())
                lines.add(String.format("§7Overworld: §f%d, %d", d.overworldX(), d.overworldZ()));
            else
                lines.add(String.format("§7Nether:    §c%d, %d", d.netherX(), d.netherZ()));
        }

        RenderHelper.drawPanel(gui, mc.font, x, y, lines,
                "§7§l✦ COORDINATES", COL_COORD, bg ? alpha : 0, shadow);
    }

    // ─────────────────────────────────────────────────────────────────
    //  PLAYER STATS PANEL
    // ─────────────────────────────────────────────────────────────────
    private void renderPlayerPanel(GuiGraphics gui, Minecraft mc,
                                    PlayerStatsProvider.PlayerStats d,
                                    int x, int y, int alpha, boolean shadow,
                                    boolean bg, NEOIConfig.DisplayMode mode) {
        List<String> lines = new ArrayList<>();
        boolean norm    = mode != NEOIConfig.DisplayMode.COMPACT;
        boolean verbose = mode == NEOIConfig.DisplayMode.VERBOSE;

        if (NEOIConfig.SHOW_PLAYER_HEALTH.get()) {
            String col = RenderHelper.fractionCode(d.health() / Math.max(d.maxHealth(), 1));
            lines.add("§7HP:   " + col + RenderHelper.fmt(d.health()) + " §7/ §c" + RenderHelper.fmt(d.maxHealth()));
        }
        if (NEOIConfig.SHOW_PLAYER_ARMOR.get())
            lines.add("§7Armor: §7" + d.armor() + " ✦");
        if (NEOIConfig.SHOW_PLAYER_FOOD.get()) {
            String col = d.food() > 12 ? "§a" : d.food() > 6 ? "§e" : "§c";
            lines.add("§7Food:  " + col + d.food() + "§7/20");
        }
        if (norm && NEOIConfig.SHOW_PLAYER_SATURATION.get())
            lines.add("§7Sat:   §e" + RenderHelper.fmt(d.saturation()));
        if (NEOIConfig.SHOW_PLAYER_XP.get())
            lines.add("§7XP:    §a" + d.xpLevel() + " §8(" + String.format("%.0f%%", d.xpProgress() * 100) + ")");
        if (norm) {
            if (NEOIConfig.SHOW_PLAYER_SPEED.get())
                lines.add("§7Speed: §a" + RenderHelper.fmt(d.speed()));
            if (NEOIConfig.SHOW_PLAYER_REACH.get())
                lines.add("§7Reach: §e" + RenderHelper.fmt(d.reach()));
        }
        if (verbose && NEOIConfig.SHOW_PLAYER_LUCK.get() && d.luck() != 0)
            lines.add("§7Luck:  §6" + RenderHelper.fmt(d.luck()));

        // State flags
        List<String> flags = new ArrayList<>();
        if (d.isFlying())    flags.add("§bFlying");
        if (d.isSprinting()) flags.add("§aSprinting");
        if (d.isCrouching()) flags.add("§7Crouching");
        if (!flags.isEmpty()) lines.add("§7State: " + String.join(", ", flags));

        if (NEOIConfig.SHOW_PLAYER_EFFECTS.get() && !d.effects().isEmpty()) {
            lines.add("§7Effects §8(" + d.effects().size() + "):");
            d.effects().forEach(e -> lines.add("  " + e));
        }

        RenderHelper.drawPanel(gui, mc.font, x, y, lines,
                "§c§l✦ PLAYER STATS", COL_PLAYER, bg ? alpha : 0, shadow);
    }
}
