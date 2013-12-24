package airbreather.mods.yafm;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import airbreather.mods.airbreathercore.CustomConfigurationBase;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemRegistry;
import airbreather.mods.airbreathercore.event.EventConfiguration;
import airbreather.mods.airbreathercore.recipe.RecipeConfiguration;

// Implements CustomConfiguration using the standard Forge configuration pattern, given a File.
final class YafmConfigurationAdapter extends CustomConfigurationBase
{
    // TODO: Possible to make it so YafmEventConfiguration doesn't have to know about this?
    private final ItemRegistry itemRegistry;

    private final YafmItemConfiguration itemConfiguration = new YafmItemConfiguration();
    private final YafmRecipeConfiguration recipeConfiguration = new YafmRecipeConfiguration(this.itemConfiguration);
    private final YafmEventConfiguration eventConfiguration;

    public YafmConfigurationAdapter(final ItemRegistry itemRegistry)
    {
        this.itemRegistry = itemRegistry;
        this.eventConfiguration = new YafmEventConfiguration(this.itemConfiguration, this.itemRegistry);
    }

    @Override
    public void Initialize(File configurationFile)
    {
        Configuration forgeConfiguration = new Configuration(configurationFile);
        forgeConfiguration.load();

        if (ShouldEnableFriedEggRecipe(forgeConfiguration))
        {
            this.recipeConfiguration.EnableFriedEggRecipe();
        }

        if (ShouldEnableCookedMuttonRecipe(forgeConfiguration))
        {
            this.recipeConfiguration.EnableCookedMuttonRecipe();
        }

        if (ShouldEnableRawMuttonDrops(forgeConfiguration))
        {
            this.eventConfiguration.EnableRawMuttonDrops();
        }

        if (ShouldEnableCookedSquidRecipe(forgeConfiguration))
        {
            this.recipeConfiguration.EnableCookedSquidRecipe();
        }

        if (ShouldEnableRawSquidDrops(forgeConfiguration))
        {
            this.eventConfiguration.EnableRawSquidDrops();
        }

        // TODO: Skip saving if we aren't in "create-initial" mode.
        forgeConfiguration.save();
    }

    @Override
    public RecipeConfiguration GetRecipeConfiguration()
    {
        return this.recipeConfiguration;
    }

    @Override
    public ItemConfiguration GetItemConfiguration()
    {
        return this.itemConfiguration;
    }

    @Override
    public EventConfiguration GetEventConfiguration()
    {
        return this.eventConfiguration;
    }

    private static boolean ShouldEnableFriedEggRecipe(Configuration forgeConfiguration)
    {
        String enableFriedEggRecipePropertyName = "enableFriedEggRecipe";
        boolean enableFriedEggRecipeDefault = true;
        String enableFriedEggRecipeComment = "Enable the Egg --> Fried Egg smelting recipe?  true/false (true is the default)";

        Property enableFriedEggRecipeProperty = forgeConfiguration.get(Configuration.CATEGORY_GENERAL, enableFriedEggRecipePropertyName, enableFriedEggRecipeDefault, enableFriedEggRecipeComment);
        return enableFriedEggRecipeProperty.getBoolean(enableFriedEggRecipeDefault);
    }

    private static boolean ShouldEnableRawMuttonDrops(Configuration forgeConfiguration)
    {
        String enableRawMuttonDropsPropertyName = "enableRawMuttonDrops";
        boolean enableRawMuttonDropsDefault = true;
        String enableRawMuttonDropsComment = "Allow sheep to drop raw mutton?  true/false (true is the default)";

        Property enableRawMuttonDropsProperty = forgeConfiguration.get(Configuration.CATEGORY_GENERAL, enableRawMuttonDropsPropertyName, enableRawMuttonDropsDefault, enableRawMuttonDropsComment);
        return enableRawMuttonDropsProperty.getBoolean(enableRawMuttonDropsDefault);
    }

    private static boolean ShouldEnableRawSquidDrops(Configuration forgeConfiguration)
    {
        String enableRawSquidDropsPropertyName = "enableRawSquidDrops";
        boolean enableRawSquidDropsDefault = true;
        String enableRawSquidDropsComment = "Allow squid to drop raw squid?  true/false (true is the default)";

        Property enableRawSquidDropsProperty = forgeConfiguration.get(Configuration.CATEGORY_GENERAL, enableRawSquidDropsPropertyName, enableRawSquidDropsDefault, enableRawSquidDropsComment);
        return enableRawSquidDropsProperty.getBoolean(enableRawSquidDropsDefault);
    }

    private static boolean ShouldEnableCookedMuttonRecipe(Configuration forgeConfiguration)
    {
        // No point in making this configurable -- if you have raw mutton, then you should be allowed to cook it.
        ////String enableCookedMuttonRecipePropertyName = "enableCookedMuttonRecipe";
        ////boolean enableCookedMuttonRecipeDefault = true;
        ////String enableCookedMuttonRecipeComment = "Enable the Raw Mutton --> Cooked Mutton smelting recipe?  true/false (true is the default)";
        ////
        ////Property enableCookedMuttonRecipeProperty = forgeConfiguration.get(Configuration.CATEGORY_GENERAL, enableCookedMuttonRecipePropertyName, enableCookedMuttonRecipeDefault, enableCookedMuttonRecipeComment);
        ////return enableCookedMuttonRecipeProperty.getBoolean(enableCookedMuttonRecipeDefault);
        return true;
    }

    private static boolean ShouldEnableCookedSquidRecipe(Configuration forgeConfiguration)
    {
        // No point in making this configurable -- if you have raw squid, then you should be allowed to cook it.
        ////String enableCookedSquidRecipePropertyName = "enableCookedSquidRecipe";
        ////boolean enableCookedSquidRecipeDefault = true;
        ////String enableCookedSquidRecipeComment = "Enable the Raw Squid --> Cooked Squid smelting recipe?  true/false (true is the default)";
        ////
        ////Property enableCookedSquidRecipeProperty = forgeConfiguration.get(Configuration.CATEGORY_GENERAL, enableCookedSquidRecipePropertyName, enableCookedSquidRecipeDefault, enableCookedSquidRecipeComment);
        ////return enableCookedSquidRecipeProperty.getBoolean(enableCookedSquidRecipeDefault);
        return true;
    }
}
