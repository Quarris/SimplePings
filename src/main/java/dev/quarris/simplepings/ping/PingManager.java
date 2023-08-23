package dev.quarris.simplepings.ping;

import java.util.*;

public class PingManager {

    private static final Set<PingInfo> PINGS = new HashSet<PingInfo>();

    public static void tick() {
        Iterator<PingInfo> ite = PINGS.iterator();
        while (ite.hasNext()) {
            PingInfo ping = ite.next();
            ping.tick();
            if (!ping.isAlive()) {
                ite.remove();
            }
        }
    }

    public static void addPing(PingInfo ping) {
        Iterator<PingInfo> ite = PINGS.iterator();
        while (ite.hasNext()) {
            PingInfo checkPing = ite.next();
            if (checkPing.getRenderPosition().distanceTo(ping.getRenderPosition()) < 2) {
                ite.remove();
            }
        }
        PINGS.add(ping);
    }

    public static Collection<PingInfo> getPings() {
        return Collections.unmodifiableSet(PINGS);
    }

}
