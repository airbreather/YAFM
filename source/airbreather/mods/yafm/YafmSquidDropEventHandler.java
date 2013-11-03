package airbreather.mods.yafm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import airbreather.mods.airbreathercore.ItemConfiguration;

final class YafmSquidDropEventHandler extends YafmLivingDropsEventHandlerBase
{
    private final ItemConfiguration itemConfiguration;

    public YafmSquidDropEventHandler(ItemConfiguration itemConfiguration)
    {
        // Drop between [1,3] (+ Looting adjustment) upon death.
        // Squid are supposed to feel relatively useless, I think.
        // So how about if their meet is the same strength as chicken,
        // with the drop rate the same as other meats?
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

        if (!(entity instanceof EntitySquid) ||
            !entity.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot") ||
            ((EntitySquid)entity).isChild())
        {
            // the mob isn't a squid, or
            // mob loot is disabled, or
            // the squid is a child.
            return null;
        }

        if (entity.isBurning())
        {
            // kinda self-explanatory -- if the mob is burning, then drop cooked
            // OBVIOUSLY, this usually won't happen.
            return this.itemConfiguration.GetItem(YafmConstants.CookedSquidID);
        }

        return this.itemConfiguration.GetItem(YafmConstants.RawSquidID);
    }
}
