package dev.quarris.simplepings;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class ServerEventHandler {

    @SubscribeEvent
    public void tickServer(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;

        ModRef.proxy.getPingManager().tick();
    }

}
