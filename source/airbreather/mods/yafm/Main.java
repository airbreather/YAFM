package airbreather.mods.yafm;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.IEventListener;

import airbreather.mods.airbreathercore.CustomConfiguration;
import airbreather.mods.airbreathercore.event.EventConfiguration;
import airbreather.mods.airbreathercore.event.EventSubscriber;
import airbreather.mods.airbreathercore.event.EventType;
import airbreather.mods.airbreathercore.event.ForgeEventSubscriber;
import airbreather.mods.airbreathercore.item.FmlItemRegistry;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemRegistrar;
import airbreather.mods.airbreathercore.item.ItemRegistry;
import airbreather.mods.airbreathercore.recipe.FmlRecipeRegistrar;
import airbreather.mods.airbreathercore.recipe.RecipeConfiguration;
import airbreather.mods.airbreathercore.recipe.RecipeRegistrar;

import static com.google.common.base.Preconditions.checkNotNull;

@Mod(modid = YafmConstants.ModID, name = "Yet Another Food Mod", version = YafmConstants.CurrentVersion)
public final class Main
{
    // The dependencies used to delegate specific mod responsibilities.
    private final ItemRegistry itemRegistry;
    private final ItemRegistrar itemRegistrar;
    private final RecipeRegistrar recipeRegistrar;
    private final EventSubscriber eventSubscriber;
    private final CustomConfiguration configuration;

    public Main()
    {
        this(new FmlItemRegistry());
    }

    private Main(ItemRegistry itemRegistry)
    {
        this(itemRegistry,
             new YafmItemRegistrar(),
             new FmlRecipeRegistrar(),
             new ForgeEventSubscriber(),
             new YafmConfigurationAdapter(itemRegistry));
    }

    public Main(final ItemRegistry itemRegistry,
                final ItemRegistrar itemRegistrar,
                final RecipeRegistrar recipeRegistrar,
                final EventSubscriber eventSubscriber,
                final CustomConfiguration configuration)
    {
        this.itemRegistry = checkNotNull(itemRegistry, "itemRegistry");
        this.itemRegistrar = checkNotNull(itemRegistrar, "itemRegistrar");
        this.recipeRegistrar = checkNotNull(recipeRegistrar, "recipeRegistrar");
        this.eventSubscriber = checkNotNull(eventSubscriber, "eventSubscriber");
        this.configuration = checkNotNull(configuration, "configuration");
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        checkNotNull(event, "event");
        this.configuration.Initialize(event.getSuggestedConfigurationFile());

        ItemConfiguration itemConfiguration = this.configuration.GetItemConfiguration();
        this.itemRegistrar.RegisterNewItems(itemConfiguration, this.itemRegistry);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // Register all the recipes.
        // This MUST be called during post-initialization, or else FmlItemRegistry won't have any items yet.
        RecipeConfiguration recipeConfiguration = this.configuration.GetRecipeConfiguration();
        this.recipeRegistrar.RegisterRecipes(recipeConfiguration, this.itemRegistry);

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
