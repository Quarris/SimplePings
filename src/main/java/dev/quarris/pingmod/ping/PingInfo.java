package dev.quarris.pingmod.ping;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class PingInfo {

    private String display;
    private Vec3 position;
    private int maxTimer;
    private int timer;

    public PingInfo(String display, Vec3 position, int timer) {
        this.display = display;
        this.position = position;
        this.maxTimer = timer;
    }

    public void tick() {
        if (this.timer < this.maxTimer) {
            this.timer++;
        }
    }

    public String getDisplay() {
        return this.display;
    }

    public boolean isAlive() {
        return this.timer < this.maxTimer;
    }

    public float getAlpha() {
        if (this.timer < this.maxTimer - 20) {
            return 1;
        }

        return (this.maxTimer - this.timer) / 20f;
    }

    public Vec3 getRenderPosition() {
        return this.position.addVector(0, Math.sin(MathHelper.clamp_int(this.timer, 0, 10) / Math.PI) * 0.25, 0);
    }
}
