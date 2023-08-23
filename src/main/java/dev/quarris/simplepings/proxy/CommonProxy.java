package dev.quarris.simplepings.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import dev.quarris.simplepings.ModConfig;
import dev.quarris.simplepings.network.PacketHandler;

public class CommonProxy {

    public void init(FMLInitializationEvent event) {
        PacketHandler.init();
        ModConfig.loadConfigs();
        FMLCommonHandler.instance().bus().register(new ModConfig.EventHandler());
    }

}
