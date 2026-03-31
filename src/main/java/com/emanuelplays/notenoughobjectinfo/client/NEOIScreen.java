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

@OnlyIn(Dist.CLIENT)
public class NEOIScreen extends Screen {

    private final Screen parent;

    // ── Toggle button state helpers ───────────────────────────────────
    private record Toggle(String label, java.util.function.Supplier<Boolean> getter,
                          java.util.function.Consumer<Boolean> setter) {}

    private final List<Toggle> toggles = new ArrayList<>();

    public NEOIScreen(Screen parent) {
        super(Component.literal("Not Enough Object Info — Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        toggles.clear();

        // ── HUD ──────────────────────────────────────────────────────
        toggles.add(new Toggle("HUD Enabled",        NEOIConfig.HUD_ENABLED::get,          NEOIConfig.HUD_ENABLED::set));
        toggles.add(new Toggle("HUD Background",     NEOIConfig.HUD_BACKGROUND::get,       NEOIConfig.HUD_BACKGROUND::set));
        toggles.add(new Toggle("HUD Shadow",         NEOIConfig.HUD_SHADOW::get,           NEOIConfig.HUD_SHADOW::set));

        // ── Block ─────────────────────────────────────────────────────
        toggles.add(new Toggle("Block: Name",        NEOIConfig.SHOW_BLOCK_NAME::get,       NEOIConfig.SHOW_BLOCK_NAME::set));
        toggles.add(new Toggle("Block: Registry ID", NEOIConfig.SHOW_BLOCK_ID::get,         NEOIConfig.SHOW_BLOCK_ID::set));
        toggles.add(new Toggle("Block: Mod",         NEOIConfig.SHOW_BLOCK_MOD::get,        NEOIConfig.SHOW_BLOCK_MOD::set));
        toggles.add(new Toggle("Block: Hardness",    NEOIConfig.SHOW_BLOCK_HARDNESS::get,   NEOIConfig.SHOW_BLOCK_HARDNESS::set));
        toggles.add(new Toggle("Block: Resistance",  NEOIConfig.SHOW_BLOCK_RESISTANCE::get, NEOIConfig.SHOW_BLOCK_RESISTANCE::set));
        toggles.add(new Toggle("Block: Tool",        NEOIConfig.SHOW_BLOCK_TOOL::get,       NEOIConfig.SHOW_BLOCK_TOOL::set));
        toggles.add(new Toggle("Block: Light",       NEOIConfig.SHOW_BLOCK_LIGHT::get,      NEOIConfig.SHOW_BLOCK_LIGHT::set));
        toggles.add(new Toggle("Block: Redstone",    NEOIConfig.SHOW_BLOCK_REDSTONE::get,   NEOIConfig.SHOW_BLOCK_REDSTONE::set));
        toggles.add(new Toggle("Block: Flammable",   NEOIConfig.SHOW_BLOCK_FLAMMABLE::get,  NEOIConfig.SHOW_BLOCK_FLAMMABLE::set));
        toggles.add(new Toggle("Block: Friction",    NEOIConfig.SHOW_BLOCK_FRICTION::get,   NEOIConfig.SHOW_BLOCK_FRICTION::set));
        toggles.add(new Toggle("Block: State",       NEOIConfig.SHOW_BLOCK_STATE::get,      NEOIConfig.SHOW_BLOCK_STATE::set));
        toggles.add(new Toggle("Block: Tags",        NEOIConfig.SHOW_BLOCK_TAGS::get,       NEOIConfig.SHOW_BLOCK_TAGS::set));
        toggles.add(new Toggle("Block Entity Data",  NEOIConfig.SHOW_TILE_ENTITY_DATA::get, NEOIConfig.SHOW_TILE_ENTITY_DATA::set));

        // ── Entity ───────────────────────────────────────────────────
        toggles.add(new Toggle("Entity: Name",       NEOIConfig.SHOW_ENTITY_NAME::get,         NEOIConfig.SHOW_ENTITY_NAME::set));
        toggles.add(new Toggle("Entity: Registry ID",NEOIConfig.SHOW_ENTITY_ID::get,           NEOIConfig.SHOW_ENTITY_ID::set));
        toggles.add(new Toggle("Entity: Mod",        NEOIConfig.SHOW_ENTITY_MOD::get,          NEOIConfig.SHOW_ENTITY_MOD::set));
        toggles.add(new Toggle("Entity: Health",     NEOIConfig.SHOW_ENTITY_HEALTH::get,       NEOIConfig.SHOW_ENTITY_HEALTH::set));
        toggles.add(new Toggle("Entity: Max Health", NEOIConfig.SHOW_ENTITY_MAX_HEALTH::get,   NEOIConfig.SHOW_ENTITY_MAX_HEALTH::set));
        toggles.add(new Toggle("Entity: Armor",      NEOIConfig.SHOW_ENTITY_ARMOR::get,        NEOIConfig.SHOW_ENTITY_ARMOR::set));
        toggles.add(new Toggle("Entity: Speed",      NEOIConfig.SHOW_ENTITY_SPEED::get,        NEOIConfig.SHOW_ENTITY_SPEED::set));
        toggles.add(new Toggle("Entity: Attack",     NEOIConfig.SHOW_ENTITY_ATTACK_DAMAGE::get,NEOIConfig.SHOW_ENTITY_ATTACK_DAMAGE::set));
        toggles.add(new Toggle("Entity: Effects",    NEOIConfig.SHOW_ENTITY_EFFECTS::get,      NEOIConfig.SHOW_ENTITY_EFFECTS::set));
        toggles.add(new Toggle("Entity: Position",   NEOIConfig.SHOW_ENTITY_POSITION::get,     NEOIConfig.SHOW_ENTITY_POSITION::set));
        toggles.add(new Toggle("Entity: Tags",       NEOIConfig.SHOW_ENTITY_TAGS::get,         NEOIConfig.SHOW_ENTITY_TAGS::set));
        toggles.add(new Toggle("Entity: NBT",        NEOIConfig.SHOW_ENTITY_NBT::get,          NEOIConfig.SHOW_ENTITY_NBT::set));

        // ── Tooltip ───────────────────────────────────────────────────
        toggles.add(new Toggle("Tooltip: ID",        NEOIConfig.TOOLTIP_SHOW_ID::get,           NEOIConfig.TOOLTIP_SHOW_ID::set));
        toggles.add(new Toggle("Tooltip: Mod",       NEOIConfig.TOOLTIP_SHOW_MOD::get,          NEOIConfig.TOOLTIP_SHOW_MOD::set));
        toggles.add(new Toggle("Tooltip: Durability",NEOIConfig.TOOLTIP_SHOW_DURABILITY::get,   NEOIConfig.TOOLTIP_SHOW_DURABILITY::set));
        toggles.add(new Toggle("Tooltip: Burn Time", NEOIConfig.TOOLTIP_SHOW_BURN_TIME::get,    NEOIConfig.TOOLTIP_SHOW_BURN_TIME::set));
        toggles.add(new Toggle("Tooltip: Food",      NEOIConfig.TOOLTIP_SHOW_FOOD::get,         NEOIConfig.TOOLTIP_SHOW_FOOD::set));
        toggles.add(new Toggle("Tooltip: Tags",      NEOIConfig.TOOLTIP_SHOW_TAGS::get,         NEOIConfig.TOOLTIP_SHOW_TAGS::set));
        toggles.add(new Toggle("Tooltip: NBT",       NEOIConfig.TOOLTIP_SHOW_NBT::get,          NEOIConfig.TOOLTIP_SHOW_NBT::set));
        toggles.add(new Toggle("Tooltip: Enchant",   NEOIConfig.TOOLTIP_SHOW_ENCHANTABILITY::get,NEOIConfig.TOOLTIP_SHOW_ENCHANTABILITY::set));
        toggles.add(new Toggle("Tooltip: Max Stack", NEOIConfig.TOOLTIP_SHOW_MAX_STACK::get,    NEOIConfig.TOOLTIP_SHOW_MAX_STACK::set));
        toggles.add(new Toggle("Tooltip: Repair",    NEOIConfig.TOOLTIP_SHOW_REPAIR_ITEM::get,  NEOIConfig.TOOLTIP_SHOW_REPAIR_ITEM::set));

        // ── Render buttons in a 2-column scrollable grid ──────────────
        int cols       = 2;
        int btnW       = 160;
        int btnH       = 20;
        int padX       = 10;
        int padY       = 5;
        int startX     = this.width / 2 - (cols * btnW + (cols - 1) * padX) / 2;
        int startY     = 30;

        for (int i = 0; i < toggles.size(); i++) {
            Toggle t  = toggles.get(i);
            int col   = i % cols;
            int row   = i / cols;
            int bx    = startX + col * (btnW + padX);
            int by    = startY + row * (btnH + padY);
            final Toggle ft = t;
            addRenderableWidget(Button.builder(
                    Component.literal(ft.label() + ": " + (ft.getter().get() ? "§aON" : "§cOFF")),
                    btn -> {
                        boolean newVal = !ft.getter().get();
                        ft.setter().accept(newVal);
                        btn.setMessage(Component.literal(ft.label() + ": " + (newVal ? "§aON" : "§cOFF")));
                    })
                    .bounds(bx, by, btnW, btnH)
                    .build());
        }

        // ── Display Mode cycle button ──────────────────────────────────
        int modeY = startY + ((toggles.size() / cols) + 1) * (btnH + padY);
        addRenderableWidget(CycleButton.<NEOIConfig.DisplayMode>builder(mode ->
                        Component.literal("Mode: §e" + mode.name()))
                .withValues(NEOIConfig.DisplayMode.values())
                .withInitialValue(NEOIConfig.DISPLAY_MODE.get())
                .create(this.width / 2 - 100, modeY, 200, btnH,
                        Component.literal("Display Mode"),
                        (btn, val) -> NEOIConfig.DISPLAY_MODE.set(val)));

        // ── Done button ───────────────────────────────────────────────
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE,
                btn -> this.minecraft.setScreen(parent))
                .bounds(this.width / 2 - 75, modeY + btnH + padY, 150, btnH)
                .build());
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui);
        gui.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFFFFAA00);
        super.render(gui, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }
}
