package airbreather.mods.yafm;

import com.google.common.collect.ImmutableList;

import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemDefinition;

// Holds item-related configuration information, specific to YAFM.
final class YafmItemConfiguration implements ItemConfiguration
{
    @Override
    public Iterable<ItemDefinition> GetItemDefinitionsForNewItems()
    {
        return ImmutableList.of
        (
            YafmConstants.FriedEggItemDefinition,
            YafmConstants.RawMuttonItemDefinition,
            YafmConstants.CookedMuttonItemDefinition,
            YafmConstants.RawSquidItemDefinition,
            YafmConstants.CookedSquidItemDefinition
        );
    }
}
