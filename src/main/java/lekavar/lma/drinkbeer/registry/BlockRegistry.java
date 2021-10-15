package lekavar.lma.drinkbeer.registry;

import lekavar.lma.drinkbeer.blocks.BeerBarrelBlock;
import lekavar.lma.drinkbeer.blocks.BeerMugBlock;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "drinkbeer");

    //general
    public static final RegistryObject<Block> beerBarrel = BLOCKS.register("beer_barrel", BeerBarrelBlock::new);
    public static final RegistryObject<Block> emptyBeerMug = BLOCKS.register("empty_beer_mug", BeerMugBlock::new);

    //beer
    public static final RegistryObject<Block> beerMugBlock = BLOCKS.register("beer_mug", BeerMugBlock::new);
    public static final RegistryObject<Block> beerMugBlazeStoutBlock = BLOCKS.register("beer_mug_blaze_stout", BeerMugBlock::new);
    public static final RegistryObject<Block> beerMugBlazeMilkStoutBlock = BLOCKS.register("beer_mug_blaze_milk_stout", BeerMugBlock::new);
    public static final RegistryObject<Block> beerMugAppleLambicBlock = BLOCKS.register("beer_mug_apple_lambic", BeerMugBlock::new);
    public static final RegistryObject<Block> beerMugSweetBerryKriekBlock = BLOCKS.register("beer_mug_sweet_berry_kriek", BeerMugBlock::new);
    public static final RegistryObject<Block> beerMugHaarsIceyPaleLagerBlock = BLOCKS.register("beer_mug_haars_icey_pale_lager", BeerMugBlock::new);
    public static final RegistryObject<Block> beerMugPumpkinKvassBlock = BLOCKS.register("beer_mug_pumpkin_kvass", BeerMugBlock::new);
}
