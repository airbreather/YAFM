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

abstract class YafmLivingDropsEventHandlerBase implements IEventListener
{
    // TODO: figure out how to use Forge access transformers so that this
    // can use the entity's own Random object instead of creating our own.
    private final Random random = new Random();

    // The minimum and maximum number of drops per event (mob death),
    // without taking into account any Looting enchantment modifiers.
    private final int minDropsPerEvent;
    private final int maxDropsPerEvent;

    protected YafmLivingDropsEventHandlerBase(int minDropsPerEvent, int maxDropsPerEvent)
    {
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

        Item itemToDrop = this.GetItemToDrop(typedEvent);

        if (itemToDrop == null)
        {
            // No item to drop = nothing to do.
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
            EntityItem droppedItem = typedEvent.entity.dropItem(itemToDrop.itemID, 1);

            // from browsing the code (and testing it out), it looks like the
            // result will already get added to "drops" as a result of calling
            // dropItem!  so even though it looks really tempting, don't do this
            // explicitly, or we'll just double the loot.
            ////typedEvent.drops.add(droppedItem);
        }
    }

    // Subclass overrides to say what item to drop for this event (if any).
    protected abstract Item GetItemToDrop(LivingDropsEvent event);
}
