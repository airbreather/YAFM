package airbreather.mods.yafm;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemDefinition;

import static com.google.common.base.Preconditions.checkArgument;

// Holds item-related configuration information, specific to YAFM.
final class YafmItemConfiguration implements ItemConfiguration
{
    private final ImmutableMap<Integer, ItemDefinition> itemMap;

    public YafmItemConfiguration()
    {
        ImmutableMap.Builder<Integer, ItemDefinition> mapBuilder = ImmutableMap.builder();
        for (ItemDefinition itemDefinition : this.GetItemDefinitionsForNonYafmItems())
        {
            mapBuilder.put(itemDefinition.GetTag(), itemDefinition);
        }

        for (ItemDefinition itemDefinition : this.GetItemDefinitionsForNewItems())
        {
            mapBuilder.put(itemDefinition.GetTag(), itemDefinition);
        }

        this.itemMap = mapBuilder.build();
    }

    public ItemDefinition GetItemDefinition(int tag)
    {
        checkArgument(this.itemMap.containsKey(tag),
                      "unrecognized tag: %s... recognized tags are: %s", tag, this.itemMap.keySet());
        return this.itemMap.get(tag);
    }

    public Iterable<Integer> GetNewItemTags()
    {
        return ImmutableList.of
        (
            YafmConstants.FriedEggID,
            YafmConstants.RawMuttonID,
            YafmConstants.CookedMuttonID,
            YafmConstants.RawSquidID,
            YafmConstants.CookedSquidID
        );
    }

    private Iterable<ItemDefinition> GetItemDefinitionsForNewItems()
    {
        return ImmutableList.of
        (
            new ItemDefinition(YafmConstants.FriedEggID, YafmConstants.ModID, YafmConstants.FriedEggItemName),
            new ItemDefinition(YafmConstants.RawMuttonID, YafmConstants.ModID, YafmConstants.RawMuttonItemName),
            new ItemDefinition(YafmConstants.CookedMuttonID, YafmConstants.ModID, YafmConstants.CookedMuttonItemName),
            new ItemDefinition(YafmConstants.RawSquidID, YafmConstants.ModID, YafmConstants.RawSquidItemName),
            new ItemDefinition(YafmConstants.CookedSquidID, YafmConstants.ModID, YafmConstants.CookedSquidItemName)
        );
    }

    private Iterable<ItemDefinition> GetItemDefinitionsForNonYafmItems()
    {
        // This lets us plug non-YAFM items into our recipe framework.
        // Note: having to do this is a deliberate consequence to designing the item framework this way.
        // It means that somewhere, at some point, we have to be explicit about all the items we're using.
        return ImmutableList.of
        (
            new ItemDefinition(YafmConstants.EggID, YafmConstants.BaseGameModID, YafmConstants.EggItemName)
        );
    }
}
