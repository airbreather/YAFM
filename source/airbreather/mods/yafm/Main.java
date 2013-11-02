package airbreather.mods.yafm;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import airbreather.mods.airbreathercore.CustomConfiguration;
import airbreather.mods.airbreathercore.RecipeConfiguration;
import airbreather.mods.airbreathercore.IRecipeRegistrar;
import airbreather.mods.airbreathercore.RecipeRegistrar;
import airbreather.mods.yafm.YafmConstants;

// Lots of this is boilerplate from the Forge tutorial -- not sure how much I can just delete.
@Mod(modid = YafmConstants.ModID, name = "Yet Another Food Mod", version = YafmConstants.CurrentVersion)
@NetworkMod(clientSideRequired = true)
public final class Main
{
    @Instance(value = YafmConstants.ModID)
    public static Main instance;

    @SidedProxy(clientSide = "airbreather.mods.yafm.CommonProxy",
                serverSide = "airbreather.mods.yafm.CommonProxy")
    public static CommonProxy proxy;

    private final IRecipeRegistrar recipeRegistrar;
    private final CustomConfiguration configuration;

    public Main()
    {
        this(new RecipeRegistrar(),
             new YafmConfigurationAdapter());
    }

    public Main(final RecipeRegistrar recipeRegistrar, final CustomConfiguration configuration)
    {
        this.recipeRegistrar = recipeRegistrar;
        this.configuration = configuration;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        this.configuration.Initialize(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        proxy.registerRenderers();

        RecipeConfiguration recipeConfiguration = this.configuration.GetRecipeConfiguration();
        this.recipeRegistrar.RegisterRecipes(recipeConfiguration.GetRecipes());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
