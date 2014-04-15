package airbreather.mods.yafm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameData;

import airbreather.mods.airbreathercore.item.ItemDefinition;
import airbreather.mods.airbreathercore.mod.IModule;
import airbreather.mods.airbreathercore.recipe.Recipe;
import airbreather.mods.airbreathercore.recipe.SmeltingRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

final class MCAAddons
{
    private static final Logger logger = LogManager.getLogger();
    private final IModule module;

    public MCAAddons(IModule module)
    {
        this.module = checkNotNull(module, "module");
    }

    public void Register()
    {
        // TODO: Figure out a reasonable abstraction for this.
        // I'm probably not realistically going to, but having the note helps.
        // Allow MCA villagers to cook our food.  That project looks awesome.
        // N.B.: I don't like reflection, but DLL Hell is worse for everybody.
        Class<?> cookableFoodClass = null;
        Class<?> choreRegistryClass = null;

        try
        {
            cookableFoodClass = Class.forName("mca.api.chores.CookableFood");
            choreRegistryClass = Class.forName("mca.api.registries.ChoreRegistry");
        }
        catch (final Throwable _)
        {
            // Don't bother logging anything.
        }

        if (cookableFoodClass == null || choreRegistryClass == null)
        {
            logger.info("MCA not detected.  Skipping adding MCA chores to cook YAFM food items");
            return;
        }

        logger.info("MCA detected.  Adding MCA chores to cook YAFM food items");
        try
        {
            LogMCACookingEntries(choreRegistryClass, cookableFoodClass, "before");

            // It's safe to assume that all smelting recipes added by YAFM are cooking recipes.
            for (Recipe recipe : this.module.GetCustomConfiguration().GetRecipeConfiguration().GetRecipes())
            {
                if (!(recipe instanceof SmeltingRecipe))
                {
                    continue;
                }

                SmeltingRecipe smeltingRecipe = (SmeltingRecipe)recipe;

                ItemDefinition input = smeltingRecipe.GetInput();
                ItemDefinition output = smeltingRecipe.GetResult().GetItemDefinition();

                this.RegisterMCACookableFood(cookableFoodClass, choreRegistryClass, input, output);
            }

            LogMCACookingEntries(choreRegistryClass, cookableFoodClass, "after ");
        }
        catch (final Throwable exception)
        {
            // Now, THIS ONE I'll log, because it's what would happen if I
            // didn't go the reflection route and instead imported their API
            logger.error("Unexpected problem occurred when trying to register MCA chores for YAFM food.", exception);
        }
    }

    private <TCookableFood> void RegisterMCACookableFood(Class<TCookableFood> cookableFoodClass,
                                                         Class<?> choreRegistryClass,
                                                         ItemDefinition rawItemDefinition,
                                                         ItemDefinition cookedItemDefinition)
            throws Throwable
    {
        Item rawItem = this.module.GetItemRegistry().FetchItem(rawItemDefinition);
        Item cookedItem = this.module.GetItemRegistry().FetchItem(cookedItemDefinition);

        // public CookableFood(Item itemFoodRaw, Item itemFoodCooked)
        Constructor<TCookableFood> cookableFoodConstructor = cookableFoodClass.getConstructor(Item.class, Item.class);
        TCookableFood cookableFood = cookableFoodConstructor.newInstance(rawItem, cookedItem);

        // public static void registerChoreEntry(Object entry)
        Method registerMethod = choreRegistryClass.getMethod("registerChoreEntry", Object.class);
        registerMethod.invoke(null, cookableFood);
    }

    private static <TCookableFood> void LogMCACookingEntries(Class<?> choreRegistryClass,
                                                             Class<TCookableFood> cookableFoodClass,
                                                             String stage)
    {
        try
        {
            // public static List<CookableFood> getCookingEntries()
            Method getCookingEntriesMethod = choreRegistryClass.getMethod("getCookingEntries");
            List cookingEntries = (List)getCookingEntriesMethod.invoke(null);

            // public Item getRawFoodItem()
            Method getRawFoodMethod = cookableFoodClass.getMethod("getRawFoodItem");

            // public Item getCookedFoodItem()
            Method getCookedFoodMethod = cookableFoodClass.getMethod("getCookedFoodItem");

            List<String> cookingEntryStrings = new ArrayList<String>();
            for (Object cookingEntryObj : cookingEntries)
            {
                if (!cookableFoodClass.isInstance(cookingEntryObj))
                {
                    logger.error("Expected CookableFood, not %s", cookingEntryObj.getClass());
                    continue;
                }

                TCookableFood cookingEntry = cookableFoodClass.cast(cookingEntryObj);
                Item rawFoodItem = (Item)getRawFoodMethod.invoke(cookingEntry);
                Item cookedFoodItem = (Item)getCookedFoodMethod.invoke(cookingEntry);
                String cookingEntryString = "[" + 
                                            GameData.itemRegistry.getNameForObject(rawFoodItem) +
                                            " --> " + 
                                            GameData.itemRegistry.getNameForObject(cookedFoodItem) +
                                            "]";
                cookingEntryStrings.add(cookingEntryString);
            }

            logger.debug(String.format("MCA cookable food entries %s: %s", stage, cookingEntryStrings));
        }
        catch (final Throwable _)
        {
            // I have a thing against letting "display a helpful message" code
            // spit out exceptions.  So in that case, screw it.
        }
    }
}
