package airbreather.mods.yafm;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemDefinition;
import airbreather.mods.airbreathercore.item.ItemRegistry;

final class YafmSheepDropEventHandler extends YafmLivingDropsEventHandlerBase
{
    private final ItemConfiguration itemConfiguration;
    private final ItemRegistry itemRegistry;

    public YafmSheepDropEventHandler(ItemConfiguration itemConfiguration, ItemRegistry itemRegistry)
    {
        // Drop between [1,3] (+ Looting adjustment) upon death.
        // Sheep should behave like cows.
        // Cows drop [0,2] leather and [1,3] meat upon death.
        // So sheep drop [1,3] meat too, in addition to the [0,2] wool.
        super(1, 3);
        this.itemConfiguration = itemConfiguration;
        this.itemRegistry = itemRegistry;
    }

    @Override
    protected Item GetItemToDrop(LivingDropsEvent event)
    {
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

        // kinda self-explanatory -- if the mob is burning then drop cooked, else drop raw.
        int itemTag = entity.isBurning() ?
                      YafmConstants.CookedMuttonID :
                      YafmConstants.RawMuttonID;

        ItemDefinition itemDefinition = this.itemConfiguration.GetItemDefinition(itemTag);
        Item itemToDrop = this.itemRegistry.FetchItem(itemDefinition);
        return itemToDrop;
    }
}
