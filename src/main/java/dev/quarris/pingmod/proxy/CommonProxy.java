package dev.quarris.pingmod.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import dev.quarris.pingmod.ModConfig;
import dev.quarris.pingmod.network.PacketHandler;
import dev.quarris.pingmod.ping.PingInfo;

public class CommonProxy {

    public void init(FMLInitializationEvent event) {
        PacketHandler.init();
        ModConfig.loadConfigs();
        FMLCommonHandler.instance().bus().register(new ModConfig.EventHandler());
    }

}
