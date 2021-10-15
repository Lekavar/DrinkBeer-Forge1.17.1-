package lekavar.lma.drinkbeer.registry;

import lekavar.lma.drinkbeer.blocks.entity.BeerBarrelEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, "drinkbeer");
    public static final RegistryObject<BlockEntityType<BeerBarrelEntity>> beerBarrelEntity = BLOCK_ENTITIES.register("beer_barrel_blockentity", () -> BlockEntityType.Builder.of(BeerBarrelEntity::new, BlockRegistry.beerBarrel.get()).build(null));
}
