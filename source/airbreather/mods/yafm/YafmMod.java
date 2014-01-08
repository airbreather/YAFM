package airbreather.mods.yafm;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import airbreather.mods.airbreathercore.mod.ModBase;

@Mod(modid = YafmConstants.ModID, name = YafmConstants.ModID, version = YafmConstants.CurrentVersion)
public final class YafmMod extends ModBase
{
    public YafmMod()
    {
        super(new YafmModule());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        this.preInitCore(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        this.postInitCore(event);
    }
}
