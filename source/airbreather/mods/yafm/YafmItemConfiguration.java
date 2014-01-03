package airbreather.mods.yafm;

import com.google.common.collect.ImmutableList;
import com.google.inject.Singleton;

import airbreather.mods.airbreathercore.item.ItemConfigurationBase;
import airbreather.mods.airbreathercore.item.ItemDefinition;

// Holds item-related configuration information, specific to YAFM.
@Singleton
final class YafmItemConfiguration extends ItemConfigurationBase
{
    @Override
    protected final Iterable<ItemDefinition> GetItemDefinitionsForNewItems()
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

    @Override
    protected final Iterable<ItemDefinition> GetItemDefinitionsForBaseItems()
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
