package airbreather.mods.yafm;

import airbreather.mods.airbreathercore.CustomConfiguration;
import airbreather.mods.airbreathercore.item.ItemRegistrar;
import airbreather.mods.airbreathercore.item.ItemRegistry;
import airbreather.mods.airbreathercore.mod.ModuleBase;

final class YafmModule extends ModuleBase
{
    private final CustomConfiguration customConfiguration;

    public YafmModule()
    {
        super();

        YafmItemConfiguration itemConfiguration = new YafmItemConfiguration();
        YafmRecipeConfiguration recipeConfiguration = new YafmRecipeConfiguration(itemConfiguration);

        ItemRegistry itemRegistry = this.GetItemRegistry();
        YafmSheepDropEventHandler sheepDropHandler = new YafmSheepDropEventHandler(itemConfiguration, itemRegistry);
        YafmSquidDropEventHandler squidDropHandler = new YafmSquidDropEventHandler(itemConfiguration, itemRegistry);
        YafmEventConfiguration eventConfiguration = new YafmEventConfiguration(sheepDropHandler, squidDropHandler);

        this.customConfiguration = new YafmConfigurationAdapter(itemConfiguration,
                                                                recipeConfiguration,
                                                                eventConfiguration);
    }

    @Override
    public ItemRegistrar GetItemRegistrar() { return new YafmItemRegistrar(); }
    @Override
    public CustomConfiguration GetCustomConfiguration() { return this.customConfiguration; }
}
