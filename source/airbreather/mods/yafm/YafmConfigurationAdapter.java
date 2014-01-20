package airbreather.mods.yafm;

import java.io.File;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
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

        // Even if the recipes and drops are off, we still want to load the items,
        // otherwise all your items would go away if you load a world that used to have them on!
        int friedEggItemID = LoadFriedEggItemID(forgeConfiguration);
        this.itemConfiguration.SetFriedEggItemID(friedEggItemID);

        int rawMuttonItemID = LoadRawMuttonItemID(forgeConfiguration);
        this.itemConfiguration.SetRawMuttonItemID(rawMuttonItemID);

        int cookedMuttonItemID = LoadCookedMuttonItemID(forgeConfiguration);
        this.itemConfiguration.SetCookedMuttonItemID(cookedMuttonItemID);

        int rawSquidItemID = LoadRawSquidItemID(forgeConfiguration);
        this.itemConfiguration.SetRawSquidItemID(rawSquidItemID);

        int cookedSquidItemID = LoadCookedSquidItemID(forgeConfiguration);
        this.itemConfiguration.SetCookedSquidItemID(cookedSquidItemID);

        int carrotSoupItemID = LoadCarrotSoupItemID(forgeConfiguration);
        this.itemConfiguration.SetCarrotSoupItemID(carrotSoupItemID);

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

    private static int LoadFriedEggItemID(Configuration forgeConfiguration)
    {
        // Parameters for the fried egg item & configuration.
        String friedEggIDPropertyName = "friedEggID";
        int friedEggDefaultID = 9754;
        String friedEggIDComment = "The ID for fried egg.  " + friedEggDefaultID + " is the default";

        // Fetch the configured fried egg item ID.
        Property friedEggProperty = forgeConfiguration.getItem(friedEggIDPropertyName, friedEggDefaultID, friedEggIDComment);
        int friedEggID = friedEggProperty.getInt();

        return friedEggID;
    }

    private static int LoadRawMuttonItemID(Configuration forgeConfiguration)
    {
        // Parameters for the raw mutton item & configuration.
        String rawMuttonIDPropertyName = "rawMuttonID";
        int rawMuttonDefaultID = 9755;
        String rawMuttonIDComment = "The ID for raw mutton.  " + rawMuttonDefaultID + " is the default";

        // Fetch the configured raw mutton item ID.
        Property rawMuttonProperty = forgeConfiguration.getItem(rawMuttonIDPropertyName, rawMuttonDefaultID, rawMuttonIDComment);
        int rawMuttonID = rawMuttonProperty.getInt();

        return rawMuttonID;
    }

    private static int LoadCookedMuttonItemID(Configuration forgeConfiguration)
    {
        // Parameters for the cooked mutton item & configuration.
        String cookedMuttonIDPropertyName = "cookedMuttonID";
        int cookedMuttonDefaultID = 9756;
        String cookedMuttonIDComment = "The ID for cooked mutton.  " + cookedMuttonDefaultID + " is the default";

        // Fetch the configured cooked mutton item ID.
        Property cookedMuttonProperty = forgeConfiguration.getItem(cookedMuttonIDPropertyName, cookedMuttonDefaultID, cookedMuttonIDComment);
        int cookedMuttonID = cookedMuttonProperty.getInt();

        return cookedMuttonID;
    }

    private static int LoadRawSquidItemID(Configuration forgeConfiguration)
    {
        // Parameters for the raw squid item & configuration.
        String rawSquidIDPropertyName = "rawSquidID";
        int rawSquidDefaultID = 9757;
        String rawSquidIDComment = "The ID for raw squid.  " + rawSquidDefaultID + " is the default";

        // Fetch the configured raw squid item ID.
        Property rawSquidProperty = forgeConfiguration.getItem(rawSquidIDPropertyName, rawSquidDefaultID, rawSquidIDComment);
        int rawSquidID = rawSquidProperty.getInt();

        return rawSquidID;
    }

    private static int LoadCookedSquidItemID(Configuration forgeConfiguration)
    {
        // Parameters for the cooked squid item & configuration.
        String cookedSquidIDPropertyName = "cookedSquidID";
        int cookedSquidDefaultID = 9758;
        String cookedSquidIDComment = "The ID for cooked squid.  " + cookedSquidDefaultID + " is the default";

        // Fetch the configured cooked squid item ID.
        Property cookedSquidProperty = forgeConfiguration.getItem(cookedSquidIDPropertyName, cookedSquidDefaultID, cookedSquidIDComment);
        int cookedSquidID = cookedSquidProperty.getInt();

        return cookedSquidID;
    }

    private static int LoadCarrotSoupItemID(Configuration forgeConfiguration)
    {
        // Parameters for the carrot soup item & configuration.
        String carrotSoupIDPropertyName = "carrotSoupID";
        int carrotSoupDefaultID = 9759;
        String carrotSoupIDComment = "The ID for carrot soup.  " + carrotSoupDefaultID + " is the default";

        // Fetch the configured carrot soup item ID.
        Property carrotSoupProperty = forgeConfiguration.getItem(carrotSoupIDPropertyName, carrotSoupDefaultID, carrotSoupIDComment);
        int carrotSoupID = carrotSoupProperty.getInt();

        return carrotSoupID;
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
}
