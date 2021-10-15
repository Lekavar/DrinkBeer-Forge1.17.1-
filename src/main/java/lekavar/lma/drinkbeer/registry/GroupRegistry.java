package lekavar.lma.drinkbeer.registry;

import lekavar.lma.drinkbeer.groups.BeerGroup;
import lekavar.lma.drinkbeer.groups.DrinkBeerGeneralBlockGroup;
import net.minecraft.world.item.CreativeModeTab;

public class GroupRegistry {
    public static final CreativeModeTab BEER_GROUP = new BeerGroup("drinkbeer.beer");
    public static final CreativeModeTab GENERAL_BLOCK_GROUP = new DrinkBeerGeneralBlockGroup("drinkbeer.general");
}
