package airbreather.mods.yafm;

import java.util.HashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.registry.LanguageRegistry;
import airbreather.mods.airbreathercore.IItemConfiguration;
import airbreather.mods.yafm.YafmConstants;

// Holds item-related configuration information, specific to YAFM.
final class YafmItemConfiguration implements IItemConfiguration
{
    private final HashMap<Integer, Item> itemMap = new HashMap<Integer, Item>(1);

    public Item GetItem(int id)
    {
        return this.itemMap.get(id);
    }

    public void SetFriedEggItemID(int itemID)
    {
        if (this.itemMap.containsKey(YafmConstants.FriedEggID))
        {
            return;
        }

        int friedEggMaxStackSize = 16;

        // For comparison, Bread is 5, Raw Chicken is 2, and Cooked Chicken is 6.
        int hungerRestored = 5;

        // Looks like 0.6 is a "standard" saturation modifier.
        // Saturation value of food is calculated by doubling this value and multiplying the result by hungerRestored.
        float saturationModifier = 0.6F;

        // Sure, wolves can eat fried eggs... can't be any worse than rotten flesh.
        boolean shouldWolvesEat = true;

        Item friedEggItem = new ItemFood(itemID, hungerRestored, saturationModifier, shouldWolvesEat);
        friedEggItem.setMaxStackSize(friedEggMaxStackSize)
                    .setUnlocalizedName(YafmConstants.FriedEggEnglishName)
                    .setCreativeTab(CreativeTabs.tabFood)
                    .setTextureName(YafmConstants.FriedEggTextureID);

        this.itemMap.put(YafmConstants.FriedEggID, friedEggItem);

        // TODO: Globalize... that's a project for another day.
        LanguageRegistry.addName(friedEggItem, YafmConstants.FriedEggEnglishName);
    }
}
