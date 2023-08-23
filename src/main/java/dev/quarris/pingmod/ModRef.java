package dev.quarris.pingmod;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.SidedProxy;
import dev.quarris.pingmod.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ModRef {

    public static final String ID = "pingmod";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide = "dev.quarris.pingmod.proxy.ClientProxy", serverSide = "dev.quarris.pingmod.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static ResourceLocation res(String name) {
        return new ResourceLocation(ID, name);
    }
}
