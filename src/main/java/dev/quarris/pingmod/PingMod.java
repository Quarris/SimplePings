package dev.quarris.pingmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = ModRef.ID, version = ModRef.VERSION)
public class PingMod {

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ModRef.proxy.init(event);
    }
}
