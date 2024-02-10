package dev.quarris.simplepings.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dev.quarris.simplepings.ModRef;
import dev.quarris.simplepings.ping.PingInfo;
import dev.quarris.simplepings.util.PlayerUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MovingObjectPosition;

public class ServerBoundPingRequestPacket implements IMessage {

    public ServerBoundPingRequestPacket() {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<ServerBoundPingRequestPacket, IMessage> {

        @Override
        public IMessage onMessage(ServerBoundPingRequestPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            MovingObjectPosition rayHit = PlayerUtils.getPingRayHit(player, 1);
            if (rayHit == null) {
                return null;
            }

            PingInfo pingInfo = PingInfo.fromRay(player, rayHit);
            if (pingInfo == null) {
                return null;
            }

            ModRef.proxy.getPingManager().addPing(pingInfo);
            return null;
        }
    }
}
