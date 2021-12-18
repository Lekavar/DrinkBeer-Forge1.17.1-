package lekavar.lma.drinkbeer.blocks;

import lekavar.lma.drinkbeer.blockentities.BeerBarrelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class BeerBarrelBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 15, 15);

    public BeerBarrelBlock() {
        super(Properties.of(Material.WOOD).strength(2.0f).noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide) {
            world.playSound(null, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1f, 1f);

            BeerBarrelBlockEntity beerBarrelBlockEntity = (BeerBarrelBlockEntity) world.getBlockEntity(pos);
            NetworkHooks.openGui((ServerPlayer) player, beerBarrelBlockEntity, (FriendlyByteBuf packerBuffer) -> {
                packerBuffer.writeBlockPos(beerBarrelBlockEntity.getBlockPos());
            });
        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BeerBarrelBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntity) {
        if (level.isClientSide()) {
            return null;
        } else {
            return (theLevel, pos, state, tile) -> {
                if (tile instanceof BeerBarrelBlockEntity beerBarrelBlockEntity) {
                    beerBarrelBlockEntity.tickServer();
                }
            };
        }
    }
}
