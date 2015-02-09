package airbreather.mods.yafm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

// Food that's in a container.
public final class ItemFoodInContainer extends ItemFood
{
    // TODO: refactor so that we can give an ItemDefinition and an ItemRegistry
    // instead of requiring that the empty container be registered before we are.
    private final Item emptyContainer;
    public ItemFoodInContainer(int hungerRestored,
                               float saturationModifier,
                               boolean shouldWolvesEat,
                               int maxServings,
                               Item emptyContainer)
    {
        super(hungerRestored, saturationModifier, shouldWolvesEat);
        this.emptyContainer = checkNotNull(emptyContainer, "emptyContainer");
        checkArgument(maxServings > 0, "maxServings > 0");
        this.setMaxDamage(maxServings);

        // Things get super weird if we allow there to be more than one per stack.
        this.setMaxStackSize(1);
    }

    // Called when the item is eaten.
    // Return value indicates the ItemStack that will replace this one.
    @Override
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityPlayer player)
    {
        itemStack = super.onItemUseFinish(itemStack, world, player);

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
