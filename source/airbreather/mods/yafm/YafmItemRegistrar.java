package airbreather.mods.yafm;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.creativetab.CreativeTabs;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemDefinition;
import airbreather.mods.airbreathercore.item.ItemRegistrarBase;
import airbreather.mods.airbreathercore.item.ItemRegistry;

// A helper class to register all the items added by this mod.
final class YafmItemRegistrar extends ItemRegistrarBase
{
    @Override
    public Item CreateItemCore(ItemDefinition definition)
    {
        int tag = definition.GetTag();
        if (tag == YafmConstants.FriedEggID)
        {
            // maxStackSize: 16
            // hungerRestored: 5 (same as bread... a bit less than Cooked Chicken)
            // saturationModifier: 0.6 (a "standard" saturation modifier)
            // shouldWolvesEat: true (can't be any worse than rotten flesh, right?)
            return CreateFoodItem(definition, 16, 5, 0.6f, true);
        }
        else if (tag == YafmConstants.RawMuttonID)
        {
            // maxStackSize: 64 (same as raw beef)
            // hungerRestored: 3 (same as raw beef)
            // saturationModifier: 0.3 (same as raw beef)
            // shouldWolvesEat: true (same as raw beef)
            return CreateFoodItem(definition, 64, 3, 0.3f, true);
        }
        else if (tag == YafmConstants.CookedMuttonID)
        {
            // maxStackSize: 64 (same as cooked beef)
            // hungerRestored: 8 (same as cooked beef)
            // saturationModifier: 0.8 (same as cooked beef)
            // shouldWolvesEat: true (same as cooked beef)
            return CreateFoodItem(definition, 64, 8, 0.8f, true);
        }
        else if (tag == YafmConstants.RawSquidID)
        {
            // maxStackSize: 64 (same as raw chicken)
            // hungerRestored: 2 (same as raw chicken)
            // saturationModifier: 0.3 (same as raw chicken)
            // shouldWolvesEat: true (same as raw chicken)
            return CreateFoodItem(definition, 64, 2, 0.3f, true);
        }
        else if (tag == YafmConstants.CookedSquidID)
        {
            // maxStackSize: 64 (same as cooked chicken)
            // hungerRestored: 6 (same as cooked chicken)
            // saturationModifier: 0.6 (same as cooked chicken)
            // shouldWolvesEat: true (same as cooked chicken)
            return CreateFoodItem(definition, 64, 6, 0.6f, true);
        }

        return super.CreateItemCore(definition);
    }

    private static Item CreateFoodItem(ItemDefinition definition,
                                       int maxStackSize,
                                       int hungerRestored,
                                       float saturationModifier,
                                       boolean shouldWolvesEat)
    {
        Item newFoodItem = new ItemFood(hungerRestored, saturationModifier, shouldWolvesEat);
        newFoodItem.setMaxStackSize(maxStackSize)
                   .setCreativeTab(CreativeTabs.tabFood);

        return newFoodItem;
    }
}
