package com.emanuelplays.notenoughobjectinfo.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

/**
 * NEOI v2 configuration.
 * All ~70 client-side toggles and settings live here.
 */
public class NEOIConfig {

    // ── HUD GENERAL ──────────────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue HUD_ENABLED;
    public static ForgeConfigSpec.EnumValue<DisplayMode> DISPLAY_MODE;
    public static ForgeConfigSpec.IntValue    HUD_X;
    public static ForgeConfigSpec.IntValue    HUD_Y;
    public static ForgeConfigSpec.IntValue    HUD_SCALE;
    public static ForgeConfigSpec.BooleanValue HUD_BACKGROUND;
    public static ForgeConfigSpec.IntValue    HUD_BACKGROUND_ALPHA;
    public static ForgeConfigSpec.BooleanValue HUD_SHADOW;
    public static ForgeConfigSpec.IntValue    HUD_MAX_TAGS;
    public static ForgeConfigSpec.IntValue    HUD_NBT_MAX_CHARS;
    public static ForgeConfigSpec.BooleanValue HUD_SHOW_PROGRESS_BARS;
    public static ForgeConfigSpec.IntValue    HUD_PANEL_GAP;

    // ── BLOCK INFO ───────────────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_NAME;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_ID;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_MOD;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_HARDNESS;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_RESISTANCE;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_TOOL;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_HARVEST_LEVEL;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_LIGHT;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_EMIT_LIGHT;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_REDSTONE;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_FLAMMABLE;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_REPLACEABLE;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_SOLID;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_FRICTION;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_SPEED_FACTOR;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_JUMP_FACTOR;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_STATE;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_TAGS;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_MAP_COLOR;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_DROPS;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_PUSH_REACTION;
    public static ForgeConfigSpec.BooleanValue SHOW_TILE_ENTITY_DATA;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_POS;

    // ── ENTITY INFO ──────────────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_NAME;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_ID;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_MOD;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_TYPE;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_HEALTH;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_MAX_HEALTH;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_ARMOR;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_ARMOR_TOUGHNESS;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_SPEED;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_ATTACK_DAMAGE;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_ATTACK_SPEED;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_FOLLOW_RANGE;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_KB_RESISTANCE;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_EFFECTS;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_POSITION;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_DIMENSION;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_TAGS;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_UUID;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_NBT;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_PASSENGERS;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_VEHICLE;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_ON_FIRE;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_BABY;

    // ── FLUID INFO ───────────────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue SHOW_FLUID_PANEL;
    public static ForgeConfigSpec.BooleanValue SHOW_FLUID_NAME;
    public static ForgeConfigSpec.BooleanValue SHOW_FLUID_ID;
    public static ForgeConfigSpec.BooleanValue SHOW_FLUID_MOD;
    public static ForgeConfigSpec.BooleanValue SHOW_FLUID_IS_SOURCE;
    public static ForgeConfigSpec.BooleanValue SHOW_FLUID_LEVEL;
    public static ForgeConfigSpec.BooleanValue SHOW_FLUID_DENSITY;
    public static ForgeConfigSpec.BooleanValue SHOW_FLUID_VISCOSITY;
    public static ForgeConfigSpec.BooleanValue SHOW_FLUID_TEMPERATURE;
    public static ForgeConfigSpec.BooleanValue SHOW_FLUID_GASEOUS;

    // ── BIOME INFO ───────────────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue SHOW_BIOME_PANEL;
    public static ForgeConfigSpec.BooleanValue SHOW_BIOME_NAME;
    public static ForgeConfigSpec.BooleanValue SHOW_BIOME_ID;
    public static ForgeConfigSpec.BooleanValue SHOW_BIOME_MOD;
    public static ForgeConfigSpec.BooleanValue SHOW_BIOME_TEMPERATURE;
    public static ForgeConfigSpec.BooleanValue SHOW_BIOME_PRECIPITATION;
    public static ForgeConfigSpec.BooleanValue SHOW_BIOME_DOWNFALL;
    public static ForgeConfigSpec.BooleanValue SHOW_BIOME_TAGS;

