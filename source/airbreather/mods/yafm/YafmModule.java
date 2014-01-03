package airbreather.mods.yafm;

import airbreather.mods.airbreathercore.CustomConfiguration;
import airbreather.mods.airbreathercore.event.EventConfiguration;
import airbreather.mods.airbreathercore.item.ItemConfiguration;
import airbreather.mods.airbreathercore.item.ItemRegistrar;
import airbreather.mods.airbreathercore.mod.ModuleBase;
import airbreather.mods.airbreathercore.recipe.RecipeConfiguration;

final class YafmModule extends ModuleBase
{
    @Override
    protected void configure()
    {
        super.configure();
        this.bind(ItemRegistrar.class).to(YafmItemRegistrar.class);
        this.bind(CustomConfiguration.class).to(YafmConfigurationAdapter.class);
        this.bind(ItemConfiguration.class).to(YafmItemConfiguration.class);
        this.bind(RecipeConfiguration.class).to(YafmRecipeConfiguration.class);
        this.bind(EventConfiguration.class).to(YafmEventConfiguration.class);
    }
}
