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

        // Toggle HUD visibility
        if (NEOIKeys.TOGGLE_HUD.consumeClick()) {
            HUDRenderHandler.hudVisible = !HUDRenderHandler.hudVisible;
            String msg = HUDRenderHandler.hudVisible
                    ? "§a[NEOI] §fHUD §aEnabled"
                    : "§a[NEOI] §fHUD §cDisabled";
            mc.player.displayClientMessage(Component.literal(msg), true);
        }

        // Cycle display mode
        if (NEOIKeys.CYCLE_MODE.consumeClick()) {
            NEOIConfig.DisplayMode current = NEOIConfig.DISPLAY_MODE.get();
            NEOIConfig.DisplayMode next = switch (current) {
                case COMPACT -> NEOIConfig.DisplayMode.NORMAL;
                case NORMAL  -> NEOIConfig.DisplayMode.VERBOSE;
                case VERBOSE -> NEOIConfig.DisplayMode.COMPACT;
            };
            NEOIConfig.DISPLAY_MODE.set(next);
            mc.player.displayClientMessage(
                    Component.literal("§a[NEOI] §fMode: §e" + next.name()), true);
        }

        // Toggle NBT display
        if (NEOIKeys.TOGGLE_NBT.consumeClick()) {
            HUDRenderHandler.showNbt = !HUDRenderHandler.showNbt;
            String msg = HUDRenderHandler.showNbt
                    ? "§a[NEOI] §fNBT display §aOn"
                    : "§a[NEOI] §fNBT display §cOff";
            mc.player.displayClientMessage(Component.literal(msg), true);
        }
    }
}