    // ── COORDINATE OVERLAY ───────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue SHOW_COORD_PANEL;
    public static ForgeConfigSpec.BooleanValue SHOW_COORD_XYZ;
    public static ForgeConfigSpec.BooleanValue SHOW_COORD_CHUNK;
    public static ForgeConfigSpec.BooleanValue SHOW_COORD_REGION;
    public static ForgeConfigSpec.BooleanValue SHOW_COORD_FACING;
    public static ForgeConfigSpec.BooleanValue SHOW_COORD_NETHER_PORTAL;
    public static ForgeConfigSpec.IntValue     COORD_X;
    public static ForgeConfigSpec.IntValue     COORD_Y;

    // ── PLAYER STATS ─────────────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue SHOW_PLAYER_PANEL;
    public static ForgeConfigSpec.BooleanValue SHOW_PLAYER_HEALTH;
    public static ForgeConfigSpec.BooleanValue SHOW_PLAYER_ARMOR;
    public static ForgeConfigSpec.BooleanValue SHOW_PLAYER_FOOD;
    public static ForgeConfigSpec.BooleanValue SHOW_PLAYER_SATURATION;
    public static ForgeConfigSpec.BooleanValue SHOW_PLAYER_XP;
    public static ForgeConfigSpec.BooleanValue SHOW_PLAYER_SPEED;
    public static ForgeConfigSpec.BooleanValue SHOW_PLAYER_REACH;
    public static ForgeConfigSpec.BooleanValue SHOW_PLAYER_LUCK;
    public static ForgeConfigSpec.BooleanValue SHOW_PLAYER_EFFECTS;

    // ── HELD ITEM HUD ────────────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue SHOW_HELD_ITEM_INFO;
    public static ForgeConfigSpec.BooleanValue SHOW_HELD_ITEM_ID;
    public static ForgeConfigSpec.BooleanValue SHOW_HELD_ITEM_MOD;
    public static ForgeConfigSpec.BooleanValue SHOW_HELD_ITEM_DURABILITY;
    public static ForgeConfigSpec.BooleanValue SHOW_HELD_ITEM_ENCHANTABILITY;
    public static ForgeConfigSpec.BooleanValue SHOW_HELD_ITEM_BURN_TIME;
    public static ForgeConfigSpec.BooleanValue SHOW_HELD_ITEM_FOOD;
    public static ForgeConfigSpec.BooleanValue SHOW_HELD_ITEM_TAGS;
    public static ForgeConfigSpec.BooleanValue SHOW_HELD_ITEM_MAX_STACK;
    public static ForgeConfigSpec.BooleanValue SHOW_HELD_ITEM_ENCHANTS;

    // ── TOOLTIP ──────────────────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_ID;
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_MOD;
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_DURABILITY;
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_BURN_TIME;
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_FOOD;
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_TAGS;
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_NBT;
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_ENCHANTABILITY;
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_MAX_STACK;
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_REPAIR_ITEM;
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_HARVEST_TIER;
    public static ForgeConfigSpec.BooleanValue TOOLTIP_SHOW_CREATIVE_TAB;

    public static ForgeConfigSpec SPEC;

    public static void register(ModLoadingContext ctx) {
        ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();
        buildConfig(b);
        SPEC = b.build();
        ctx.registerConfig(ModConfig.Type.CLIENT, SPEC);
    }

