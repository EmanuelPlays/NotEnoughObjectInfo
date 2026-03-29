package com.emanuelplays.notenoughobjectinfo.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

/**
 * Factory used by Forge / ModMenu to open NEOI's settings screen.
 * Register this with the IExtensionPoint in the mod constructor if desired.
 */
@OnlyIn(Dist.CLIENT)
public class NEOIConfigScreen implements Function<Screen, Screen> {

    @Override
    public Screen apply(Screen parent) {
        return new NEOIScreen(parent);
    }
}
