package airbreather.mods.yafm;

// Some constants used throughout the mod.
final class YafmConstants
{
    // ID of the mod.
    public static final String ModID = "YAFM";

    // ID of the base game.
    public static final String BaseGameModID = "minecraft";

    // Current version of the mod.
    public static final String CurrentVersion = "0.4.1";

    // An ID that refers to the egg item.
    // NOT the actual item ID, just something to use internally.
    public static final int EggID;

    // An ID that refers to the fried egg item.
    // NOT the actual configurable item ID, just something to use internally.
    public static final int FriedEggID;

    // An ID that refers to the raw mutton item.
    // NOT the actual configurable item ID, just something to use internally.
    public static final int RawMuttonID;

    // An ID that refers to the cooked mutton item.
    // NOT the actual configurable item ID, just something to use internally.
    public static final int CookedMuttonID;

    // An ID that refers to the raw squid item.
    // NOT the actual configurable item ID, just something to use internally.
    public static final int RawSquidID;

    // An ID that refers to the cooked squid item.
    // NOT the actual configurable item ID, just something to use internally.
    public static final int CookedSquidID;

    // The name for the egg item.
    public static final String EggItemName = "egg";

    // The name for the fried egg item.
    public static final String FriedEggItemName = "eggFried";

    // The name for the raw mutton item.
    public static final String RawMuttonItemName = "muttonRaw";

    // The name for the cooked mutton item.
    public static final String CookedMuttonItemName = "muttonCooked";

    // The name for the raw squid item.
    public static final String RawSquidItemName = "squidRaw";

    // The name for the cooked squid item.
    public static final String CookedSquidItemName = "squidCooked";

    static
    {
        // Set up all the internal IDs.
        int x = 0;
        EggID = x++;
        FriedEggID = x++;
        RawMuttonID = x++;
        CookedMuttonID = x++;
        RawSquidID = x++;
        CookedSquidID = x++;
    }
}
