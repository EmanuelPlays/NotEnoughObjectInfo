package com.emanuelplays.notenoughobjectinfo;

import com.emanuelplays.notenoughobjectinfo.config.NEOIConfig;
import com.emanuelplays.notenoughobjectinfo.handler.TooltipHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(NotEnoughObjectInfo.MOD_ID)
public class NotEnoughObjectInfo {

    public static final String MOD_ID = "neoi";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NotEnoughObjectInfo() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        NEOIConfig.register(ModLoadingContext.get());

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(new TooltipHandler());

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ModLoadingContext.get().registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            (mc, parent) -> new com.emanuelplays.notenoughobjectinfo.client.NEOIScreen(parent)
                    )
            );
        }

        LOGGER.info("[NEOI] Not Enough Object Info loaded! Author: EmanuelPlays");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("[NEOI] Common setup complete.");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("[NEOI] Client setup complete.");
    }
}
