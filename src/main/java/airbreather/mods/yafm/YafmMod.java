package airbreather.mods.yafm;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import airbreather.mods.airbreathercore.mod.IModLifecycleManager;
import airbreather.mods.airbreathercore.mod.IModule;
import airbreather.mods.airbreathercore.mod.ModLifecycleManager;

import static com.google.common.base.Preconditions.checkNotNull;

@Mod(modid = YafmConstants.ModID, name = YafmConstants.ModName)
public final class YafmMod
{
    private final IModLifecycleManager modLifecycleManager;

    public YafmMod(final IModLifecycleManager modLifecycleManager)
    {
        this.modLifecycleManager = checkNotNull(modLifecycleManager, "modLifecycleManager");
    }

    // Either a parameterless constructor or a parameterless static factory
    // method is required for FML to load us.
    @Mod.InstanceFactory
    private static YafmMod CreateInstance()
    {
        IModule module = new YafmModule();
        IModLifecycleManager modLifecycleManager = new ModLifecycleManager(module);
        return new YafmMod(modLifecycleManager);
    }

    @Mod.EventHandler
    private void PreInit(FMLPreInitializationEvent event)
    {
        this.modLifecycleManager.OnPreInit(event);
    }

    @Mod.EventHandler
    private void Init(FMLInitializationEvent event)
    {
        this.modLifecycleManager.OnInit(event);
    }

    @Mod.EventHandler
    private void PostInit(FMLPostInitializationEvent event)
    {
        this.modLifecycleManager.OnPostInit(event);
    }
}
