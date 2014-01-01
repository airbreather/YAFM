package airbreather.mods.yafm;

import java.util.Random;

import com.google.common.base.Optional;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.IEventListener;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import airbreather.mods.airbreathercore.asm.EntityAccessor;
import airbreather.mods.airbreathercore.event.EventType;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

abstract class YafmLivingDropsEventHandlerBase implements IEventListener
{
    // A Random generator to use as a fallback if we can't
    // get the rand field on an Entity itself.
    private final Random rand = new Random();

    // The minimum and maximum number of drops per event (mob death),
    // without taking into account any Looting enchantment modifiers.
    private final int minDropsPerEvent;
    private final int maxDropsPerEvent;

    protected YafmLivingDropsEventHandlerBase(int minDropsPerEvent, int maxDropsPerEvent)
    {
        checkArgument(maxDropsPerEvent >= minDropsPerEvent,
                      "min cannot be greater than max. max: %s, min: %s", maxDropsPerEvent, minDropsPerEvent);
        this.minDropsPerEvent = minDropsPerEvent;
        this.maxDropsPerEvent = maxDropsPerEvent;
    }

    public EventType GetEventType()
    {
        return EventType.LivingDrops;
    }

    public void invoke(Event event)
    {
        checkNotNull(event, "event");
        checkArgument(event instanceof LivingDropsEvent,
                      "expected LivingDropsEvent, but got %s.",
                      event.getClass());

        LivingDropsEvent typedEvent = (LivingDropsEvent)event;

        Optional<Item> itemToDrop = this.GetItemToDrop(typedEvent);

        if (!itemToDrop.isPresent())
        {
            // No item to drop = nothing to do.
            return;
        }

        // Mimic the behavior of vanilla Minecraft's algorithm to drop items.
        // Start off by selecting a random number from [min, max] of items.
        // Random.nextInt(n) returns [0, n), so we need to do a bit of fiddling
        // to get what we need.
        EntityAccessor entityAccessor = new EntityAccessor(typedEvent.entity);
        Random rand = entityAccessor.GetRand().or(this.rand);

        int range = this.maxDropsPerEvent - this.minDropsPerEvent + 1;
        int dropCount = this.minDropsPerEvent + rand.nextInt(range);

        // ... and then add between 0 and (LOOTING LEVEL) more!
        if (typedEvent.lootingLevel > 0)
        {
            int perkBonus = rand.nextInt(typedEvent.lootingLevel + 1);
            dropCount += perkBonus;
        }

        for (int i = 0; i < dropCount; i++)
        {
            // For some reason (guessing it's to avoid dropping a 0-item stack),
            // the vanilla code loops through and drops multiple 1-item stacks.
            EntityItem droppedItem = typedEvent.entity.func_145779_a(itemToDrop.get(), 1);

            // from browsing the code (and testing it out), it looks like the
            // result will already get added to "drops" as a result of calling
            // dropItem!  so even though it looks really tempting, don't do this
            // explicitly, or we'll just double the loot.
            ////typedEvent.drops.add(droppedItem);
        }
    }

    // Subclass overrides to say what item to drop for this event (if any).
    protected abstract Optional<Item> GetItemToDrop(LivingDropsEvent event);
}
