package dev.quarris.simplepings.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dev.quarris.simplepings.ModRef;
import dev.quarris.simplepings.network.message.ClientBoundAddPingPacket;
import dev.quarris.simplepings.network.message.ServerBoundPingRequestPacket;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModRef.ID);

    public static void init() {
        INSTANCE.registerMessage(ServerBoundPingRequestPacket.Handler.class, ServerBoundPingRequestPacket.class, 0, Side.SERVER);
        INSTANCE.registerMessage(ClientBoundAddPingPacket.Handler.class, ClientBoundAddPingPacket.class, 1, Side.CLIENT);
    }

}
