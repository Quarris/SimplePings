package dev.quarris.simplepings.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import dev.quarris.simplepings.ServerEventHandler;
import dev.quarris.simplepings.client.ClientEventHandler;
import dev.quarris.simplepings.client.ClientPingManager;
import dev.quarris.simplepings.client.RenderIconHandler;
import dev.quarris.simplepings.registry.KeyBindSetup;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ClientRegistry.registerKeyBinding(KeyBindSetup.pingKeyBinding);
        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new RenderIconHandler());
        this.pingManager = new ClientPingManager(Minecraft.getMinecraft());
    }

    @Override
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {}

    @Override
    public EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
}
