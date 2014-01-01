package airbreather.mods.yafm;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import airbreather.mods.airbreathercore.event.LivingDropsEventHandlerBase;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemDefinition;
import airbreather.mods.airbreathercore.item.ItemRegistry;

import static com.google.common.base.Preconditions.checkNotNull;

final class YafmSquidDropEventHandler extends LivingDropsEventHandlerBase
{
    private final ItemConfiguration itemConfiguration;
    private final ItemRegistry itemRegistry;

    @Inject
    public YafmSquidDropEventHandler(ItemConfiguration itemConfiguration, ItemRegistry itemRegistry)
    {
        // Drop between [1,3] (+ Looting adjustment) upon death.
        // Squid are supposed to feel relatively useless, I think.
        // So how about if their meet is the same strength as chicken,
        // with the drop rate the same as other meats?
        super(1, 3);
        this.itemConfiguration = checkNotNull(itemConfiguration, "itemConfiguration");
        this.itemRegistry = checkNotNull(itemRegistry, "itemRegistry");
    }

    @Override
    protected Optional<Item> GetItemToDrop(LivingDropsEvent event)
    {
        checkNotNull(event, "event");
        Entity entity = checkNotNull(event.entity, "event.entity");

        if (!(entity instanceof EntitySquid) ||
            !entity.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot") ||
            ((EntitySquid)entity).isChild())
        {
            // the mob isn't a squid, or
            // mob loot is disabled, or
            // the squid is a child.
            return Optional.absent();
        }

        // kinda self-explanatory -- if the mob is burning then drop cooked, else drop raw.
        // OBVIOUSLY, squid won't be burning that often.
        int itemTag = entity.isBurning() ?
                      YafmConstants.CookedSquidID :
                      YafmConstants.RawSquidID;

        ItemDefinition itemDefinition = this.itemConfiguration.GetItemDefinition(itemTag);
        Item itemToDrop = this.itemRegistry.FetchItem(itemDefinition);
        return Optional.of(itemToDrop);
    }
}
