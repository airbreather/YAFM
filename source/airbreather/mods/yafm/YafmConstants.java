package airbreather.mods.yafm;

// Some constants used throughout the mod.
final class YafmConstants
{
    // ID of the mod.
    public static final String ModID = "YAFM";

    // Current version of the mod.
    public static final String CurrentVersion = "0.1.0";

    // An ID that refers to the fried egg item.
    // NOT the actual configurable item ID, just something to use internally.
    public static final int FriedEggID = 0;

    // The string that refers to the texture file for the fried egg item's icon.
    // Ends up being "YAFM:eggFried", which translates to assets/yafm/textures/items/eggFried.png
    public static final String FriedEggTextureID = ModID + ":eggFried";

    // The English name for the fried egg item.
    // TODO: globalize... that's a project for another day.
    public static final String FriedEggEnglishName = "Fried Egg";
}
