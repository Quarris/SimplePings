package dev.quarris.simplepings;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;

@SuppressWarnings("unused")
@Mod(modid = ModRef.ID, version = ModRef.VERSION)
public class SimplePings {

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRef.proxy.preInit(event);
    }

    @EventHandler
    public void serverInit(FMLServerAboutToStartEvent event) {
        ModRef.proxy.serverAboutToStart(event);
    }
}
