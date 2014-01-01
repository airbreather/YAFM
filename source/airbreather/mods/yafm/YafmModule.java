package airbreather.mods.yafm;

import com.google.inject.Scopes;

import airbreather.mods.airbreathercore.CustomConfiguration;
import airbreather.mods.airbreathercore.item.ItemRegistrar;
import airbreather.mods.airbreathercore.mod.ModuleBase;

final class YafmModule extends ModuleBase
{
    @Override
    protected void configure()
    {
        super.configure();
        this.bind(ItemRegistrar.class).to(YafmItemRegistrar.class);
        this.bind(CustomConfiguration.class).to(YafmConfigurationAdapter.class).in(Scopes.SINGLETON);
    }
}