    private static void buildConfig(ForgeConfigSpec.Builder b) {

        /* ── HUD GENERAL ── */
        b.comment("General HUD Settings").push("hud_general");
        HUD_ENABLED            = b.comment("Master switch for the entire overlay").define("hudEnabled", true);
        DISPLAY_MODE           = b.comment("COMPACT | NORMAL | VERBOSE").defineEnum("displayMode", DisplayMode.NORMAL);
        HUD_X                  = b.comment("HUD left offset in pixels").defineInRange("hudX", 4, 0, 3840);
        HUD_Y                  = b.comment("HUD top offset in pixels").defineInRange("hudY", 4, 0, 2160);
        HUD_SCALE              = b.comment("Text scale 25–200 percent").defineInRange("hudScale", 100, 25, 200);
        HUD_BACKGROUND         = b.comment("Draw background panel behind text").define("hudBackground", true);
        HUD_BACKGROUND_ALPHA   = b.comment("Background opacity 0–255").defineInRange("hudBackgroundAlpha", 130, 0, 255);
        HUD_SHADOW             = b.comment("Draw drop shadow on text").define("hudShadow", true);
        HUD_MAX_TAGS           = b.comment("Max tags to list in VERBOSE mode (0 = all)").defineInRange("hudMaxTags", 8, 0, 64);
        HUD_NBT_MAX_CHARS      = b.comment("Max NBT characters before truncation").defineInRange("hudNbtMaxChars", 256, 64, 2048);
        HUD_SHOW_PROGRESS_BARS = b.comment("Show visual health/durability progress bars").define("hudShowProgressBars", true);
        HUD_PANEL_GAP          = b.comment("Pixel gap between stacked panels").defineInRange("hudPanelGap", 4, 0, 20);
        b.pop();

        /* ── BLOCK ── */
        b.comment("Block Information Toggles").push("block_info");
        SHOW_BLOCK_NAME         = b.define("showBlockName",         true);
        SHOW_BLOCK_ID           = b.define("showBlockId",           true);
        SHOW_BLOCK_MOD          = b.define("showBlockMod",          true);
        SHOW_BLOCK_HARDNESS     = b.define("showBlockHardness",     true);
        SHOW_BLOCK_RESISTANCE   = b.define("showBlockResistance",   true);
        SHOW_BLOCK_TOOL         = b.define("showBlockTool",         true);
        SHOW_BLOCK_HARVEST_LEVEL= b.define("showBlockHarvestLevel", true);
        SHOW_BLOCK_LIGHT        = b.define("showBlockLight",        true);
        SHOW_BLOCK_EMIT_LIGHT   = b.define("showBlockEmitLight",    true);
        SHOW_BLOCK_REDSTONE     = b.define("showBlockRedstone",     true);
        SHOW_BLOCK_FLAMMABLE    = b.define("showBlockFlammable",    true);
        SHOW_BLOCK_FRICTION     = b.define("showBlockFriction",     true);
        SHOW_BLOCK_STATE        = b.define("showBlockState",        true);
        SHOW_BLOCK_TAGS         = b.define("showBlockTags",         true);
        SHOW_TILE_ENTITY_DATA   = b.define("showTileEntityData",    true);
        SHOW_BLOCK_POS          = b.define("showBlockPos",          true);
        SHOW_BLOCK_REPLACEABLE  = b.define("showBlockReplaceable",  false);
        SHOW_BLOCK_SOLID        = b.define("showBlockSolid",        false);
        SHOW_BLOCK_SPEED_FACTOR = b.define("showBlockSpeedFactor",  false);
        SHOW_BLOCK_JUMP_FACTOR  = b.define("showBlockJumpFactor",   false);
        SHOW_BLOCK_MAP_COLOR    = b.define("showBlockMapColor",     false);
        SHOW_BLOCK_DROPS        = b.define("showBlockDrops",        false);
        SHOW_BLOCK_PUSH_REACTION= b.define("showBlockPushReaction", false);
        b.pop();

        /* ── ENTITY ── */
        b.comment("Entity Information Toggles").push("entity_info");
        SHOW_ENTITY_NAME          = b.define("showEntityName",          true);
        SHOW_ENTITY_ID            = b.define("showEntityId",            true);
        SHOW_ENTITY_MOD           = b.define("showEntityMod",           true);
        SHOW_ENTITY_TYPE          = b.define("showEntityType",          true);
        SHOW_ENTITY_HEALTH        = b.define("showEntityHealth",        true);
        SHOW_ENTITY_MAX_HEALTH    = b.define("showEntityMaxHealth",     true);
        SHOW_ENTITY_ARMOR         = b.define("showEntityArmor",         true);
        SHOW_ENTITY_SPEED         = b.define("showEntitySpeed",         true);
        SHOW_ENTITY_ATTACK_DAMAGE = b.define("showEntityAttackDamage",  true);
        SHOW_ENTITY_EFFECTS       = b.define("showEntityEffects",       true);
        SHOW_ENTITY_POSITION      = b.define("showEntityPosition",      true);
        SHOW_ENTITY_DIMENSION     = b.define("showEntityDimension",     true);
        SHOW_ENTITY_TAGS          = b.define("showEntityTags",          true);
        SHOW_ENTITY_ON_FIRE       = b.define("showEntityOnFire",        true);
        SHOW_ENTITY_PASSENGERS    = b.define("showEntityPassengers",    true);
        SHOW_ENTITY_VEHICLE       = b.define("showEntityVehicle",       true);
        SHOW_ENTITY_ARMOR_TOUGHNESS = b.define("showEntityArmorToughness", false);
        SHOW_ENTITY_ATTACK_SPEED  = b.define("showEntityAttackSpeed",   false);
        SHOW_ENTITY_FOLLOW_RANGE  = b.define("showEntityFollowRange",   false);
        SHOW_ENTITY_KB_RESISTANCE = b.define("showEntityKbResistance",  false);
        SHOW_ENTITY_UUID          = b.define("showEntityUuid",          false);
        SHOW_ENTITY_BABY          = b.define("showEntityBaby",          true);
        SHOW_ENTITY_NBT           = b.define("showEntityNbt",           false);
        b.pop();

        /* ── FLUID ── */
        b.comment("Fluid Information Toggles (NEW in v2)").push("fluid_info");
        SHOW_FLUID_PANEL       = b.define("showFluidPanel",     true);
        SHOW_FLUID_NAME        = b.define("showFluidName",      true);
        SHOW_FLUID_ID          = b.define("showFluidId",        true);
        SHOW_FLUID_MOD         = b.define("showFluidMod",       true);
        SHOW_FLUID_IS_SOURCE   = b.define("showFluidIsSource",  true);
        SHOW_FLUID_LEVEL       = b.define("showFluidLevel",     true);
        SHOW_FLUID_DENSITY     = b.define("showFluidDensity",   true);
        SHOW_FLUID_VISCOSITY   = b.define("showFluidViscosity", true);
        SHOW_FLUID_TEMPERATURE = b.define("showFluidTemperature", true);
        SHOW_FLUID_GASEOUS     = b.define("showFluidGaseous",   true);
        b.pop();

        /* ── BIOME ── */
        b.comment("Biome Information Toggles (NEW in v2)").push("biome_info");
        SHOW_BIOME_PANEL         = b.define("showBiomePanel",       true);
        SHOW_BIOME_NAME          = b.define("showBiomeName",        true);
        SHOW_BIOME_ID            = b.define("showBiomeId",          true);
        SHOW_BIOME_MOD           = b.define("showBiomeMod",         true);
        SHOW_BIOME_TEMPERATURE   = b.define("showBiomeTemperature", true);
        SHOW_BIOME_PRECIPITATION = b.define("showBiomePrecipitation", true);
        SHOW_BIOME_DOWNFALL      = b.define("showBiomeDownfall",    true);
        SHOW_BIOME_TAGS          = b.define("showBiomeTags",        true);
        b.pop();

        /* ── COORDINATES ── */
        b.comment("Coordinate Overlay Settings (NEW in v2)").push("coordinate_overlay");
        SHOW_COORD_PANEL        = b.define("showCoordPanel",       false);
        SHOW_COORD_XYZ          = b.define("showCoordXyz",         true);
        SHOW_COORD_CHUNK        = b.define("showCoordChunk",        true);
        SHOW_COORD_REGION       = b.define("showCoordRegion",       true);
        SHOW_COORD_FACING       = b.define("showCoordFacing",       true);
        SHOW_COORD_NETHER_PORTAL= b.define("showCoordNetherPortal", true);
        COORD_X                 = b.comment("X position of coordinate panel").defineInRange("coordX", 4, 0, 3840);
        COORD_Y                 = b.comment("Y position of coordinate panel").defineInRange("coordY", 80, 0, 2160);
        b.pop();

        /* ── PLAYER STATS ── */
        b.comment("Player Stats Panel Toggles (NEW in v2)").push("player_stats");
        SHOW_PLAYER_PANEL      = b.define("showPlayerPanel",     false);
        SHOW_PLAYER_HEALTH     = b.define("showPlayerHealth",    true);
        SHOW_PLAYER_ARMOR      = b.define("showPlayerArmor",     true);
        SHOW_PLAYER_FOOD       = b.define("showPlayerFood",      true);
        SHOW_PLAYER_SATURATION = b.define("showPlayerSaturation",true);
        SHOW_PLAYER_XP         = b.define("showPlayerXp",        true);
        SHOW_PLAYER_SPEED      = b.define("showPlayerSpeed",     true);
        SHOW_PLAYER_REACH      = b.define("showPlayerReach",     true);
        SHOW_PLAYER_LUCK       = b.define("showPlayerLuck",      false);
        SHOW_PLAYER_EFFECTS    = b.define("showPlayerEffects",   true);
        b.pop();

        /* ── HELD ITEM ── */
        b.comment("Held Item HUD Toggles").push("held_item_hud");
        SHOW_HELD_ITEM_INFO           = b.define("showHeldItemInfo",           true);
        SHOW_HELD_ITEM_ID             = b.define("showHeldItemId",             true);
        SHOW_HELD_ITEM_MOD            = b.define("showHeldItemMod",            true);
        SHOW_HELD_ITEM_DURABILITY     = b.define("showHeldItemDurability",     true);
        SHOW_HELD_ITEM_ENCHANTABILITY = b.define("showHeldItemEnchantability", true);
        SHOW_HELD_ITEM_BURN_TIME      = b.define("showHeldItemBurnTime",       true);
        SHOW_HELD_ITEM_FOOD           = b.define("showHeldItemFood",           true);
        SHOW_HELD_ITEM_MAX_STACK      = b.define("showHeldItemMaxStack",       true);
        SHOW_HELD_ITEM_ENCHANTS       = b.define("showHeldItemEnchants",       true);
        SHOW_HELD_ITEM_TAGS           = b.define("showHeldItemTags",           false);
        b.pop();

        /* ── TOOLTIP ── */
        b.comment("Item Tooltip Extra Info Toggles").push("tooltip_info");
        TOOLTIP_SHOW_ID             = b.define("tooltipShowId",             true);
        TOOLTIP_SHOW_MOD            = b.define("tooltipShowMod",            true);
        TOOLTIP_SHOW_DURABILITY     = b.define("tooltipShowDurability",     true);
        TOOLTIP_SHOW_BURN_TIME      = b.define("tooltipShowBurnTime",       true);
        TOOLTIP_SHOW_FOOD           = b.define("tooltipShowFood",           true);
        TOOLTIP_SHOW_ENCHANTABILITY = b.define("tooltipShowEnchantability", true);
        TOOLTIP_SHOW_MAX_STACK      = b.define("tooltipShowMaxStack",       true);
        TOOLTIP_SHOW_REPAIR_ITEM    = b.define("tooltipShowRepairItem",     true);
        TOOLTIP_SHOW_HARVEST_TIER   = b.define("tooltipShowHarvestTier",    true);
        TOOLTIP_SHOW_CREATIVE_TAB   = b.define("tooltipShowCreativeTab",    false);
        TOOLTIP_SHOW_TAGS           = b.define("tooltipShowTags",           false);
        TOOLTIP_SHOW_NBT            = b.define("tooltipShowNbt",            false);
        b.pop();
    }

    public enum DisplayMode { COMPACT, NORMAL, VERBOSE }
}
