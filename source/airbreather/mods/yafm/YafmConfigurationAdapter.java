package airbreather.mods.yafm;

import java.io.File;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import airbreather.mods.airbreathercore.CustomConfiguration;
import airbreather.mods.airbreathercore.IItemConfiguration;
import airbreather.mods.airbreathercore.RecipeConfiguration;
import airbreather.mods.yafm.YafmItemConfiguration;
import airbreather.mods.yafm.YafmRecipeConfiguration;

// Implements CustomConfiguration using the standard Forge configuration pattern, given a File.
final class YafmConfigurationAdapter implements CustomConfiguration
{
    private final YafmItemConfiguration itemConfiguration = new YafmItemConfiguration();
    private final YafmRecipeConfiguration recipeConfiguration = new YafmRecipeConfiguration(this.itemConfiguration);

    public void Initialize(File configurationFile)
    {
        Configuration forgeConfiguration = new Configuration(configurationFile);
        forgeConfiguration.load();

        // Even if the fried egg recipe is off, we still want to load the fried egg item,
        // otherwise all your fried eggs would go away if you load a world that used to have it on!
        int patchworkItemID = LoadFriedEggItemID(forgeConfiguration);
        this.itemConfiguration.SetFriedEggItemID(patchworkItemID);

        if (ShouldEnableFriedEggRecipe(forgeConfiguration))
        {
            // Need to call this AFTER setting the item in the ItemConfiguration.
            this.recipeConfiguration.EnableFriedEggRecipe();
        }

        // TODO: Skip saving if we aren't in "create-initial" mode.
        forgeConfiguration.save();
    }

    public RecipeConfiguration GetRecipeConfiguration()
    {
        return this.recipeConfiguration;
    }

    public IItemConfiguration GetItemConfiguration()
    {
        return this.itemConfiguration;
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

    private static boolean ShouldEnableFriedEggRecipe(Configuration forgeConfiguration)
    {
        String enableFriedEggRecipePropertyName = "enableFriedEggRecipe";
        boolean enableFriedEggRecipeDefault = true;
        String enableFriedEggRecipeComment = "Enable the Egg --> Fried Egg smelting recipe?  true/false (true is the default)";
        
        Property enableFriedEggRecipeProperty = forgeConfiguration.get(Configuration.CATEGORY_GENERAL, enableFriedEggRecipePropertyName, enableFriedEggRecipeDefault, enableFriedEggRecipeComment);
        return enableFriedEggRecipeProperty.getBoolean(enableFriedEggRecipeDefault);
    }
}
