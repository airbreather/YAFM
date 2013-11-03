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
