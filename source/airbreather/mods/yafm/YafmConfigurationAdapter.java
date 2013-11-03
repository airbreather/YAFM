package airbreather.mods.yafm;

import java.io.File;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import airbreather.mods.airbreathercore.CustomConfiguration;
import airbreather.mods.airbreathercore.ItemConfiguration;
import airbreather.mods.airbreathercore.event.EventConfiguration;
import airbreather.mods.airbreathercore.recipe.RecipeConfiguration;

// Implements CustomConfiguration using the standard Forge configuration pattern, given a File.
final class YafmConfigurationAdapter implements CustomConfiguration
{
    private final YafmItemConfiguration itemConfiguration = new YafmItemConfiguration();
    private final YafmRecipeConfiguration recipeConfiguration = new YafmRecipeConfiguration(this.itemConfiguration);
    private final YafmEventConfiguration eventConfiguration = new YafmEventConfiguration(this.itemConfiguration);

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

        if (ShouldEnableFriedEggRecipe(forgeConfiguration))
        {
            // Need to call this AFTER setting the item in the ItemConfiguration.
            this.recipeConfiguration.EnableFriedEggRecipe();
        }

        if (ShouldEnableRawMuttonDrops(forgeConfiguration))
        {
            this.eventConfiguration.EnableRawMuttonDrops();
        }

        // TODO: Skip saving if we aren't in "create-initial" mode.
        forgeConfiguration.save();
    }

    public RecipeConfiguration GetRecipeConfiguration()
    {
        return this.recipeConfiguration;
    }

    public ItemConfiguration GetItemConfiguration()
    {
        return this.itemConfiguration;
    }

    public EventConfiguration GetEventConfiguration()
    {
        return this.eventConfiguration;
    }

    private static int LoadFriedEggItemID(Configuration forgeConfiguration)
    {
        // Parameters for the fried egg item & configuration.
        String friedEggIDPropertyName = "friedEggID";
        int friedEggDefaultID = 9754;
        String friedEggIDComment = "The ID for the fried egg.  " + friedEggDefaultID + " is the default";

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
        String rawMuttonIDComment = "The ID for the raw mutton.  " + rawMuttonDefaultID + " is the default";

        // Fetch the configured raw mutton item ID.
        Property rawMuttonProperty = forgeConfiguration.getItem(rawMuttonIDPropertyName, rawMuttonDefaultID, rawMuttonIDComment);
        int rawMuttonID = rawMuttonProperty.getInt();

        return rawMuttonID;
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
}
