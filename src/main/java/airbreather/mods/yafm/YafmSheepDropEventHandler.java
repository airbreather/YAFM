package airbreather.mods.yafm;

import com.google.common.base.Optional;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import airbreather.mods.airbreathercore.event.LivingDropsEventHandlerBase;
import airbreather.mods.airbreathercore.item.ItemDefinition;
import airbreather.mods.airbreathercore.item.ItemRegistry;

import static com.google.common.base.Preconditions.checkNotNull;

final class YafmSheepDropEventHandler extends LivingDropsEventHandlerBase
{
    private final ItemRegistry itemRegistry;

    public YafmSheepDropEventHandler(ItemRegistry itemRegistry)
    {
        // Drop between [1,3] (+ Looting adjustment) upon death.
        // Sheep should behave like cows.
        // Cows drop [0,2] leather and [1,3] meat upon death.
        // So sheep drop [1,3] meat too, in addition to the [0,2] wool.
        super(1, 3);
        this.itemRegistry = checkNotNull(itemRegistry, "itemRegistry");
    }

    @Override
    protected Optional<Item> GetItemToDrop(LivingDropsEvent event)
    {
        checkNotNull(event, "event");
        Entity entity = checkNotNull(event.entity, "event.entity");

        if (!(entity instanceof EntitySheep) ||
            !entity.worldObj.getGameRules().getBoolean("doMobLoot") ||
            ((EntitySheep)entity).isChild())
        {
            // the mob isn't a sheep, or
            // mob loot is disabled, or
            // the sheep is a child.
            return Optional.absent();
        }

        // kinda self-explanatory -- if the mob is burning then drop cooked, else drop raw.
        ItemDefinition itemDefinition = entity.isBurning() ?
                                        YafmConstants.CookedMuttonItemDefinition :
                                        YafmConstants.RawMuttonItemDefinition;

        Item itemToDrop = this.itemRegistry.FetchItem(itemDefinition);
        return Optional.of(itemToDrop);
    }
}
