package dev.quarris.pingmod.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dev.quarris.pingmod.ModConfig;
import dev.quarris.pingmod.ModRef;
import dev.quarris.pingmod.network.PacketHandler;
import dev.quarris.pingmod.network.message.PingMessage;
import dev.quarris.pingmod.ping.PingInfo;
import dev.quarris.pingmod.ping.PingManager;
import dev.quarris.pingmod.registry.KeyBindSetup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class ClientEventHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        PingManager.tick();
        if (KeyBindSetup.pingKeyBinding.isPressed()) {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            Vec3 playerPos = player.getPosition(0);
            Vec3 lookVec = player.getLookVec();
            Vec3 targetVec = Vec3.createVectorHelper(lookVec.xCoord * ModConfig.raycastDistance, lookVec.yCoord * ModConfig.raycastDistance, lookVec.zCoord * ModConfig.raycastDistance);

            MovingObjectPosition rayHit = Minecraft.getMinecraft().theWorld.rayTraceBlocks(playerPos, playerPos.addVector(targetVec.xCoord, targetVec.yCoord, targetVec.zCoord));
            if (rayHit != null && rayHit.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                PacketHandler.INSTANCE.sendToServer(new PingMessage(rayHit.hitVec.addVector(0, 1, 0)));
            }
        }
    }
}
