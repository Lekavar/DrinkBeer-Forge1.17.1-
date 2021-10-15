package lekavar.lma.drinkbeer.blocks.entity;

import lekavar.lma.drinkbeer.container.BeerBarrelContainer;
import lekavar.lma.drinkbeer.DrinkBeer;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BeerBarrelEntity extends BlockEntity implements MenuProvider {
    private int remainingBrewTime;
    private int isMaterialCompleted;
    private int beerType;
    private int isBrewing;

    public BeerBarrelEntity(BlockPos pos, BlockState state) {
        super(DrinkBeer.BlockEntityTypes.beerBarrelEntityBlockEntityType, pos, state);
    }

    public int get(int index) {
        switch (index) {
            case 0:
                return remainingBrewTime;
            case 1:
                return isMaterialCompleted;
            case 2:
                return beerType;
            case 3:
                return isBrewing;
            default:
                return 0;
        }
    }

    public void set(int index, int value) {
        switch (index) {
            case 0:
                remainingBrewTime = value;
                break;
            case 1:
                isMaterialCompleted = value;
                break;
            case 2:
                beerType = value;
                break;
            case 3:
                isBrewing = value;
        }
    }

    public int size() {
        return 4;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        super.save(tag);
        tag.putShort("RemainingBrewTime", (short) this.remainingBrewTime);
        tag.putShort("IsMaterialCompleted", (short) this.isMaterialCompleted);
        tag.putShort("BeerType", (short) this.beerType);
        tag.putShort("IsBrewing", (short) this.isBrewing);

        return tag;
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);
        this.remainingBrewTime = tag.getShort("RemainingBrewTime");
        this.isMaterialCompleted = tag.getShort("IsMaterialCompleted");
        this.beerType = tag.getShort("BeerType");
        this.isBrewing = tag.getShort("IsBrewing");
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BeerBarrelEntity entity) {
        if (!level.isClientSide()) {
            entity.remainingBrewTime = entity.remainingBrewTime > 0 ? --entity.remainingBrewTime : 0;
        }
        entity.setChanged();
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.drinkbeer.beer_barrel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BeerBarrelContainer(id, inventory, this);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (!this.level.isClientSide)
            this.level.setBlocksDirty(this.worldPosition, this.getBlockState(), this.getBlockState());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.remainingBrewTime = tag.getShort("RemainingBrewTime");
        this.isMaterialCompleted = tag.getShort("IsMaterialCompleted");
        this.beerType = tag.getShort("BeerType");
        this.isBrewing = tag.getShort("IsBrewing");
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
            return new ClientboundBlockEntityDataPacket(worldPosition, 1, getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putShort("RemainingBrewTime", (short) this.remainingBrewTime);
        tag.putShort("IsMaterialCompleted", (short) this.isMaterialCompleted);
        tag.putShort("BeerType", (short) this.beerType);
        tag.putShort("IsBrewing", (short) this.isBrewing);
        return tag;
    }
}
