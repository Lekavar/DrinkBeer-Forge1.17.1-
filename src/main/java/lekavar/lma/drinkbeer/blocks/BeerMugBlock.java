package lekavar.lma.drinkbeer.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Function;

public class BeerMugBlock extends Block {
    public static final IntegerProperty AMOUNT = IntegerProperty.create("amount", 1, 3);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape[] SHAPE_BY_AMOUNT = new VoxelShape[]{
            Block.box(0, 0, 0, 16, 16, 16),
            Block.box(4, 0, 4, 12, 6, 12),
            Block.box(2, 0, 2, 14, 6, 14),
            Block.box(1, 0, 1, 15, 6, 15)};

    public BeerMugBlock() {
        super(Properties.of(Material.WOOD).strength(1.0f));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE_BY_AMOUNT[p_60555_.getValue(this.getAmountProperty())];
    }

    public IntegerProperty getAmountProperty() {
        return this.AMOUNT;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AMOUNT, FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
        return this.defaultBlockState().setValue(FACING, p_48689_.getHorizontalDirection());
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        ItemStack itemStack = p_60506_.getItemInHand(p_60507_);
        if (itemStack.getItem().asItem() == p_60503_.getBlock().asItem()) {
            int amount = p_60503_.getValue(AMOUNT);
            int mugInHandCount = p_60506_.getItemInHand(p_60507_).getCount();
            boolean isCreative = p_60506_.isCreative();
            switch (amount) {
                case 1:
                    p_60504_.setBlock(p_60505_, p_60503_.getBlock().defaultBlockState().setValue(AMOUNT, 2).setValue(FACING, p_60503_.getValue(FACING)), 0);
                    if (!isCreative) {
                        p_60506_.getItemInHand(p_60507_).setCount(mugInHandCount - 1);
                    }
                    if (!p_60504_.isClientSide()) {
                        p_60504_.playSound(null, p_60505_, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1f, 1f);
                    }
                    return InteractionResult.SUCCESS;
                case 2:
                    p_60504_.setBlock(p_60505_, p_60503_.getBlock().defaultBlockState().setValue(AMOUNT, 3).setValue(FACING, p_60503_.getValue(FACING)), 0);
                    if (!isCreative) {
                        p_60506_.getItemInHand(p_60507_).setCount(mugInHandCount - 1);
                    }
                    if (!p_60504_.isClientSide()) {
                        p_60504_.playSound(null, p_60505_, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1f, 1f);
                    }
                    return InteractionResult.SUCCESS;
                default:
                    return InteractionResult.FAIL;
            }
        } else {
            return InteractionResult.FAIL;
        }
    }
}
