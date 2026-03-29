package com.emanuelplays.notenoughobjectinfo.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class NEOIConfig {

    // ── HUD GENERAL ──────────────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue HUD_ENABLED;
    public static ForgeConfigSpec.EnumValue<DisplayMode> DISPLAY_MODE;
    public static ForgeConfigSpec.IntValue HUD_X;
    public static ForgeConfigSpec.IntValue HUD_Y;
    public static ForgeConfigSpec.IntValue HUD_SCALE;
    public static ForgeConfigSpec.BooleanValue HUD_BACKGROUND;
    public static ForgeConfigSpec.IntValue HUD_BACKGROUND_ALPHA;
    public static ForgeConfigSpec.BooleanValue HUD_SHADOW;

    // ── BLOCK INFO ───────────────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_NAME;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_ID;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_MOD;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_HARDNESS;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_RESISTANCE;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_TOOL;
    public static ForgeConfigSpec.BooleanValue SHOW_BLOCK_LIGHT;
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
    public static ForgeConfigSpec.BooleanValue SHOW_TILE_ENTITY_DATA;

    // ── ENTITY INFO ──────────────────────────────────────────────────
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_NAME;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_ID;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_MOD;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_TYPE;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_HEALTH;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_MAX_HEALTH;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_ARMOR;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_SPEED;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_ATTACK_DAMAGE;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_EFFECTS;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_POSITION;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_DIMENSION;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_TAGS;
    public static ForgeConfigSpec.BooleanValue SHOW_ENTITY_NBT;

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

    public static ForgeConfigSpec SPEC;

    public static void register(ModLoadingContext ctx) {
        ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();
        buildConfig(b);
        SPEC = b.build();
        ctx.registerConfig(ModConfig.Type.CLIENT, SPEC);
    }

    private static void buildConfig(ForgeConfigSpec.Builder b) {
        b.comment("General HUD Settings").push("hud_general");
        HUD_ENABLED           = b.comment("Enable/disable the HUD overlay entirely").define("hudEnabled", true);
        DISPLAY_MODE          = b.comment("COMPACT, NORMAL, or VERBOSE").defineEnum("displayMode", DisplayMode.NORMAL);
        HUD_X                 = b.comment("HUD X offset from left edge").defineInRange("hudX", 4, 0, 3840);
        HUD_Y                 = b.comment("HUD Y offset from top edge").defineInRange("hudY", 4, 0, 2160);
        HUD_SCALE             = b.comment("Text scale 25-200%").defineInRange("hudScale", 100, 25, 200);
        HUD_BACKGROUND        = b.comment("Draw background behind text").define("hudBackground", true);
        HUD_BACKGROUND_ALPHA  = b.comment("Background alpha 0-255").defineInRange("hudBackgroundAlpha", 120, 0, 255);
        HUD_SHADOW            = b.comment("Render text shadow").define("hudShadow", true);
        b.pop();

        b.comment("Block Information Toggles").push("block_info");
        SHOW_BLOCK_NAME        = b.define("showBlockName",        true);
        SHOW_BLOCK_ID          = b.define("showBlockId",          true);
        SHOW_BLOCK_MOD         = b.define("showBlockMod",         true);
        SHOW_BLOCK_HARDNESS    = b.define("showBlockHardness",    true);
        SHOW_BLOCK_RESISTANCE  = b.define("showBlockResistance",  true);
        SHOW_BLOCK_TOOL        = b.define("showBlockTool",        true);
        SHOW_BLOCK_LIGHT       = b.define("showBlockLight",       true);
        SHOW_BLOCK_REDSTONE    = b.define("showBlockRedstone",    true);
        SHOW_BLOCK_FLAMMABLE   = b.define("showBlockFlammable",   true);
        SHOW_BLOCK_REPLACEABLE = b.define("showBlockReplaceable", false);
        SHOW_BLOCK_SOLID       = b.define("showBlockSolid",       false);
        SHOW_BLOCK_FRICTION    = b.define("showBlockFriction",    true);
        SHOW_BLOCK_SPEED_FACTOR= b.define("showBlockSpeedFactor", false);
        SHOW_BLOCK_JUMP_FACTOR = b.define("showBlockJumpFactor",  false);
        SHOW_BLOCK_STATE       = b.define("showBlockState",       true);
        SHOW_BLOCK_TAGS        = b.define("showBlockTags",        true);
        SHOW_BLOCK_MAP_COLOR   = b.define("showBlockMapColor",    false);
        SHOW_BLOCK_DROPS       = b.define("showBlockDrops",       false);
        SHOW_TILE_ENTITY_DATA  = b.define("showTileEntityData",   true);
        b.pop();

        b.comment("Entity Information Toggles").push("entity_info");
        SHOW_ENTITY_NAME         = b.define("showEntityName",         true);
        SHOW_ENTITY_ID           = b.define("showEntityId",           true);
        SHOW_ENTITY_MOD          = b.define("showEntityMod",          true);
        SHOW_ENTITY_TYPE         = b.define("showEntityType",         true);
        SHOW_ENTITY_HEALTH       = b.define("showEntityHealth",       true);
        SHOW_ENTITY_MAX_HEALTH   = b.define("showEntityMaxHealth",    true);
        SHOW_ENTITY_ARMOR        = b.define("showEntityArmor",        true);
        SHOW_ENTITY_SPEED        = b.define("showEntitySpeed",        true);
        SHOW_ENTITY_ATTACK_DAMAGE= b.define("showEntityAttackDamage", true);
        SHOW_ENTITY_EFFECTS      = b.define("showEntityEffects",      true);
        SHOW_ENTITY_POSITION     = b.define("showEntityPosition",     true);
        SHOW_ENTITY_DIMENSION    = b.define("showEntityDimension",    true);
        SHOW_ENTITY_TAGS         = b.define("showEntityTags",         true);
        SHOW_ENTITY_NBT          = b.define("showEntityNbt",          false);
        b.pop();

        b.comment("Held Item HUD Toggles").push("held_item_hud");
        SHOW_HELD_ITEM_INFO           = b.define("showHeldItemInfo",           true);
        SHOW_HELD_ITEM_ID             = b.define("showHeldItemId",             true);
        SHOW_HELD_ITEM_MOD            = b.define("showHeldItemMod",            true);
        SHOW_HELD_ITEM_DURABILITY     = b.define("showHeldItemDurability",     true);
        SHOW_HELD_ITEM_ENCHANTABILITY = b.define("showHeldItemEnchantability", true);
        SHOW_HELD_ITEM_BURN_TIME      = b.define("showHeldItemBurnTime",       true);
        SHOW_HELD_ITEM_FOOD           = b.define("showHeldItemFood",           true);
        SHOW_HELD_ITEM_TAGS           = b.define("showHeldItemTags",           false);
        SHOW_HELD_ITEM_MAX_STACK      = b.define("showHeldItemMaxStack",       true);
        b.pop();

        b.comment("Item Tooltip Extra Info Toggles").push("tooltip_info");
        TOOLTIP_SHOW_ID             = b.define("tooltipShowId",             true);
        TOOLTIP_SHOW_MOD            = b.define("tooltipShowMod",            true);
        TOOLTIP_SHOW_DURABILITY     = b.define("tooltipShowDurability",     true);
        TOOLTIP_SHOW_BURN_TIME      = b.define("tooltipShowBurnTime",       true);
        TOOLTIP_SHOW_FOOD           = b.define("tooltipShowFood",           true);
        TOOLTIP_SHOW_TAGS           = b.define("tooltipShowTags",           false);
        TOOLTIP_SHOW_NBT            = b.define("tooltipShowNbt",            false);
        TOOLTIP_SHOW_ENCHANTABILITY = b.define("tooltipShowEnchantability", true);
        TOOLTIP_SHOW_MAX_STACK      = b.define("tooltipShowMaxStack",       true);
        TOOLTIP_SHOW_REPAIR_ITEM    = b.define("tooltipShowRepairItem",     true);
        b.pop();
    }

    public enum DisplayMode {
        COMPACT, NORMAL, VERBOSE
    }
}
