package airbreather.mods.yafm;

import java.util.ArrayList;
import net.minecraftforge.event.IEventListener;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemRegistry;
import airbreather.mods.airbreathercore.event.EventConfiguration;
import airbreather.mods.airbreathercore.event.EventType;

// Holds event-related configuration information, specific to YAFM.
final class YafmEventConfiguration implements EventConfiguration
{
    private final ItemConfiguration itemConfiguration;
    private final ItemRegistry itemRegistry;
    private boolean enableRawMuttonDrops = false;
    private boolean enableRawSquidDrops = false;

    public YafmEventConfiguration(ItemConfiguration itemConfiguration, ItemRegistry itemRegistry)
    {
        this.itemConfiguration = itemConfiguration;
        this.itemRegistry = itemRegistry;
    }

    public void EnableRawMuttonDrops()
    {
        this.enableRawMuttonDrops = true;
    }

    public void EnableRawSquidDrops()
    {
        this.enableRawSquidDrops = true;
    }

    public Iterable<EventType> GetRecognizedEventTypes()
    {
        // The only kind of event we care to know about right now is:
        // "mob died, consider dropping items".
        ArrayList<EventType> results = new ArrayList<EventType>(1);
        results.add(EventType.LivingDrops);
        return results;
    }

    public Iterable<IEventListener> GetEventHandlers(EventType eventType)
    {
        switch (eventType)
        {
            case LivingDrops:
                return this.GetLivingDropsEventHandlers();

            default:
                return new ArrayList<IEventListener>(0);
        }
    }

    private Iterable<IEventListener> GetLivingDropsEventHandlers()
    {
        ArrayList<IEventListener> results = new ArrayList<IEventListener>(2);

        if (this.enableRawMuttonDrops)
        {
            results.add(new YafmSheepDropEventHandler(this.itemConfiguration, this.itemRegistry));
        }

        if (this.enableRawSquidDrops)
        {
            results.add(new YafmSquidDropEventHandler(this.itemConfiguration, this.itemRegistry));
        }

        results.trimToSize();
        return results;
    }
}
