package airbreather.mods.yafm;

import airbreather.mods.airbreathercore.item.ItemDefinition;

// Some constants used throughout the mod.
final class YafmConstants
{
    // ID of the mod.
    public static final String ModID = "yafm";

    // ID of the base game.
    public static final String BaseGameModID = "minecraft";

    // Name of the mod.
    public static final String ModName = "Yet Another Food Mod";

    // Refers to the egg item in the base game.
    public static final ItemDefinition EggItemDefinition = new ItemDefinition(BaseGameModID, "egg");

    // Refers to the carrot item in the base game.
    public static final ItemDefinition CarrotItemDefinition = new ItemDefinition(BaseGameModID, "carrot");

    // Refers to the bowl item in the base game.
    public static final ItemDefinition BowlItemDefinition = new ItemDefinition(BaseGameModID, "bowl");

    // Refers to the fried egg item added by this mod.
    public static final ItemDefinition FriedEggItemDefinition = new ItemDefinition(ModID, "eggFried");

    // Refers to the raw mutton item added by this mod.
    public static final ItemDefinition RawMuttonItemDefinition = new ItemDefinition(ModID, "muttonRaw");

    // Refers to the cooked mutton item added by this mod.
    public static final ItemDefinition CookedMuttonItemDefinition = new ItemDefinition(ModID, "muttonCooked");

    // Refers to the raw squid item added by this mod.
    public static final ItemDefinition RawSquidItemDefinition = new ItemDefinition(ModID, "squidRaw");

    // Refers to the cooked squid item added by this mod.
    public static final ItemDefinition CookedSquidItemDefinition = new ItemDefinition(ModID, "squidCooked");

    // Refers to the carrot soup item added by this mod.
    public static final ItemDefinition CarrotSoupItemDefinition = new ItemDefinition(ModID, "carrotSoup");
}
