package airbreather.mods.yafm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// Food that's in a container.
public final class ItemFoodInContainer extends ItemFood
{
    // TODO: refactor so that we can give an ItemDefinition and an ItemRegistry
    // instead of requiring that the empty container be registered before we are.
    private final Item emptyContainer;
    public ItemFoodInContainer(int itemID,
                               int hungerRestored,
                               float saturationModifier,
                               boolean shouldWolvesEat,
                               int maxServings,
                               Item emptyContainer)
    {
        super(itemID, hungerRestored, saturationModifier, shouldWolvesEat);
        this.emptyContainer = emptyContainer;
        this.setMaxDamage(maxServings);

        // Things get super weird if we allow there to be more than one per stack.
        this.setMaxStackSize(1);
    }

    // Called when the item is eaten.
    // Return value indicates the ItemStack that will replace this one.
    @Override
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player)
    {
        super.onEaten(itemStack, world, player);

        // the base version decrements -- we don't want to do that.
        ++itemStack.stackSize;

        if (itemStack.attemptDamageItem(1, null) ||
            itemStack.getItemDamage() == itemStack.getMaxDamage())
        {
            return new ItemStack(this.emptyContainer);
        }

        return itemStack;
    }
}
