package dev.quarris.simplepings.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import dev.quarris.simplepings.ModConfigs;
import dev.quarris.simplepings.ServerEventHandler;
import dev.quarris.simplepings.network.PacketHandler;
import dev.quarris.simplepings.ping.PingManager;
import dev.quarris.simplepings.server.ServerPingManager;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {

    protected PingManager pingManager;

    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.init();
        ModConfigs.loadConfigs();
        FMLCommonHandler.instance().bus().register(new ModConfigs.EventHandler());
        FMLCommonHandler.instance().bus().register(new ServerEventHandler());
    }

    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        this.pingManager = new ServerPingManager(event.getServer());
    }

    public PingManager getPingManager() {
        return this.pingManager;
    }

    public EntityPlayer getPlayer() {
        return null;
    }
}
