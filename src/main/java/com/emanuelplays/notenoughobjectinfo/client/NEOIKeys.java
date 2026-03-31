package com.emanuelplays.notenoughobjectinfo.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

@OnlyIn(Dist.CLIENT)
public class NEOIKeys {

    public static final KeyMapping TOGGLE_HUD = new KeyMapping(
            "key.neoi.toggle_hud",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_H,
            "key.categories.neoi"
    );

    public static final KeyMapping CYCLE_MODE = new KeyMapping(
            "key.neoi.cycle_mode",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_J,
            "key.categories.neoi"
    );

    public static final KeyMapping TOGGLE_NBT = new KeyMapping(
            "key.neoi.toggle_nbt",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_N,
            "key.categories.neoi"
    );

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_HUD);
        event.register(CYCLE_MODE);
        event.register(TOGGLE_NBT);
    }
}
