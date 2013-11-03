package airbreather.mods.yafm;

import java.util.HashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.registry.LanguageRegistry;
import airbreather.mods.airbreathercore.ItemConfiguration;

// Holds item-related configuration information, specific to YAFM.
final class YafmItemConfiguration implements ItemConfiguration
{
    private final HashMap<Integer, Item> itemMap = new HashMap<Integer, Item>(1);

    public Item GetItem(int id)
    {
        return this.itemMap.get(id);
    }

    public void SetFriedEggItemID(int itemID)
    {
        // maxStackSize: 16
        // hungerRestored: 5 (same as bread... a bit less than Cooked Chicken)
        // saturationModifier: 0.6 (a "standard" saturation modifier)
        // shouldWolvesEat: true (can't be any worse than rotten flesh, right?)
        this.RegisterFoodItem(YafmConstants.FriedEggID,
                              itemID,
                              16,
                              5,
                              0.6F,
                              true,
                              YafmConstants.FriedEggEnglishName,
                              YafmConstants.FriedEggTextureID);
    }

    public void SetRawMuttonItemID(int itemID)
    {
        // maxStackSize: 64 (same as raw beef)
        // hungerRestored: 3 (same as raw beef)
        // saturationModifier: 0.3 (same as raw beef)
        // shouldWolvesEat: true (same as raw beef)
        this.RegisterFoodItem(YafmConstants.RawMuttonID,
                              itemID,
                              64,
                              3,
                              0.3F,
                              true,
                              YafmConstants.RawMuttonEnglishName,
                              YafmConstants.RawMuttonTextureID);
    }

    public void SetCookedMuttonItemID(int itemID)
    {
        // maxStackSize: 64 (same as cooked beef)
        // hungerRestored: 8 (same as cooked beef)
        // saturationModifier: 0.8 (same as cooked beef)
        // shouldWolvesEat: true (same as cooked beef)
        this.RegisterFoodItem(YafmConstants.CookedMuttonID,
                              itemID,
                              64,
                              8,
                              0.8F,
                              true,
                              YafmConstants.CookedMuttonEnglishName,
                              YafmConstants.CookedMuttonTextureID);
    }

    public void SetRawSquidItemID(int itemID)
    {
        // maxStackSize: 64 (same as raw chicken)
        // hungerRestored: 2 (same as raw chicken)
        // saturationModifier: 0.3 (same as raw chicken)
        // shouldWolvesEat: true (same as raw chicken)
        this.RegisterFoodItem(YafmConstants.RawSquidID,
                              itemID,
                              64,
                              2,
                              0.3F,
                              true,
                              YafmConstants.RawSquidEnglishName,
                              YafmConstants.RawSquidTextureID);
    }

    public void SetCookedSquidItemID(int itemID)
    {
        // maxStackSize: 64 (same as cooked chicken)
        // hungerRestored: 6 (same as cooked chicken)
        // saturationModifier: 0.6 (same as cooked chicken)
        // shouldWolvesEat: true (same as cooked chicken)
        this.RegisterFoodItem(YafmConstants.CookedSquidID,
                              itemID,
                              64,
                              6,
                              0.6F,
                              true,
                              YafmConstants.CookedSquidEnglishName,
                              YafmConstants.CookedSquidTextureID);
    }

    private void RegisterFoodItem(int internalID,
                                  int itemID,
                                  int maxStackSize,
                                  int hungerRestored,
                                  float saturationModifier,
                                  boolean shouldWolvesEat,
                                  String englishName,
                                  String textureID)
    {
        if (this.itemMap.containsKey(internalID))
        {
            return;
        }

        Item newFoodItem = new ItemFood(itemID, hungerRestored, saturationModifier, shouldWolvesEat);
        newFoodItem.setMaxStackSize(maxStackSize)
                   .setUnlocalizedName(englishName)
                   .setCreativeTab(CreativeTabs.tabFood)
                   .setTextureName(textureID);

        this.itemMap.put(internalID, newFoodItem);

        // TODO: Globalize... that's a project for another day.
        LanguageRegistry.addName(newFoodItem, englishName);
    }
}
