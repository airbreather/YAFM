package airbreather.mods.yafm;

import java.util.ArrayList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import airbreather.mods.airbreathercore.ItemConfiguration;
import airbreather.mods.airbreathercore.recipe.Recipe;
import airbreather.mods.airbreathercore.recipe.RecipeConfiguration;
import airbreather.mods.airbreathercore.recipe.SmeltingRecipe;
////import airbreather.mods.airbreathercore.CraftingRecipe;

// Holds recipe-related configuration information, specific to YAFM.
final class YafmRecipeConfiguration implements RecipeConfiguration
{
    private static final float FoodSmeltingExperience = 0.35f;
    private boolean enableFriedEggRecipe = false;

    private ItemConfiguration itemConfiguration;

    public YafmRecipeConfiguration(ItemConfiguration itemConfiguration)
    {
        this.itemConfiguration = itemConfiguration;
    }

    public void EnableFriedEggRecipe()
    {
        this.enableFriedEggRecipe = true;
    }

    public Iterable<Recipe> GetRecipes()
    {
        ArrayList<Recipe> results = new ArrayList<Recipe>(1);

        if (this.enableFriedEggRecipe)
        {
            Item eggItem = Item.egg;
            Item friedEggItem = this.itemConfiguration.GetItem(YafmConstants.FriedEggID);
            ItemStack friedEggResult = new ItemStack(friedEggItem);

            // Smelt Egg --> Fried Egg
            // (0.35 experience, same as all other food smelting recipes)
            Recipe friedEggRecipe = new SmeltingRecipe(friedEggResult, eggItem, FoodSmeltingExperience);
            results.add(friedEggRecipe);
        }

        results.trimToSize();
        return results;
    }
}
