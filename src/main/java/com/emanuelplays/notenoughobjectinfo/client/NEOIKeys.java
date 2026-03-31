package com.emanuelplays.notenoughobjectinfo.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

public class NEOIKeys {

    public static final KeyMapping TOGGLE_HUD = new KeyMapping(
            "key.neoi.toggle_hud", InputConstants.Type.KEYSYM, InputConstants.KEY_H, "key.categories.neoi");

    public static final KeyMapping CYCLE_MODE = new KeyMapping(
            "key.neoi.cycle_mode", InputConstants.Type.KEYSYM, InputConstants.KEY_J, "key.categories.neoi");

    public static final KeyMapping TOGGLE_NBT = new KeyMapping(
            "key.neoi.toggle_nbt", InputConstants.Type.KEYSYM, InputConstants.KEY_N, "key.categories.neoi");

    public static final KeyMapping TOGGLE_BIOME = new KeyMapping(
            "key.neoi.toggle_biome", InputConstants.Type.KEYSYM, InputConstants.KEY_B, "key.categories.neoi");

    public static final KeyMapping TOGGLE_COORDS = new KeyMapping(
            "key.neoi.toggle_coords", InputConstants.Type.KEYSYM, InputConstants.KEY_C, "key.categories.neoi");

    public static final KeyMapping TOGGLE_PLAYER = new KeyMapping(
            "key.neoi.toggle_player", InputConstants.Type.KEYSYM, InputConstants.KEY_P, "key.categories.neoi");

    public static final KeyMapping TOGGLE_FLUID = new KeyMapping(
            "key.neoi.toggle_fluid", InputConstants.Type.KEYSYM, InputConstants.KEY_F, "key.categories.neoi");

    public static final KeyMapping NEXT_PRESET = new KeyMapping(
            "key.neoi.next_preset", InputConstants.Type.KEYSYM, -1, "key.categories.neoi");

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_HUD);
        event.register(CYCLE_MODE);
        event.register(TOGGLE_NBT);
        event.register(TOGGLE_BIOME);
        event.register(TOGGLE_COORDS);
        event.register(TOGGLE_PLAYER);
        event.register(TOGGLE_FLUID);
        event.register(NEXT_PRESET);
    }
}
