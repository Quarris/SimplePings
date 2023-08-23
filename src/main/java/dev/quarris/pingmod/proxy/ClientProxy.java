package dev.quarris.pingmod.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import dev.quarris.pingmod.client.ClientEventHandler;
import dev.quarris.pingmod.client.RenderIconHandler;
import dev.quarris.pingmod.registry.KeyBindSetup;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ClientRegistry.registerKeyBinding(KeyBindSetup.pingKeyBinding);
        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new RenderIconHandler());
    }

}
