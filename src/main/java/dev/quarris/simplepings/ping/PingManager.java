package dev.quarris.simplepings.ping;

import java.util.Set;

public abstract class PingManager {

    /**
     * Adds ping to the respective manager.
     * @param pPingInfo The ping info.
     */
    public abstract void addPing(PingInfo pPingInfo);

    /**
     * Updates the respective manager.
     */
    public abstract void tick();

    public abstract Set<Ping> getPings();

}
