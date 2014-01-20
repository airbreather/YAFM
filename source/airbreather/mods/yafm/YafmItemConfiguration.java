package airbreather.mods.yafm;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.item.Item;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemDefinition;

// Holds item-related configuration information, specific to YAFM.
final class YafmItemConfiguration implements ItemConfiguration
{
    private final HashMap<Integer, ItemDefinition> itemMap = new HashMap<Integer, ItemDefinition>(9);

    public YafmItemConfiguration()
    {
        this.InitializeItemDefinitionsForNonYafmItems();
    }

    public ItemDefinition GetItemDefinition(int tag)
    {
        return this.itemMap.get(tag);
    }

    public Iterable<Integer> GetNewItemTags()
    {
        ArrayList<Integer> itemTags = new ArrayList<Integer>(6);
        itemTags.add(YafmConstants.FriedEggID);
        itemTags.add(YafmConstants.RawMuttonID);
        itemTags.add(YafmConstants.CookedMuttonID);
        itemTags.add(YafmConstants.RawSquidID);
        itemTags.add(YafmConstants.CookedSquidID);
        itemTags.add(YafmConstants.CarrotSoupID);
        return itemTags;
    }

    public void SetFriedEggItemID(int itemID)
    {
        this.SetItemID(YafmConstants.FriedEggID, itemID, YafmConstants.FriedEggItemName);
    }

    public void SetRawMuttonItemID(int itemID)
    {
        this.SetItemID(YafmConstants.RawMuttonID, itemID, YafmConstants.RawMuttonItemName);
    }

    public void SetCookedMuttonItemID(int itemID)
    {
        this.SetItemID(YafmConstants.CookedMuttonID, itemID, YafmConstants.CookedMuttonItemName);
    }

    public void SetRawSquidItemID(int itemID)
    {
        this.SetItemID(YafmConstants.RawSquidID, itemID, YafmConstants.RawSquidItemName);
    }

    public void SetCookedSquidItemID(int itemID)
    {
        this.SetItemID(YafmConstants.CookedSquidID, itemID, YafmConstants.CookedSquidItemName);
    }

    public void SetCarrotSoupItemID(int itemID)
    {
        this.SetItemID(YafmConstants.CarrotSoupID, itemID, YafmConstants.CarrotSoupItemName);
    }

    private void SetItemID(int tag, int itemID, String itemName)
    {
        if (this.itemMap.containsKey(tag))
        {
            return;
        }

        ItemDefinition itemDefinition = new ItemDefinition(tag, itemID, YafmConstants.ModID, itemName);
        this.itemMap.put(tag, itemDefinition);
    }

    private void InitializeItemDefinitionsForNonYafmItems()
    {
        // This lets us plug non-YAFM items into our recipe framework.
        // Note: having to do this is a deliberate consequence to designing the item framework this way.
        // It means that somewhere, at some point, we have to be explicit about all the items we're using.
        int eggItemID = Item.egg.itemID;
        ItemDefinition eggItemDefinition = new ItemDefinition(YafmConstants.EggID,
                                                              eggItemID,
                                                              YafmConstants.BaseGameModID,
                                                              YafmConstants.EggItemName);
        this.itemMap.put(eggItemDefinition.GetTag(), eggItemDefinition);

        int carrotItemID = Item.carrot.itemID;
        ItemDefinition carrotItemDefinition = new ItemDefinition(YafmConstants.CarrotID,
                                                                 carrotItemID,
                                                                 YafmConstants.BaseGameModID,
                                                                 YafmConstants.CarrotItemName);
        this.itemMap.put(carrotItemDefinition.GetTag(), carrotItemDefinition);

        int bowlItemID = Item.bowlEmpty.itemID;
        ItemDefinition bowlItemDefinition = new ItemDefinition(YafmConstants.BowlID,
                                                               bowlItemID,
                                                               YafmConstants.BaseGameModID,
                                                               YafmConstants.BowlItemName);
        this.itemMap.put(bowlItemDefinition.GetTag(), bowlItemDefinition);
    }
}
