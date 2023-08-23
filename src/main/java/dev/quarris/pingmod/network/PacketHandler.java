package dev.quarris.pingmod.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dev.quarris.pingmod.ModRef;
import dev.quarris.pingmod.PingMod;
import dev.quarris.pingmod.network.message.PingMessage;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModRef.ID);

    public static void init() {
        INSTANCE.registerMessage(PingMessage.PingMessageHandler.class, PingMessage.class, 0, Side.SERVER);
        INSTANCE.registerMessage(PingMessage.PingMessageHandler.class, PingMessage.class, 1, Side.CLIENT);
    }

}
