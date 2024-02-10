package dev.quarris.simplepings.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dev.quarris.simplepings.ModRef;
import dev.quarris.simplepings.ping.PingInfo;
import io.netty.buffer.ByteBuf;

public class ClientBoundAddPingPacket implements IMessage {

    private PingInfo pingInfo;

    public ClientBoundAddPingPacket() {

    }

    public ClientBoundAddPingPacket(PingInfo pingInfo) {
        this.pingInfo = pingInfo;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        this.pingInfo.writeTo(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pingInfo = PingInfo.fromBuf(buf);
    }

    public static class Handler implements IMessageHandler<ClientBoundAddPingPacket, IMessage> {

        @Override
        public IMessage onMessage(ClientBoundAddPingPacket message, MessageContext ctx) {
            ModRef.proxy.getPingManager().addPing(message.pingInfo);
            return null;
        }
    }
}
