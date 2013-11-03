package airbreather.mods.yafm;

import java.util.ArrayList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.IEventListener;
import airbreather.mods.airbreathercore.ItemConfiguration;
import airbreather.mods.airbreathercore.event.EventConfiguration;
import airbreather.mods.airbreathercore.event.EventType;

// Holds event-related configuration information, specific to YAFM.
final class YafmEventConfiguration implements EventConfiguration
{
    private final ItemConfiguration itemConfiguration;
    private boolean enableRawMuttonDrops = false;

    public YafmEventConfiguration(ItemConfiguration itemConfiguration)
    {
        this.itemConfiguration = itemConfiguration;
    }

    public void EnableRawMuttonDrops()
    {
        this.enableRawMuttonDrops = true;
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
        ArrayList<IEventListener> results = new ArrayList<IEventListener>(1);

        if (this.enableRawMuttonDrops)
        {
            results.add(new YafmSheepDropEventHandler(this.itemConfiguration));
        }

        results.trimToSize();
        return results;
    }
}
