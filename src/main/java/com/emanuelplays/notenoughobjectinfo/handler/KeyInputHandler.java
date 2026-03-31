package com.emanuelplays.notenoughobjectinfo.handler;

import com.emanuelplays.notenoughobjectinfo.client.NEOIKeys;
import com.emanuelplays.notenoughobjectinfo.config.NEOIConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // Toggle entire HUD
        if (NEOIKeys.TOGGLE_HUD.consumeClick()) {
            HUDRenderHandler.hudVisible = !HUDRenderHandler.hudVisible;
            toast(mc, HUDRenderHandler.hudVisible
                    ? "§a[NEOI] §fHUD §aEnabled"
                    : "§c[NEOI] §fHUD §cDisabled");
        }

        // Cycle display mode
        if (NEOIKeys.CYCLE_MODE.consumeClick()) {
            NEOIConfig.DisplayMode next = switch (NEOIConfig.DISPLAY_MODE.get()) {
                case COMPACT -> NEOIConfig.DisplayMode.NORMAL;
                case NORMAL  -> NEOIConfig.DisplayMode.VERBOSE;
                case VERBOSE -> NEOIConfig.DisplayMode.COMPACT;
            };
            NEOIConfig.DISPLAY_MODE.set(next);
            toast(mc, "§a[NEOI] §fMode: §e" + next.name());
        }

        // Toggle NBT
        if (NEOIKeys.TOGGLE_NBT.consumeClick()) {
            HUDRenderHandler.showNbt = !HUDRenderHandler.showNbt;
            toast(mc, HUDRenderHandler.showNbt
                    ? "§a[NEOI] §fNBT display §aOn"
                    : "§c[NEOI] §fNBT display §cOff");
        }

        // Toggle biome panel
        if (NEOIKeys.TOGGLE_BIOME.consumeClick()) {
            HUDRenderHandler.showBiome = !HUDRenderHandler.showBiome;
            toast(mc, HUDRenderHandler.showBiome
                    ? "§a[NEOI] §fBiome panel §aOn"
                    : "§c[NEOI] §fBiome panel §cOff");
        }

        // Toggle coordinate overlay
        if (NEOIKeys.TOGGLE_COORDS.consumeClick()) {
            HUDRenderHandler.showCoords = !HUDRenderHandler.showCoords;
            toast(mc, HUDRenderHandler.showCoords
                    ? "§a[NEOI] §fCoordinates §aOn"
                    : "§c[NEOI] §fCoordinates §cOff");
        }

        // Toggle player stats panel
        if (NEOIKeys.TOGGLE_PLAYER.consumeClick()) {
            HUDRenderHandler.showPlayer = !HUDRenderHandler.showPlayer;
            toast(mc, HUDRenderHandler.showPlayer
                    ? "§a[NEOI] §fPlayer stats §aOn"
                    : "§c[NEOI] §fPlayer stats §cOff");
        }

        // Toggle fluid panel
        if (NEOIKeys.TOGGLE_FLUID.consumeClick()) {
            HUDRenderHandler.showFluid = !HUDRenderHandler.showFluid;
            toast(mc, HUDRenderHandler.showFluid
                    ? "§a[NEOI] §fFluid panel §aOn"
                    : "§c[NEOI] §fFluid panel §cOff");
        }

        // Cycle config preset (placeholder — wired to config reload)
        if (NEOIKeys.NEXT_PRESET.consumeClick()) {
            toast(mc, "§e[NEOI] §fPreset cycling not yet configured.");
        }
    }

    private static void toast(Minecraft mc, String msg) {
        if (mc.player != null)
            mc.player.displayClientMessage(Component.literal(msg), true);
    }
}
