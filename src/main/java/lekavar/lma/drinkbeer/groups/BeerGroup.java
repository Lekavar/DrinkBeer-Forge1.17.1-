package lekavar.lma.drinkbeer.groups;

import lekavar.lma.drinkbeer.registry.BlockRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BeerGroup extends CreativeModeTab {
    public BeerGroup(String label)
    {
        super(label);
    }

    @Override
    public ItemStack makeIcon()
    {
        return new ItemStack(BlockRegistry.beerMugBlock.get());
    }
}
