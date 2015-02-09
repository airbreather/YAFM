package airbreather.mods.yafm;

import com.google.common.collect.ImmutableList;

import net.minecraftforge.fml.common.eventhandler.IEventListener;

import airbreather.mods.airbreathercore.event.EventConfiguration;
import airbreather.mods.airbreathercore.event.EventType;

import static com.google.common.base.Preconditions.checkNotNull;

// Holds event-related configuration information, specific to YAFM.
final class YafmEventConfiguration implements EventConfiguration
{
    private final YafmSheepDropEventHandler sheepHandler;
    private final YafmSquidDropEventHandler squidHandler;
    private boolean enableRawMuttonDrops = false;
    private boolean enableRawSquidDrops = false;

    public YafmEventConfiguration(YafmSheepDropEventHandler sheepHandler, YafmSquidDropEventHandler squidHandler)
    {
        this.sheepHandler = checkNotNull(sheepHandler, "sheepHandler");
        this.squidHandler = checkNotNull(squidHandler, "squidHandler");
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
            resultBuilder.add(this.sheepHandler);
        }

        if (this.enableRawSquidDrops)
        {
            resultBuilder.add(this.squidHandler);
        }

        return resultBuilder.build();
    }
}
