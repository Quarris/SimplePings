package dev.quarris.simplepings.client;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dev.quarris.simplepings.ModRef;
import dev.quarris.simplepings.network.PacketHandler;
import dev.quarris.simplepings.network.message.ServerBoundPingRequestPacket;
import dev.quarris.simplepings.ping.PingInfo;
import dev.quarris.simplepings.registry.KeyBindSetup;
import dev.quarris.simplepings.util.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Timer;

public class ClientEventHandler {

    private static final Timer MINECRAFT_TIMER = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_71428_T", "timer");

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;

        ModRef.proxy.getPingManager().tick();

        if (KeyBindSetup.pingKeyBinding.isPressed()) {
            float partialTicks = MINECRAFT_TIMER.renderPartialTicks;
            MovingObjectPosition rayHit = PlayerUtils.getPingRayHit(Minecraft.getMinecraft().thePlayer, partialTicks);
            if (rayHit == null) {
                return;
            }

            PingInfo pingInfo = PingInfo.fromRay(Minecraft.getMinecraft().thePlayer, rayHit);
            if (pingInfo == null) {
                return;
            }

            //ModRef.proxy.getPingManager().addPing(Minecraft.getMinecraft().thePlayer, pingInfo);
            PacketHandler.INSTANCE.sendToServer(new ServerBoundPingRequestPacket());
        }
    }
}
