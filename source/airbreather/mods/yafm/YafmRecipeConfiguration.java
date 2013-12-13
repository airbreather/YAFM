package airbreather.mods.yafm;

import java.util.ArrayList;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemDefinition;
import airbreather.mods.airbreathercore.recipe.Recipe;
import airbreather.mods.airbreathercore.recipe.RecipeConfiguration;
import airbreather.mods.airbreathercore.recipe.RecipeResult;
import airbreather.mods.airbreathercore.recipe.SmeltingRecipe;

// Holds recipe-related configuration information, specific to YAFM.
final class YafmRecipeConfiguration implements RecipeConfiguration
{
    private static final float FoodSmeltingExperience = 0.35f;
    private boolean enableFriedEggRecipe = false;
    private boolean enableCookedMuttonRecipe = false;
    private boolean enableCookedSquidRecipe = false;

    private ItemConfiguration itemConfiguration;

    public YafmRecipeConfiguration(ItemConfiguration itemConfiguration)
    {
        this.itemConfiguration = itemConfiguration;
    }

    public void EnableFriedEggRecipe()
    {
        this.enableFriedEggRecipe = true;
    }

    public void EnableCookedMuttonRecipe()
    {
        this.enableCookedMuttonRecipe = true;
    }

    public void EnableCookedSquidRecipe()
    {
        this.enableCookedSquidRecipe = true;
    }

    public Iterable<Recipe> GetRecipes()
    {
        ArrayList<Recipe> results = new ArrayList<Recipe>(2);

        if (this.enableFriedEggRecipe)
        {
            ItemDefinition egg = this.itemConfiguration.GetItemDefinition(YafmConstants.EggID);
            ItemDefinition friedEgg = this.itemConfiguration.GetItemDefinition(YafmConstants.FriedEggID);
            RecipeResult friedEggResult = new RecipeResult(friedEgg);

            // Smelt Egg --> Fried Egg
            // (0.35 experience, same as all other food smelting recipes)
            Recipe friedEggRecipe = new SmeltingRecipe(friedEggResult, egg, FoodSmeltingExperience);
            results.add(friedEggRecipe);
        }

        if (this.enableCookedMuttonRecipe)
        {
            ItemDefinition rawMutton = this.itemConfiguration.GetItemDefinition(YafmConstants.RawMuttonID);
            ItemDefinition cookedMutton = this.itemConfiguration.GetItemDefinition(YafmConstants.CookedMuttonID);
            RecipeResult cookedMuttonResult = new RecipeResult(cookedMutton);

            // Smelt Raw Mutton --> Cooked Mutton
            // (0.35 experience, same as all other food smelting recipes)
            Recipe cookedMuttonRecipe = new SmeltingRecipe(cookedMuttonResult, rawMutton, FoodSmeltingExperience);
            results.add(cookedMuttonRecipe);
        }

        if (this.enableCookedSquidRecipe)
        {
            ItemDefinition rawSquid = this.itemConfiguration.GetItemDefinition(YafmConstants.RawSquidID);
            ItemDefinition cookedSquid = this.itemConfiguration.GetItemDefinition(YafmConstants.CookedSquidID);
            RecipeResult cookedSquidResult = new RecipeResult(cookedSquid);

            // Smelt Raw Squid --> Cooked Squid
            // (0.35 experience, same as all other food smelting recipes)
            Recipe cookedSquidRecipe = new SmeltingRecipe(cookedSquidResult, rawSquid, FoodSmeltingExperience);
            results.add(cookedSquidRecipe);
        }

        results.trimToSize();
        return results;
    }
}
