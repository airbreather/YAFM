package airbreather.mods.yafm;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraftforge.event.IEventListener;
import airbreather.mods.airbreathercore.CustomConfiguration;
import airbreather.mods.airbreathercore.event.EventConfiguration;
import airbreather.mods.airbreathercore.event.EventSubscriber;
import airbreather.mods.airbreathercore.event.EventType;
import airbreather.mods.airbreathercore.event.ForgeEventSubscriber;
import airbreather.mods.airbreathercore.recipe.RecipeConfiguration;
import airbreather.mods.airbreathercore.recipe.RecipeRegistrar;
import airbreather.mods.airbreathercore.recipe.FmlRecipeRegistrar;

@Mod(modid = YafmConstants.ModID, name = "Yet Another Food Mod", version = YafmConstants.CurrentVersion)
@NetworkMod(clientSideRequired = true)
public final class Main
{
    // The dependencies used to delegate specific mod responsibilities.
    private final RecipeRegistrar recipeRegistrar;
    private final EventSubscriber eventSubscriber;
    private final CustomConfiguration configuration;

    public Main()
    {
        this(new FmlRecipeRegistrar(),
             new ForgeEventSubscriber(),
             new YafmConfigurationAdapter());
    }

    public Main(final RecipeRegistrar recipeRegistrar,
                final EventSubscriber eventSubscriber,
                final CustomConfiguration configuration)
    {
        this.recipeRegistrar = recipeRegistrar;
        this.eventSubscriber = eventSubscriber;
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
        // Register all the recipes.
        RecipeConfiguration recipeConfiguration = this.configuration.GetRecipeConfiguration();
        this.recipeRegistrar.RegisterRecipes(recipeConfiguration.GetRecipes());

        // Now register all the event handlers, particularly those for mob drop events.
        this.eventSubscriber.Initialize();
        EventConfiguration eventConfiguration = this.configuration.GetEventConfiguration();
        for (EventType eventType : eventConfiguration.GetRecognizedEventTypes())
        {
            for (IEventListener handler : eventConfiguration.GetEventHandlers(eventType))
            {
                this.eventSubscriber.SubscribeToEvent(eventType, handler);
            }
        }
    }
}
