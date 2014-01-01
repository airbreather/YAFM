package airbreather.mods.yafm;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import cpw.mods.fml.common.eventhandler.IEventListener;

import airbreather.mods.airbreathercore.event.EventConfiguration;
import airbreather.mods.airbreathercore.event.EventType;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemRegistry;

import static com.google.common.base.Preconditions.checkNotNull;

// Holds event-related configuration information, specific to YAFM.
final class YafmEventConfiguration implements EventConfiguration
{
    private final ItemConfiguration itemConfiguration;
    private final ItemRegistry itemRegistry;
    private boolean enableRawMuttonDrops = false;
    private boolean enableRawSquidDrops = false;

    @Inject
    public YafmEventConfiguration(ItemConfiguration itemConfiguration, ItemRegistry itemRegistry)
    {
        this.itemConfiguration = checkNotNull(itemConfiguration, "itemConfiguration");
        this.itemRegistry = checkNotNull(itemRegistry, "itemRegistry");
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
        return ImmutableList.of(EventType.LivingDrops);
    }

    public Iterable<IEventListener> GetEventHandlers(EventType eventType)
    {
        switch (eventType)
        {
            case LivingDrops:
                return this.GetLivingDropsEventHandlers();

            default:
                return ImmutableList.of();
        }
    }

    private Iterable<IEventListener> GetLivingDropsEventHandlers()
    {
        ImmutableList.Builder<IEventListener> resultBuilder = ImmutableList.builder();

        if (this.enableRawMuttonDrops)
        {
            resultBuilder.add(new YafmSheepDropEventHandler(this.itemConfiguration, this.itemRegistry));
        }

        if (this.enableRawSquidDrops)
        {
            resultBuilder.add(new YafmSquidDropEventHandler(this.itemConfiguration, this.itemRegistry));
        }

        return resultBuilder.build();
    }
}
