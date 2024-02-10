package dev.quarris.simplepings.client;

import com.google.common.collect.ImmutableSet;
import dev.quarris.simplepings.ping.Ping;
import dev.quarris.simplepings.ping.PingInfo;
import dev.quarris.simplepings.ping.PingManager;
import net.minecraft.client.Minecraft;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ClientPingManager extends PingManager {

    private final Minecraft client;
    private final Set<Ping> pings = new HashSet<Ping>();

    public ClientPingManager(Minecraft client) {
        this.client = client;
    }

    @Override
    public void tick() {
        Iterator<Ping> ite = this.pings.iterator();
        while (ite.hasNext()) {
            Ping ping = ite.next();
            ping.tick();
            if (!ping.isAlive()) {
                ite.remove();
            }
        }
    }


    /**
     * Computes the renderable ping and stores/renders it for the duration of the animation.
     *
     * @param pPingInfo The ping info.
     */
    @Override
    public void addPing(PingInfo pPingInfo) {
        this.pings.add(new Ping(pPingInfo, 100));
    }

    @Override
    public Set<Ping> getPings() {
        return ImmutableSet.copyOf(this.pings);
    }
}
