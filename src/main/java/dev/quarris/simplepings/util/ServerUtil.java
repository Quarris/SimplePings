package dev.quarris.simplepings.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class ServerUtil {

    public static EntityPlayerMP getPlayerByUUID(MinecraftServer server, UUID id) {
        for (int i = 0; i < server.getConfigurationManager().playerEntityList.size(); i++) {
            EntityPlayerMP player = ((EntityPlayerMP) server.getConfigurationManager().playerEntityList.get(i));

            if (id.equals(player.getUniqueID())) {
                return player;
            }
        }

        return null;
    }

}
