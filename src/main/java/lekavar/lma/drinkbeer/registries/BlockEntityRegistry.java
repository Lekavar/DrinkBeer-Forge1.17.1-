package lekavar.lma.drinkbeer.registries;

import lekavar.lma.drinkbeer.tileentity.BeerBarrelTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOKC_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, "drinkbeer");
    public static final RegistryObject<BlockEntityType<BeerBarrelTileEntity>> BEER_BARREL_TILEENTITY = BLOKC_ENTITIES.register("beer_barrel_blockentity", () -> BlockEntityType.Builder.of(BeerBarrelTileEntity::new, BlockRegistry.BEER_BARREL.get()).build(null));
}
