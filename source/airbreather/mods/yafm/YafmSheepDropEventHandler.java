package airbreather.mods.yafm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import airbreather.mods.airbreathercore.ItemConfiguration;

final class YafmSheepDropEventHandler extends YafmLivingDropsEventHandlerBase
{
    private final ItemConfiguration itemConfiguration;

    public YafmSheepDropEventHandler(ItemConfiguration itemConfiguration)
    {
        // Drop between [1,3] (+ Looting adjustment) upon death.
        // Sheep should behave like cows.
        // Cows drop [0,2] leather and [1,3] meat upon death.
        // So sheep drop [1,3] meat too, in addition to the [0,2] wool.
        super(1, 3);
        this.itemConfiguration = itemConfiguration;
    }

    @Override
    protected Item GetItemToDrop(LivingDropsEvent event)
    {
        // Whoops -- ignore this; cows drop their meat regardless.
        // It's just for rare drops that we care about this value.
        ////if (!event.recentlyHit)
        ////{
        ////    return null;
        ////}
        
        Entity entity = event.entity;

        if (!(entity instanceof EntitySheep) ||
            !entity.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot") ||
            ((EntitySheep)entity).isChild())
        {
            // the mob isn't a sheep, or
            // mob loot is disabled, or
            // the sheep is a child.
            return null;
        }

        if (entity.isBurning())
        {
            // kinda self-explanatory -- if the mob is burning, then drop cooked
            return this.itemConfiguration.GetItem(YafmConstants.CookedMuttonID);
        }

        return this.itemConfiguration.GetItem(YafmConstants.RawMuttonID);
    }
}
