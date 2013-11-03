package airbreather.mods.yafm;

import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.IEventListener;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import airbreather.mods.airbreathercore.event.EventType;

abstract class YafmMobDropEventHandlerBase implements IEventListener
{
    // TODO: figure out how to use Forge access transformers so that this
    // can use the entity's own Random object instead of creating our own.
    private final Random random = new Random();

    private final Item item;

    // The minimum and maximum number of drops per event (mob death),
    // without taking into account any Looting enchantment modifiers.
    private final int minDropsPerEvent;
    private final int maxDropsPerEvent;

    protected YafmMobDropEventHandlerBase(Item item)
    {
        // [0,2] is the contract of EntityLivingBase.dropFewItems(),
        // which we'll usually be trying to mimic.
        this(item, 0, 2);
    }

    protected YafmMobDropEventHandlerBase(Item item, int minDropsPerEvent, int maxDropsPerEvent)
    {
        this.item = item;
        this.minDropsPerEvent = minDropsPerEvent;
        this.maxDropsPerEvent = maxDropsPerEvent;
    }

    public EventType GetEventType()
    {
        return EventType.LivingDrops;
    }

    public void invoke(Event event)
    {
        if (!(event instanceof LivingDropsEvent))
        {
            FMLLog.warning("Expected a LivingDropsEvent, but got a %s.  THIS IS A PROGRAMMING ERROR.", event.getClass());
            return;
        }

        LivingDropsEvent typedEvent = (LivingDropsEvent)event;
        if (!typedEvent.recentlyHit)
        {
            // the mob wasn't hit by the player
            // (or his/her wolf) recently enough.
            return;
        }

        Entity entity = typedEvent.entity;
        if (!this.EntityIsRecognized(entity))
        {
            // the mob isn't the recognized entity type... carry on.
            return;
        }

        // Mimic the behavior of vanilla Minecraft's algorithm to drop items.
        // Start off by selecting a random number from [min, max] of items.
        // Random.nextInt(n) returns [0, n), so we need to do a bit of fiddling
        // to get what we need.
        int range = this.maxDropsPerEvent - this.minDropsPerEvent + 1;
        int dropCount = this.minDropsPerEvent + this.random.nextInt(range);

        // ... and then add between 0 and (LOOTING LEVEL) more!
        if (typedEvent.lootingLevel > 0)
        {
            int perkBonus = this.random.nextInt(typedEvent.lootingLevel + 1);
            dropCount += perkBonus;
        }
        
        for (int i = 0; i < dropCount; i++)
        {
            // For some reason (guessing it's to avoid dropping a 0-item stack),
            // the vanilla code loops through and drops multiple 1-item stacks.
            EntityItem droppedItem = entity.dropItem(this.item.itemID, 1);

            // from browsing the code (and testing it out), it looks like the
            // result will already get added to "drops" as a result of calling
            // dropItem!  so even though it looks really tempting, don't do this
            // explicitly, or we'll just double the loot.
            ////typedEvent.drops.add(droppedItem);
        }
    }

    // Subclass can override to say that the given entity
    // is one that we should drop items for.
    protected abstract boolean EntityIsRecognized(Entity entity);
}