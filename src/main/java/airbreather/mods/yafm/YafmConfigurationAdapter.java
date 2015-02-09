package airbreather.mods.yafm;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import airbreather.mods.airbreathercore.CustomConfigurationBase;
import airbreather.mods.airbreathercore.event.EventConfiguration;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.recipe.RecipeConfiguration;

import static com.google.common.base.Preconditions.checkNotNull;

// Implements CustomConfiguration using the standard Forge configuration pattern, given a File.
final class YafmConfigurationAdapter extends CustomConfigurationBase
{
    private final YafmItemConfiguration itemConfiguration;
    private final YafmRecipeConfiguration recipeConfiguration;
    private final YafmEventConfiguration eventConfiguration;

    public YafmConfigurationAdapter(YafmItemConfiguration itemConfiguration,
                                    YafmRecipeConfiguration recipeConfiguration,
                                    YafmEventConfiguration eventConfiguration)
    {
        this.itemConfiguration = checkNotNull(itemConfiguration, "itemConfiguration");
        this.recipeConfiguration = checkNotNull(recipeConfiguration, "recipeConfiguration");
        this.eventConfiguration = checkNotNull(eventConfiguration, "eventConfiguration");
    }

    @Override
    public void Initialize(File configurationFile)
    {
        checkNotNull(configurationFile, "configurationFile");
        Configuration forgeConfiguration = new Configuration(configurationFile);
        forgeConfiguration.load();

        if (ShouldEnableFriedEggRecipe(forgeConfiguration))
        {
            this.recipeConfiguration.EnableFriedEggRecipe();
        }

        if (ShouldEnableCarrotSoupRecipe(forgeConfiguration))
        {
            this.recipeConfiguration.EnableCarrotSoupRecipe();
        }

        if (ShouldEnableRawMuttonDrops(forgeConfiguration))
        {
            this.eventConfiguration.EnableRawMuttonDrops();
        }

        if (ShouldEnableRawSquidDrops(forgeConfiguration))
        {
            this.eventConfiguration.EnableRawSquidDrops();
        }

        // Unconditionally enable the cooking recipes for the new meats.
        // If you have the raw stuff, you should be allowed to cook it.
        this.recipeConfiguration.EnableCookedMuttonRecipe();
        this.recipeConfiguration.EnableCookedSquidRecipe();

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

    private static boolean ShouldEnableCarrotSoupRecipe(Configuration forgeConfiguration)
    {
        String enableCarrotSoupRecipePropertyName = "enableCarrotSoupRecipe";
        boolean enableCarrotSoupRecipeDefault = true;
        String enableCarrotSoupRecipeComment = "Enable the Carrot Soup crafting recipe?  true/false (true is the default)";

        Property enableCarrotSoupRecipeProperty = forgeConfiguration.get(Configuration.CATEGORY_GENERAL, enableCarrotSoupRecipePropertyName, enableCarrotSoupRecipeDefault, enableCarrotSoupRecipeComment);
        return enableCarrotSoupRecipeProperty.getBoolean(enableCarrotSoupRecipeDefault);
    }

    private static boolean ShouldEnableRawMuttonDrops(Configuration forgeConfiguration)
    {
        // 1.8 added raw mutton drops to vanilla, so don't drop our own anymore.
        return false;

        ////String enableRawMuttonDropsPropertyName = "enableRawMuttonDrops";
        ////boolean enableRawMuttonDropsDefault = true;
        ////String enableRawMuttonDropsComment = "Allow sheep to drop raw mutton?  true/false (true is the default)";
        ////
        ////Property enableRawMuttonDropsProperty = forgeConfiguration.get(Configuration.CATEGORY_GENERAL, enableRawMuttonDropsPropertyName, enableRawMuttonDropsDefault, enableRawMuttonDropsComment);
        ////return enableRawMuttonDropsProperty.getBoolean(enableRawMuttonDropsDefault);
    }

    private static boolean ShouldEnableRawSquidDrops(Configuration forgeConfiguration)
    {
        String enableRawSquidDropsPropertyName = "enableRawSquidDrops";
        boolean enableRawSquidDropsDefault = true;
        String enableRawSquidDropsComment = "Allow squid to drop raw squid?  true/false (true is the default)";

        Property enableRawSquidDropsProperty = forgeConfiguration.get(Configuration.CATEGORY_GENERAL, enableRawSquidDropsPropertyName, enableRawSquidDropsDefault, enableRawSquidDropsComment);
        return enableRawSquidDropsProperty.getBoolean(enableRawSquidDropsDefault);
    }
}
