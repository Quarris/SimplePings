package dev.quarris.simplepings.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.UUID;

public class LevelUtils {

    /**
     * Iterates over all players in the world to find the one with the given id.
     * @param level
     * @param uuid
     * @return
     */
    public static EntityPlayer getPlayerByUUID(World level, UUID uuid) {
        for (Object o : level.playerEntities) {
            EntityPlayer player = (EntityPlayer) o;
            if (player.getUniqueID().equals(uuid)) {
                return player;
            }
        }

        return null;
    }

}
