package dev.quarris.simplepings;

import cpw.mods.fml.common.SidedProxy;
import dev.quarris.simplepings.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;

public class ModRef {

    public static final String ID = "simplepings";
    public static final String VERSION = "1.0";

    @SidedProxy(modId = ID, clientSide = "dev.quarris.simplepings.proxy.ClientProxy", serverSide = "dev.quarris.simplepings.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static ResourceLocation res(String name) {
        return new ResourceLocation(ID, name);
    }
}
