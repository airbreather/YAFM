package airbreather.mods.yafm;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.item.Item;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemDefinition;

// Holds item-related configuration information, specific to YAFM.
final class YafmItemConfiguration implements ItemConfiguration
{
    private final HashMap<Integer, ItemDefinition> itemMap = new HashMap<Integer, ItemDefinition>(6);

    public YafmItemConfiguration()
    {
        this.InitializeItemDefinitionsForNonYafmItems();
        this.RegisterItem(YafmConstants.FriedEggID, YafmConstants.FriedEggItemName);
        this.RegisterItem(YafmConstants.RawMuttonID, YafmConstants.RawMuttonItemName);
        this.RegisterItem(YafmConstants.CookedMuttonID, YafmConstants.CookedMuttonItemName);
        this.RegisterItem(YafmConstants.RawSquidID, YafmConstants.RawSquidItemName);
        this.RegisterItem(YafmConstants.CookedSquidID, YafmConstants.CookedSquidItemName);
    }

    public ItemDefinition GetItemDefinition(int tag)
    {
        return this.itemMap.get(tag);
    }

    public Iterable<Integer> GetNewItemTags()
    {
        ArrayList<Integer> itemTags = new ArrayList<Integer>(5);
        itemTags.add(YafmConstants.FriedEggID);
        itemTags.add(YafmConstants.RawMuttonID);
        itemTags.add(YafmConstants.CookedMuttonID);
        itemTags.add(YafmConstants.RawSquidID);
        itemTags.add(YafmConstants.CookedSquidID);
        return itemTags;
    }

    private void RegisterItem(int tag, String itemName)
    {
        if (this.itemMap.containsKey(tag))
        {
            return;
        }

        ItemDefinition itemDefinition = new ItemDefinition(tag, YafmConstants.ModID, itemName);
        this.itemMap.put(tag, itemDefinition);
    }

    private void InitializeItemDefinitionsForNonYafmItems()
    {
        // This lets us plug non-YAFM items into our recipe framework.
        // Note: having to do this is a deliberate consequence to designing the item framework this way.
        // It means that somewhere, at some point, we have to be explicit about all the items we're using.
        ItemDefinition eggItemDefinition = new ItemDefinition(YafmConstants.EggID,
                                                              YafmConstants.BaseGameModID,
                                                              YafmConstants.EggItemName);
        this.itemMap.put(eggItemDefinition.GetTag(), eggItemDefinition);
    }
}
