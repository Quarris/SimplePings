package dev.quarris.pingmod;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

public class ModConfig {

    public static Configuration config;

    public static double pingRange = 64;
    public static double raycastDistance = 64;

    public static void loadConfigs() {
        config = new Configuration(new File(Loader.instance().getConfigDir(), "pingmod.cfg"));
        syncConfig(true);
    }

    private static void syncConfig(boolean load) {
        List<String> propOrder = new ArrayList<String>();

        if (!config.isChild) {
            if (load) {
                config.load();
            }
        }

        Property prop;

        prop = config.get(CATEGORY_GENERAL, "pingRange", 64);
        prop.comment = "The max distance that the players will be able to receive pings from.";
        pingRange = prop.getDouble(pingRange);
        propOrder.add(prop.getName());

        prop = config.get(CATEGORY_GENERAL, "raycastDistance", 64);
        prop.comment = "The max distance players will be able to ping blocks from.";
        raycastDistance = prop.getDouble(raycastDistance);
        propOrder.add(prop.getName());

        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static class EventHandler {
        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (ModRef.ID.equals(event.modID) && !event.isWorldRunning) {
                if (Configuration.CATEGORY_GENERAL.equals(event.configID)) {
                    syncConfig(false);
                }
            }
        }
    }

}
