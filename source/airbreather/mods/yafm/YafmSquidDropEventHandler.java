package airbreather.mods.yafm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemDefinition;
import airbreather.mods.airbreathercore.item.ItemRegistry;

final class YafmSquidDropEventHandler extends YafmLivingDropsEventHandlerBase
{
    private final ItemConfiguration itemConfiguration;
    private final ItemRegistry itemRegistry;

    public YafmSquidDropEventHandler(ItemConfiguration itemConfiguration, ItemRegistry itemRegistry)
    {
        // Drop between [1,3] (+ Looting adjustment) upon death.
        // Squid are supposed to feel relatively useless, I think.
        // So how about if their meet is the same strength as chicken,
        // with the drop rate the same as other meats?
        super(1, 3);
        this.itemConfiguration = itemConfiguration;
        this.itemRegistry = itemRegistry;
    }

    @Override
    protected Item GetItemToDrop(LivingDropsEvent event)
    {
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

        // kinda self-explanatory -- if the mob is burning then drop cooked, else drop raw.
        // OBVIOUSLY, squid won't be burning that often.
        int itemTag = entity.isBurning() ?
                      YafmConstants.CookedSquidID :
                      YafmConstants.RawSquidID;

        ItemDefinition itemDefinition = this.itemConfiguration.GetItemDefinition(itemTag);
        Item itemToDrop = this.itemRegistry.FetchItem(itemDefinition);
        return itemToDrop;
    }
}
