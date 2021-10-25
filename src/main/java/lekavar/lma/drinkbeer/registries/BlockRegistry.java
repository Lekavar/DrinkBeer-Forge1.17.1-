package lekavar.lma.drinkbeer.registries;

import lekavar.lma.drinkbeer.blocks.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "drinkbeer");

    //general
    public static final RegistryObject<Block> BEER_BARREL = BLOCKS.register("beer_barrel", BeerBarrelBlock::new);
    public static final RegistryObject<Block> EMPTY_BEER_MUG = BLOCKS.register("empty_beer_mug", BeerMugBlock::new);
    public static final RegistryObject<Block> IRON_CALL_BELL = BLOCKS.register("iron_call_bell", CallBellBlock::new);
    public static final RegistryObject<Block> GOLDEN_CALL_BELL = BLOCKS.register("golden_call_bell", CallBellBlock::new);

    public static final RegistryObject<Block> RECIPE_BOARD_BEER_MUG = BLOCKS.register("recipe_board_beer_mug", () -> new RecipeBoardBlock(true));
    public static final RegistryObject<Block> RECIPE_BOARD_BEER_MUG_BLAZE_STOUT = BLOCKS.register("recipe_board_beer_mug_blaze_stout", () -> new RecipeBoardBlock(true));
    public static final RegistryObject<Block> RECIPE_BOARD_BEER_MUG_BLAZE_MILK_STOUT = BLOCKS.register("recipe_board_beer_mug_blaze_milk_stout", () -> new RecipeBoardBlock(true));
    public static final RegistryObject<Block> RECIPE_BOARD_BEER_MUG_APPLE_LAMBIC = BLOCKS.register("recipe_board_beer_mug_apple_lambic", () -> new RecipeBoardBlock(true));
    public static final RegistryObject<Block> RECIPE_BOARD_BEER_MUG_SWEET_BERRY_KRIEK = BLOCKS.register("recipe_board_beer_mug_sweet_berry_kriek", () -> new RecipeBoardBlock(true));
    public static final RegistryObject<Block> RECIPE_BOARD_BEER_MUG_HAARS_ICEY_PALE_LAGER = BLOCKS.register("recipe_board_beer_mug_haars_icey_pale_lager", () -> new RecipeBoardBlock(true));
    public static final RegistryObject<Block> RECIPE_BOARD_BEER_MUG_PUMPKIN_KVASS = BLOCKS.register("recipe_board_beer_mug_pumpkin_kvass", () -> new RecipeBoardBlock(true));
    public static final RegistryObject<Block> RECIPE_BOARD_BEER_MUG_NIGHT_HOWL_KVASS = BLOCKS.register("recipe_board_beer_mug_night_howl_kvass", () -> new RecipeBoardBlock(true));

    public static final RegistryObject<Block> RECIPE_BOARD_PACKAGE = BLOCKS.register("recipe_board_package", RecipeBoardPackageBlock::new);

    //beer
    public static final RegistryObject<Block> BEER_MUG = BLOCKS.register("beer_mug", BeerMugBlock::new);
    public static final RegistryObject<Block> BEER_MUG_BLAZE_STOUT = BLOCKS.register("beer_mug_blaze_stout", BeerMugBlock::new);
    public static final RegistryObject<Block> BEER_MUG_BLAZE_MILK_STOUT = BLOCKS.register("beer_mug_blaze_milk_stout", BeerMugBlock::new);
    public static final RegistryObject<Block> BEER_MUG_APPLE_LAMBIC = BLOCKS.register("beer_mug_apple_lambic", BeerMugBlock::new);
    public static final RegistryObject<Block> BEER_MUG_SWEET_BERRY_KRIEK = BLOCKS.register("beer_mug_sweet_berry_kriek", BeerMugBlock::new);
    public static final RegistryObject<Block> BEER_MUG_HAARS_ICEY_PALE_LAGER = BLOCKS.register("beer_mug_haars_icey_pale_lager", BeerMugBlock::new);
    public static final RegistryObject<Block> BEER_MUG_PUMPKIN_KVASS = BLOCKS.register("beer_mug_pumpkin_kvass", BeerMugBlock::new);
    public static final RegistryObject<Block> BEER_MUG_NIGHT_HOWL_KVASS = BLOCKS.register("beer_mug_night_howl_kvass", BeerMugBlock::new);
    // public static final RegistryObject<Block> BEER_MUG_FROTHY_PINK_EGGNOG = BLOCKS.register("beer_mug_frothy_pink_eggnog", BeerMugBlock::new);

}
