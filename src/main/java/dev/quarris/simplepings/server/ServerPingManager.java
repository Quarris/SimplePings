package dev.quarris.simplepings.server;

import dev.quarris.simplepings.ModConfigs;
import dev.quarris.simplepings.network.PacketHandler;
import dev.quarris.simplepings.network.message.ClientBoundAddPingPacket;
import dev.quarris.simplepings.ping.Ping;
import dev.quarris.simplepings.ping.PingInfo;
import dev.quarris.simplepings.ping.PingManager;
import dev.quarris.simplepings.util.ServerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;

import java.util.*;

public class ServerPingManager extends PingManager {

    private final MinecraftServer server;

    private final Map<UUID, PingInfo> processingPings = new HashMap<UUID, PingInfo>();
    private int ticks;

    public ServerPingManager(MinecraftServer server) {
        this.server = server;
    }

    /**
     * Every interval (configurable) the server broadcasts its stored pings
     * to nearby players, excluding the original player.
     */
    @Override
    public void tick() {
        if (this.ticks++ >= ModConfigs.broadcastInterval) {
            for (Map.Entry<UUID, PingInfo> entry : this.processingPings.entrySet()) {
                UUID playerId = entry.getKey();
                PingInfo pingInfo = entry.getValue();
                EntityPlayerMP player = ServerUtil.getPlayerByUUID(this.server, playerId);
                // We don't need to send ping from players that are logged out,
                // are far from the ping OR are out of dimension.
                if (player == null ||
                    player.dimension != pingInfo.getDimension() ||
                    player.getDistanceSq(pingInfo.getX(), pingInfo.getY(), pingInfo.getZ()) > ModConfigs.pingViewRange * ModConfigs.pingViewRange) {
                    continue;
                }

                Vec3 position = pingInfo.getPosition();
                WorldServer level = this.server.worldServerForDimension(pingInfo.getDimension());
                double range = ModConfigs.pingViewRange;
                List<EntityPlayer> nearbyPlayers = level.getEntitiesWithinAABB(EntityPlayer.class,
                    AxisAlignedBB.getBoundingBox(
                        position.xCoord - range, position.yCoord - range, position.zCoord - range,
                        position.xCoord + range, position.yCoord + range, position.zCoord + range));

                for (EntityPlayer targetPlayer : nearbyPlayers) {
                    PacketHandler.INSTANCE.sendTo(new ClientBoundAddPingPacket(pingInfo), (EntityPlayerMP) targetPlayer);
                }

            }
            this.processingPings.clear();
            this.ticks = 0;
        }
    }

    /**
     * Adds the ping info to a processing list, which gets broadcasted to clients every interval (configurable).
     *
     * @param pPingInfo The ping info.
     */
    @Override
    public void addPing(PingInfo pPingInfo) {
        EntityPlayerMP player = ServerUtil.getPlayerByUUID(this.server, pPingInfo.getPlayerId());
        if (player == null) {
            return; // Player no longer exists.
        }

        this.processingPings.put(pPingInfo.getPlayerId(), pPingInfo);
    }

    @Override
    public Set<Ping> getPings() {
        return Collections.emptySet();
    }
}
