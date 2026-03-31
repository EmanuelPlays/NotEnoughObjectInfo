package com.emanuelplays.notenoughobjectinfo.client;

import com.emanuelplays.notenoughobjectinfo.config.NEOIConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class NEOIScreen extends Screen {

    private final Screen parent;
    private int currentTab = 0;

    private static final String[] TAB_NAMES = {
        "HUD", "Block", "Entity", "Fluid", "Biome", "Coords", "Player", "Item", "Tooltip"
    };

    private record Toggle(String label, Supplier<Boolean> get, Consumer<Boolean> set) {}

    private final List<List<Toggle>> tabs = new ArrayList<>();

    public NEOIScreen(Screen parent) {
        super(Component.literal("§6Not Enough Object Info §7— §fSettings v2"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        tabs.clear();

        // Tab 0 — HUD General
        List<Toggle> hud = new ArrayList<>();
        hud.add(new Toggle("HUD Enabled",       NEOIConfig.HUD_ENABLED::get,          NEOIConfig.HUD_ENABLED::set));
        hud.add(new Toggle("Background Panel",  NEOIConfig.HUD_BACKGROUND::get,       NEOIConfig.HUD_BACKGROUND::set));
        hud.add(new Toggle("Text Shadow",       NEOIConfig.HUD_SHADOW::get,           NEOIConfig.HUD_SHADOW::set));
        hud.add(new Toggle("Progress Bars",     NEOIConfig.HUD_SHOW_PROGRESS_BARS::get, NEOIConfig.HUD_SHOW_PROGRESS_BARS::set));
        tabs.add(hud);

        // Tab 1 — Block
        List<Toggle> block = new ArrayList<>();
        block.add(new Toggle("Name",            NEOIConfig.SHOW_BLOCK_NAME::get,         NEOIConfig.SHOW_BLOCK_NAME::set));
        block.add(new Toggle("Registry ID",     NEOIConfig.SHOW_BLOCK_ID::get,           NEOIConfig.SHOW_BLOCK_ID::set));
        block.add(new Toggle("Mod Source",      NEOIConfig.SHOW_BLOCK_MOD::get,          NEOIConfig.SHOW_BLOCK_MOD::set));
        block.add(new Toggle("Hardness",        NEOIConfig.SHOW_BLOCK_HARDNESS::get,     NEOIConfig.SHOW_BLOCK_HARDNESS::set));
        block.add(new Toggle("Blast Resist.",   NEOIConfig.SHOW_BLOCK_RESISTANCE::get,   NEOIConfig.SHOW_BLOCK_RESISTANCE::set));
        block.add(new Toggle("Harvest Tool",    NEOIConfig.SHOW_BLOCK_TOOL::get,         NEOIConfig.SHOW_BLOCK_TOOL::set));
        block.add(new Toggle("Light Levels",    NEOIConfig.SHOW_BLOCK_LIGHT::get,        NEOIConfig.SHOW_BLOCK_LIGHT::set));
        block.add(new Toggle("Emit Light",      NEOIConfig.SHOW_BLOCK_EMIT_LIGHT::get,   NEOIConfig.SHOW_BLOCK_EMIT_LIGHT::set));
        block.add(new Toggle("Redstone Power",  NEOIConfig.SHOW_BLOCK_REDSTONE::get,     NEOIConfig.SHOW_BLOCK_REDSTONE::set));
        block.add(new Toggle("Flammable",       NEOIConfig.SHOW_BLOCK_FLAMMABLE::get,    NEOIConfig.SHOW_BLOCK_FLAMMABLE::set));
        block.add(new Toggle("Friction",        NEOIConfig.SHOW_BLOCK_FRICTION::get,     NEOIConfig.SHOW_BLOCK_FRICTION::set));
        block.add(new Toggle("Block State",     NEOIConfig.SHOW_BLOCK_STATE::get,        NEOIConfig.SHOW_BLOCK_STATE::set));
        block.add(new Toggle("Tags",            NEOIConfig.SHOW_BLOCK_TAGS::get,         NEOIConfig.SHOW_BLOCK_TAGS::set));
        block.add(new Toggle("Block Entity",    NEOIConfig.SHOW_TILE_ENTITY_DATA::get,   NEOIConfig.SHOW_TILE_ENTITY_DATA::set));
        block.add(new Toggle("Position",        NEOIConfig.SHOW_BLOCK_POS::get,          NEOIConfig.SHOW_BLOCK_POS::set));
        block.add(new Toggle("Replaceable",     NEOIConfig.SHOW_BLOCK_REPLACEABLE::get,  NEOIConfig.SHOW_BLOCK_REPLACEABLE::set));
        block.add(new Toggle("Solid Render",    NEOIConfig.SHOW_BLOCK_SOLID::get,        NEOIConfig.SHOW_BLOCK_SOLID::set));
        block.add(new Toggle("Speed Factor",    NEOIConfig.SHOW_BLOCK_SPEED_FACTOR::get, NEOIConfig.SHOW_BLOCK_SPEED_FACTOR::set));
        block.add(new Toggle("Jump Factor",     NEOIConfig.SHOW_BLOCK_JUMP_FACTOR::get,  NEOIConfig.SHOW_BLOCK_JUMP_FACTOR::set));
        block.add(new Toggle("Map Color",       NEOIConfig.SHOW_BLOCK_MAP_COLOR::get,    NEOIConfig.SHOW_BLOCK_MAP_COLOR::set));
        block.add(new Toggle("Push Reaction",   NEOIConfig.SHOW_BLOCK_PUSH_REACTION::get,NEOIConfig.SHOW_BLOCK_PUSH_REACTION::set));
        tabs.add(block);

        // Tab 2 — Entity
        List<Toggle> entity = new ArrayList<>();
        entity.add(new Toggle("Name",           NEOIConfig.SHOW_ENTITY_NAME::get,          NEOIConfig.SHOW_ENTITY_NAME::set));
        entity.add(new Toggle("Registry ID",    NEOIConfig.SHOW_ENTITY_ID::get,            NEOIConfig.SHOW_ENTITY_ID::set));
        entity.add(new Toggle("Mod Source",     NEOIConfig.SHOW_ENTITY_MOD::get,           NEOIConfig.SHOW_ENTITY_MOD::set));
        entity.add(new Toggle("Category",       NEOIConfig.SHOW_ENTITY_TYPE::get,          NEOIConfig.SHOW_ENTITY_TYPE::set));
        entity.add(new Toggle("Health",         NEOIConfig.SHOW_ENTITY_HEALTH::get,        NEOIConfig.SHOW_ENTITY_HEALTH::set));
        entity.add(new Toggle("Max Health",     NEOIConfig.SHOW_ENTITY_MAX_HEALTH::get,    NEOIConfig.SHOW_ENTITY_MAX_HEALTH::set));
        entity.add(new Toggle("Armor",          NEOIConfig.SHOW_ENTITY_ARMOR::get,         NEOIConfig.SHOW_ENTITY_ARMOR::set));
        entity.add(new Toggle("Armor Toughness",NEOIConfig.SHOW_ENTITY_ARMOR_TOUGHNESS::get,NEOIConfig.SHOW_ENTITY_ARMOR_TOUGHNESS::set));
        entity.add(new Toggle("Speed",          NEOIConfig.SHOW_ENTITY_SPEED::get,         NEOIConfig.SHOW_ENTITY_SPEED::set));
        entity.add(new Toggle("Attack Damage",  NEOIConfig.SHOW_ENTITY_ATTACK_DAMAGE::get, NEOIConfig.SHOW_ENTITY_ATTACK_DAMAGE::set));
        entity.add(new Toggle("Attack Speed",   NEOIConfig.SHOW_ENTITY_ATTACK_SPEED::get,  NEOIConfig.SHOW_ENTITY_ATTACK_SPEED::set));
        entity.add(new Toggle("Follow Range",   NEOIConfig.SHOW_ENTITY_FOLLOW_RANGE::get,  NEOIConfig.SHOW_ENTITY_FOLLOW_RANGE::set));
        entity.add(new Toggle("KB Resistance",  NEOIConfig.SHOW_ENTITY_KB_RESISTANCE::get, NEOIConfig.SHOW_ENTITY_KB_RESISTANCE::set));
        entity.add(new Toggle("Potion Effects", NEOIConfig.SHOW_ENTITY_EFFECTS::get,       NEOIConfig.SHOW_ENTITY_EFFECTS::set));
        entity.add(new Toggle("Position",       NEOIConfig.SHOW_ENTITY_POSITION::get,      NEOIConfig.SHOW_ENTITY_POSITION::set));
        entity.add(new Toggle("Dimension",      NEOIConfig.SHOW_ENTITY_DIMENSION::get,     NEOIConfig.SHOW_ENTITY_DIMENSION::set));
        entity.add(new Toggle("Tags",           NEOIConfig.SHOW_ENTITY_TAGS::get,          NEOIConfig.SHOW_ENTITY_TAGS::set));
        entity.add(new Toggle("On Fire",        NEOIConfig.SHOW_ENTITY_ON_FIRE::get,       NEOIConfig.SHOW_ENTITY_ON_FIRE::set));
        entity.add(new Toggle("Passengers",     NEOIConfig.SHOW_ENTITY_PASSENGERS::get,    NEOIConfig.SHOW_ENTITY_PASSENGERS::set));
        entity.add(new Toggle("Vehicle",        NEOIConfig.SHOW_ENTITY_VEHICLE::get,       NEOIConfig.SHOW_ENTITY_VEHICLE::set));
        entity.add(new Toggle("Baby Flag",      NEOIConfig.SHOW_ENTITY_BABY::get,          NEOIConfig.SHOW_ENTITY_BABY::set));
        entity.add(new Toggle("UUID",           NEOIConfig.SHOW_ENTITY_UUID::get,          NEOIConfig.SHOW_ENTITY_UUID::set));
        entity.add(new Toggle("NBT Summary",    NEOIConfig.SHOW_ENTITY_NBT::get,           NEOIConfig.SHOW_ENTITY_NBT::set));
        tabs.add(entity);

        // Tab 3 — Fluid
        List<Toggle> fluid = new ArrayList<>();
        fluid.add(new Toggle("Fluid Panel",     NEOIConfig.SHOW_FLUID_PANEL::get,       NEOIConfig.SHOW_FLUID_PANEL::set));
        fluid.add(new Toggle("Name",            NEOIConfig.SHOW_FLUID_NAME::get,        NEOIConfig.SHOW_FLUID_NAME::set));
        fluid.add(new Toggle("Registry ID",     NEOIConfig.SHOW_FLUID_ID::get,          NEOIConfig.SHOW_FLUID_ID::set));
        fluid.add(new Toggle("Mod Source",      NEOIConfig.SHOW_FLUID_MOD::get,         NEOIConfig.SHOW_FLUID_MOD::set));
        fluid.add(new Toggle("Is Source",       NEOIConfig.SHOW_FLUID_IS_SOURCE::get,   NEOIConfig.SHOW_FLUID_IS_SOURCE::set));
        fluid.add(new Toggle("Fluid Level",     NEOIConfig.SHOW_FLUID_LEVEL::get,       NEOIConfig.SHOW_FLUID_LEVEL::set));
        fluid.add(new Toggle("Density",         NEOIConfig.SHOW_FLUID_DENSITY::get,     NEOIConfig.SHOW_FLUID_DENSITY::set));
        fluid.add(new Toggle("Viscosity",       NEOIConfig.SHOW_FLUID_VISCOSITY::get,   NEOIConfig.SHOW_FLUID_VISCOSITY::set));
        fluid.add(new Toggle("Temperature",     NEOIConfig.SHOW_FLUID_TEMPERATURE::get, NEOIConfig.SHOW_FLUID_TEMPERATURE::set));
        fluid.add(new Toggle("Gaseous",         NEOIConfig.SHOW_FLUID_GASEOUS::get,     NEOIConfig.SHOW_FLUID_GASEOUS::set));
        tabs.add(fluid);

        // Tab 4 — Biome
        List<Toggle> biome = new ArrayList<>();
        biome.add(new Toggle("Biome Panel",     NEOIConfig.SHOW_BIOME_PANEL::get,         NEOIConfig.SHOW_BIOME_PANEL::set));
        biome.add(new Toggle("Name",            NEOIConfig.SHOW_BIOME_NAME::get,          NEOIConfig.SHOW_BIOME_NAME::set));
        biome.add(new Toggle("Registry ID",     NEOIConfig.SHOW_BIOME_ID::get,            NEOIConfig.SHOW_BIOME_ID::set));
        biome.add(new Toggle("Mod Source",      NEOIConfig.SHOW_BIOME_MOD::get,           NEOIConfig.SHOW_BIOME_MOD::set));
        biome.add(new Toggle("Temperature",     NEOIConfig.SHOW_BIOME_TEMPERATURE::get,   NEOIConfig.SHOW_BIOME_TEMPERATURE::set));
        biome.add(new Toggle("Precipitation",   NEOIConfig.SHOW_BIOME_PRECIPITATION::get, NEOIConfig.SHOW_BIOME_PRECIPITATION::set));
        biome.add(new Toggle("Downfall",        NEOIConfig.SHOW_BIOME_DOWNFALL::get,      NEOIConfig.SHOW_BIOME_DOWNFALL::set));
        biome.add(new Toggle("Tags",            NEOIConfig.SHOW_BIOME_TAGS::get,          NEOIConfig.SHOW_BIOME_TAGS::set));
        tabs.add(biome);

        // Tab 5 — Coords
        List<Toggle> coords = new ArrayList<>();
        coords.add(new Toggle("Coord Panel",    NEOIConfig.SHOW_COORD_PANEL::get,         NEOIConfig.SHOW_COORD_PANEL::set));
        coords.add(new Toggle("XYZ",            NEOIConfig.SHOW_COORD_XYZ::get,           NEOIConfig.SHOW_COORD_XYZ::set));
        coords.add(new Toggle("Chunk",          NEOIConfig.SHOW_COORD_CHUNK::get,         NEOIConfig.SHOW_COORD_CHUNK::set));
        coords.add(new Toggle("Region",         NEOIConfig.SHOW_COORD_REGION::get,        NEOIConfig.SHOW_COORD_REGION::set));
        coords.add(new Toggle("Facing",         NEOIConfig.SHOW_COORD_FACING::get,        NEOIConfig.SHOW_COORD_FACING::set));
        coords.add(new Toggle("Nether Portal",  NEOIConfig.SHOW_COORD_NETHER_PORTAL::get, NEOIConfig.SHOW_COORD_NETHER_PORTAL::set));
        tabs.add(coords);

        // Tab 6 — Player
        List<Toggle> player = new ArrayList<>();
        player.add(new Toggle("Player Panel",   NEOIConfig.SHOW_PLAYER_PANEL::get,      NEOIConfig.SHOW_PLAYER_PANEL::set));
        player.add(new Toggle("Health",         NEOIConfig.SHOW_PLAYER_HEALTH::get,     NEOIConfig.SHOW_PLAYER_HEALTH::set));
        player.add(new Toggle("Armor",          NEOIConfig.SHOW_PLAYER_ARMOR::get,      NEOIConfig.SHOW_PLAYER_ARMOR::set));
        player.add(new Toggle("Food Level",     NEOIConfig.SHOW_PLAYER_FOOD::get,       NEOIConfig.SHOW_PLAYER_FOOD::set));
        player.add(new Toggle("Saturation",     NEOIConfig.SHOW_PLAYER_SATURATION::get, NEOIConfig.SHOW_PLAYER_SATURATION::set));
        player.add(new Toggle("XP Level",       NEOIConfig.SHOW_PLAYER_XP::get,         NEOIConfig.SHOW_PLAYER_XP::set));
        player.add(new Toggle("Speed",          NEOIConfig.SHOW_PLAYER_SPEED::get,      NEOIConfig.SHOW_PLAYER_SPEED::set));
        player.add(new Toggle("Reach",          NEOIConfig.SHOW_PLAYER_REACH::get,      NEOIConfig.SHOW_PLAYER_REACH::set));
        player.add(new Toggle("Luck",           NEOIConfig.SHOW_PLAYER_LUCK::get,       NEOIConfig.SHOW_PLAYER_LUCK::set));
        player.add(new Toggle("Effects",        NEOIConfig.SHOW_PLAYER_EFFECTS::get,    NEOIConfig.SHOW_PLAYER_EFFECTS::set));
        tabs.add(player);

        // Tab 7 — Held Item
        List<Toggle> item = new ArrayList<>();
        item.add(new Toggle("Item Panel",       NEOIConfig.SHOW_HELD_ITEM_INFO::get,           NEOIConfig.SHOW_HELD_ITEM_INFO::set));
        item.add(new Toggle("Registry ID",      NEOIConfig.SHOW_HELD_ITEM_ID::get,             NEOIConfig.SHOW_HELD_ITEM_ID::set));
        item.add(new Toggle("Mod Source",       NEOIConfig.SHOW_HELD_ITEM_MOD::get,            NEOIConfig.SHOW_HELD_ITEM_MOD::set));
        item.add(new Toggle("Durability",       NEOIConfig.SHOW_HELD_ITEM_DURABILITY::get,     NEOIConfig.SHOW_HELD_ITEM_DURABILITY::set));
        item.add(new Toggle("Enchantability",   NEOIConfig.SHOW_HELD_ITEM_ENCHANTABILITY::get, NEOIConfig.SHOW_HELD_ITEM_ENCHANTABILITY::set));
        item.add(new Toggle("Burn Time",        NEOIConfig.SHOW_HELD_ITEM_BURN_TIME::get,      NEOIConfig.SHOW_HELD_ITEM_BURN_TIME::set));
        item.add(new Toggle("Food Value",       NEOIConfig.SHOW_HELD_ITEM_FOOD::get,           NEOIConfig.SHOW_HELD_ITEM_FOOD::set));
        item.add(new Toggle("Max Stack",        NEOIConfig.SHOW_HELD_ITEM_MAX_STACK::get,      NEOIConfig.SHOW_HELD_ITEM_MAX_STACK::set));
        item.add(new Toggle("Enchantments",     NEOIConfig.SHOW_HELD_ITEM_ENCHANTS::get,       NEOIConfig.SHOW_HELD_ITEM_ENCHANTS::set));
        item.add(new Toggle("Tags",             NEOIConfig.SHOW_HELD_ITEM_TAGS::get,           NEOIConfig.SHOW_HELD_ITEM_TAGS::set));
        tabs.add(item);

        // Tab 8 — Tooltip
        List<Toggle> tooltip = new ArrayList<>();
        tooltip.add(new Toggle("Registry ID",   NEOIConfig.TOOLTIP_SHOW_ID::get,             NEOIConfig.TOOLTIP_SHOW_ID::set));
        tooltip.add(new Toggle("Mod Source",    NEOIConfig.TOOLTIP_SHOW_MOD::get,            NEOIConfig.TOOLTIP_SHOW_MOD::set));
        tooltip.add(new Toggle("Durability",    NEOIConfig.TOOLTIP_SHOW_DURABILITY::get,     NEOIConfig.TOOLTIP_SHOW_DURABILITY::set));
        tooltip.add(new Toggle("Burn Time",     NEOIConfig.TOOLTIP_SHOW_BURN_TIME::get,      NEOIConfig.TOOLTIP_SHOW_BURN_TIME::set));
        tooltip.add(new Toggle("Food Value",    NEOIConfig.TOOLTIP_SHOW_FOOD::get,           NEOIConfig.TOOLTIP_SHOW_FOOD::set));
        tooltip.add(new Toggle("Enchantability",NEOIConfig.TOOLTIP_SHOW_ENCHANTABILITY::get, NEOIConfig.TOOLTIP_SHOW_ENCHANTABILITY::set));
        tooltip.add(new Toggle("Max Stack",     NEOIConfig.TOOLTIP_SHOW_MAX_STACK::get,      NEOIConfig.TOOLTIP_SHOW_MAX_STACK::set));
        tooltip.add(new Toggle("Repair Item",   NEOIConfig.TOOLTIP_SHOW_REPAIR_ITEM::get,    NEOIConfig.TOOLTIP_SHOW_REPAIR_ITEM::set));
        tooltip.add(new Toggle("Harvest Tier",  NEOIConfig.TOOLTIP_SHOW_HARVEST_TIER::get,   NEOIConfig.TOOLTIP_SHOW_HARVEST_TIER::set));
        tooltip.add(new Toggle("Creative Tab",  NEOIConfig.TOOLTIP_SHOW_CREATIVE_TAB::get,   NEOIConfig.TOOLTIP_SHOW_CREATIVE_TAB::set));
        tooltip.add(new Toggle("Tags",          NEOIConfig.TOOLTIP_SHOW_TAGS::get,           NEOIConfig.TOOLTIP_SHOW_TAGS::set));
        tooltip.add(new Toggle("NBT Summary",   NEOIConfig.TOOLTIP_SHOW_NBT::get,            NEOIConfig.TOOLTIP_SHOW_NBT::set));
        tabs.add(tooltip);

        rebuildButtons();
    }

    private void rebuildButtons() {
        clearWidgets();
        int btnW = 155, btnH = 18, padX = 5, padY = 3;
        int cols = 2;
        int startX = this.width / 2 - (cols * btnW + padX) / 2;
        int startY = 50;

        // Tab buttons
        int tabBtnW = this.width / TAB_NAMES.length;
        for (int i = 0; i < TAB_NAMES.length; i++) {
            final int idx = i;
            addRenderableWidget(Button.builder(
                    Component.literal(i == currentTab ? "§e" + TAB_NAMES[i] : "§7" + TAB_NAMES[i]),
                    btn -> { currentTab = idx; rebuildButtons(); })
                    .bounds(i * tabBtnW, 24, tabBtnW, 18)
                    .build());
        }

        // Display mode cycle
        addRenderableWidget(CycleButton.<NEOIConfig.DisplayMode>builder(
                m -> Component.literal("Mode: §e" + m.name()))
                .withValues(NEOIConfig.DisplayMode.values())
                .withInitialValue(NEOIConfig.DISPLAY_MODE.get())
                .create(this.width / 2 - 80, 4, 160, 18,
                        Component.literal("Display Mode"),
                        (btn, val) -> NEOIConfig.DISPLAY_MODE.set(val)));

        // Toggle buttons for current tab
        List<Toggle> toggles = tabs.get(currentTab);
        for (int i = 0; i < toggles.size(); i++) {
            Toggle t = toggles.get(i);
            int col = i % cols, row = i / cols;
            int bx = startX + col * (btnW + padX);
            int by = startY + row * (btnH + padY);
            addRenderableWidget(Button.builder(
                    Component.literal(t.label() + ": " + (t.get().get() ? "§aON" : "§cOFF")),
                    btn -> {
                        boolean nv = !t.get().get();
                        t.set().accept(nv);
                        btn.setMessage(Component.literal(t.label() + ": " + (nv ? "§aON" : "§cOFF")));
                    })
                    .bounds(bx, by, btnW, btnH)
                    .build());
        }

        // Done
        int lastRow = (toggles.size() + cols - 1) / cols;
        int doneY = startY + lastRow * (btnH + padY) + 6;
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE,
                btn -> this.minecraft.setScreen(parent))
                .bounds(this.width / 2 - 60, Math.min(doneY, this.height - 24), 120, 20)
                .build());
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partial) {
        renderBackground(gui);
        gui.drawCenteredString(this.font, this.title, this.width / 2, 6, 0xFFFFAA00);
        super.render(gui, mouseX, mouseY, partial);
    }

    @Override
    public void onClose() { this.minecraft.setScreen(parent); }
}
