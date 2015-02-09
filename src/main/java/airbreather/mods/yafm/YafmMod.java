package airbreather.mods.yafm;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import airbreather.mods.airbreathercore.mod.IModLifecycleManager;
import airbreather.mods.airbreathercore.mod.IModule;
import airbreather.mods.airbreathercore.mod.ModLifecycleManager;

import static com.google.common.base.Preconditions.checkNotNull;

@Mod(modid = YafmConstants.ModID, name = YafmConstants.ModName)
public final class YafmMod
{
    private final MCAAddons mcaAddons;
    private final IModLifecycleManager modLifecycleManager;

    public YafmMod(final IModule module, final IModLifecycleManager modLifecycleManager)
    {
        this.mcaAddons = new MCAAddons(checkNotNull(module, "module"));
        this.modLifecycleManager = checkNotNull(modLifecycleManager, "modLifecycleManager");
    }

    // Either a parameterless constructor or a parameterless static factory
    // method is required for FML to load us.
    @Mod.InstanceFactory
    private static YafmMod CreateInstance()
    {
        IModule module = new YafmModule();
        IModLifecycleManager modLifecycleManager = new ModLifecycleManager(module);
        return new YafmMod(module, modLifecycleManager);
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
        this.mcaAddons.Register();
    }
}
