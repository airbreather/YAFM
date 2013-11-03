package airbreather.mods.yafm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import airbreather.mods.airbreathercore.ItemConfiguration;

final class YafmSheepDropEventHandler extends YafmMobDropEventHandlerBase
{
    public YafmSheepDropEventHandler(ItemConfiguration itemConfiguration)
    {
        super(itemConfiguration.GetItem(YafmConstants.RawMuttonID));
    }

    @Override
    protected boolean EntityIsRecognized(Entity entity)
    {
        // I tried all I could to use Java generics, but type erasure got in the way here.
        // If Minecraft had a way to refer to different kinds of entities by an ID
        // rather than distinguishing based on runtime type, then this would be a bit easier.
        return entity instanceof EntitySheep;
    }
}
