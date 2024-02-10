package dev.quarris.simplepings.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dev.quarris.simplepings.ModRef;
import dev.quarris.simplepings.ping.Ping;
import dev.quarris.simplepings.ping.PingInfo;
import dev.quarris.simplepings.ping.PingManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import scala.Int;

public class RenderIconHandler {

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (Ping ping : ModRef.proxy.getPingManager().getPings()) {
            if (ping.isAlive()) {
                renderPing(ping, event.partialTicks);
            }
        }
    }

    private static void renderPing(Ping ping, float partialTicks) {
        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;

        double targetX = ping.getInfo().getPosition().xCoord;
        double targetY = ping.getInfo().getPosition().yCoord;
        double targetZ = ping.getInfo().getPosition().zCoord;

        double renderX = targetX - RenderManager.renderPosX;
        double renderY = targetY - RenderManager.renderPosY;
        double renderZ = targetZ - RenderManager.renderPosZ;

        float alpha = 1 - ping.getLifetime();

        GL11.glPushMatrix();
        GL11.glTranslated(renderX, renderY, renderZ);
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);

        // Disable depth testing
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        // Enable blending for transparency
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_LIGHTING);

        textureManager.bindTexture(ModRef.res("textures/icon.png"));

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(1, 1, 1, alpha);
        tessellator.addVertexWithUV(-0.5, 0.5, 0, 0, 0);
        tessellator.addVertexWithUV(0.5, 0.5, 0, 1, 0);
        tessellator.addVertexWithUV(0.5, -0.5, 0, 1, 1);
        tessellator.addVertexWithUV(-0.5, -0.5, 0, 0, 1);
        tessellator.draw();

        // Disable depth testing and blending
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

        double textOffsetY = 0.8; // Adjust to position the text

        GL11.glPushMatrix();
        GL11.glTranslated(renderX, renderY + textOffsetY, renderZ);

        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-0.025F, -0.025F, 0.025F);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int textAlpha = Math.max(4, (int) (alpha * 255)); // Text alpha for some reason resets to max when < 4
        font.drawString(ping.getInfo().getPlayerName(), -font.getStringWidth(ping.getInfo().getPlayerName()) / 2, 0, (textAlpha << 24) | 0xFFFFFF);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
    }
}
