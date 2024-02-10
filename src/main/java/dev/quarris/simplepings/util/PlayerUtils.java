package dev.quarris.simplepings.util;

import dev.quarris.simplepings.ModConfigs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class PlayerUtils {

    /**
     * Casts a ray form the given players eyes, with the given partial ticks for client accuracy (servers should use 1.0)
     * @param player The player from which the raycast originates.
     * @param partialTicks
     * @return
     */
    public static MovingObjectPosition getPingRayHit(EntityPlayer player, float partialTicks) {
        Vec3 eyePos = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3 lookVec = player.getLook(partialTicks);
        Vec3 targetVec = Vec3.createVectorHelper(lookVec.xCoord * ModConfigs.raycastDistance, lookVec.yCoord * ModConfigs.raycastDistance, lookVec.zCoord * ModConfigs.raycastDistance);

        MovingObjectPosition rayHit = player.worldObj.rayTraceBlocks(eyePos, eyePos.addVector(targetVec.xCoord, targetVec.yCoord, targetVec.zCoord));
        if (rayHit == null) {
            return null;
        }

        return rayHit;
    }

}
