package dev.quarris.simplepings.ping;

public class Ping {

    private final PingInfo pingInfo;
    private final int lifespan;

    private int age;

    public Ping(PingInfo pingInfo, int lifespan) {
        this.pingInfo = pingInfo;
        this.lifespan = lifespan;
    }

    public void tick() {
        this.age++;
    }

    public boolean isAlive() {
        return this.age <= this.lifespan;
    }

    /**
     * @return the float percentage of this pings life.
     */
    public float getLifetime() {
        return this.age / (float) this.lifespan;
    }

    public PingInfo getInfo() {
        return this.pingInfo;
    }
}
