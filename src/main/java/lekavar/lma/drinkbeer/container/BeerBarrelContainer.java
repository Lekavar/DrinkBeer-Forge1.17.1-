package lekavar.lma.drinkbeer.container;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import lekavar.lma.drinkbeer.blocks.entity.BeerBarrelEntity;
import lekavar.lma.drinkbeer.registry.BlockRegistry;
import lekavar.lma.drinkbeer.registry.ContainerTypeRegistry;
import lekavar.lma.drinkbeer.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.util.concurrent.Runnables.doNothing;

public class BeerBarrelContainer extends AbstractContainerMenu {
    private final Inventory input;
    private final Inventory output;
    private List<ItemStack> getBackResultList;
    private final Slot materialSlot1;
    private final Slot materialSlot2;
    private final Slot materialSlot3;
    private final Slot materialSlot4;
    private final Slot emptyMugSlot;
    private final Slot resultSlot;
    private Inventory playerInv;
    private BeerBarrelEntity entity;
    public Boolean pouring;

    public BeerBarrelContainer(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory, (BeerBarrelEntity) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()));
    }

    public BeerBarrelContainer(int id, Inventory playerInventory, BeerBarrelEntity e) {
        super(ContainerTypeRegistry.beerBarrelContainer.get(), id);
        this.entity = e;
        this.input = new Inventory(null);
        this.output = new Inventory(null);
        this.playerInv = playerInventory;
        this.pouring = false;

        this.materialSlot1 = this.addSlot(new Slot(input, 0, 28, 26) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                if (!isBrewing()) {
                    return createMaterialMap().containsKey(stack.getItem());
                } else
                    return false;
            }

            @Override
            public void setChanged() {
                super.setChanged();
                updateResultSlot();
            }

            @Override
            public void onTake(Player p_150645_, ItemStack p_150646_) {
                super.onTake(p_150645_, p_150646_);
                updateResultSlot();
            }
        });
        this.materialSlot2 = this.addSlot(new Slot(input, 1, 46, 26) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                if (!isBrewing()) {
                    return createMaterialMap().containsKey(stack.getItem());
                } else
                    return false;
            }

            @Override
            public void setChanged() {
                super.setChanged();
                updateResultSlot();
            }

            @Override
            public void onTake(Player p_150645_, ItemStack p_150646_) {
                super.onTake(p_150645_, p_150646_);
                updateResultSlot();
            }
        });
        this.materialSlot3 = this.addSlot(new Slot(input, 2, 28, 44) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                if (!isBrewing()) {
                    return createMaterialMap().containsKey(stack.getItem());
                } else
                    return false;
            }

            @Override
            public void setChanged() {
                super.setChanged();
                updateResultSlot();
            }

            @Override
            public void onTake(Player p_150645_, ItemStack p_150646_) {
                super.onTake(p_150645_, p_150646_);
                updateResultSlot();
            }
        });
        this.materialSlot4 = this.addSlot(new Slot(input, 3, 46, 44) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                if (!isBrewing()) {
                    return createMaterialMap().containsKey(stack.getItem());
                } else
                    return false;
            }

            @Override
            public void setChanged() {
                super.setChanged();
                updateResultSlot();
            }

            @Override
            public void onTake(Player p_150645_, ItemStack p_150646_) {
                super.onTake(p_150645_, p_150646_);
                updateResultSlot();
            }
        });
        this.emptyMugSlot = this.addSlot(new Slot(input, 4, 73, 50) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                if (stack.getItem().asItem() == ItemRegistry.emptyBeerMug.get() && isMaterialCompleted() && !isBrewing())
                    return true;
                else
                    return false;
            }

            @Override
            public void setChanged() {
                super.setChanged();
                updateResultSlot();
            }
        });
        this.resultSlot = this.addSlot(new Slot(output, 0, 128, 34) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player player) {
                if (isBrewing() && !isBrewingTimeRemain()) {
                    return true;
                } else
                    return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                pouring = true;
                resetBeerBarrel();
            }
        });
        //The player inventory
        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInv, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInv, m, 8 + m * 18, 142));
        }
        if (this.entity.get(2) != 0) {
            BiMap<Integer, Item> beerTypeMap = createBeerTypeMap();
            ItemStack resultItemStack = new ItemStack(beerTypeMap.get(this.entity.get(2)), 4);
            this.resultSlot.set(resultItemStack);
        }

        trackRemainingBrewTime();
        trackIsMaterialCompleted();
        trackBeerType();
        trackIsBrewing();
    }

    private void trackRemainingBrewTime() {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getRemainingBrewingTime();
            }

            @Override
            public void set(int value) {
                entity.set(0, value);
            }
        });
    }

    private void trackIsMaterialCompleted() {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getRemainingBrewingTime();
            }

            @Override
            public void set(int value) {
                entity.set(1, value);
            }
        });
    }

    private void trackBeerType() {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getBeerType();
            }

            @Override
            public void set(int value) {
                entity.set(2, value);
            }
        });
    }

    private void trackIsBrewing() {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getIsBrewing();
            }

            @Override
            public void set(int value) {
                entity.set(3, value);
            }
        });
    }

    public void resetBeerBarrel() {
        this.entity.set(1, 0);
        this.entity.set(2, 0);
        this.entity.set(3, 0);
        this.entity.setChanged();
    }

    public boolean isBrewing() {
        return this.getIsBrewing() == 1 ? true : false;
    }

    public boolean isBrewingTimeRemain() {
        return this.getRemainingBrewingTime() > 0 ? true : false;
    }

    public boolean isMaterialCompleted() {
        if (getIsMaterialCompleted() == 0)
            return false;
        else
            return true;
    }

    public int getRemainingBrewingTime() {
        return this.entity.get(0);
    }

    public int getIsMaterialCompleted() {
        return this.entity.get(1);
    }

    public int getBeerType() {
        return this.entity.get(2);
    }

    public int getIsBrewing() {
        return this.entity.get(3);
    }

    public void stopPouring(){
        this.pouring = false;
    }

    public int getBrewingTimeInResultSlot() {
        Item beerItem = this.resultSlot.getItem().getItem();
        if (beerItem != Items.AIR) {
            Map<Item, Integer> brewingTimeMap = createBrewingTimeMap();
            return brewingTimeMap.get(beerItem);
        }
        return 0;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(entity.getLevel(), entity.getBlockPos()), player, BlockRegistry.beerBarrel.get());
    }

    public BeerBarrelEntity getEntity(){
        return entity;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();
            if (index < 5 && !this.moveItemStackTo(stack1, 5, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
            if (!this.moveItemStackTo(stack1, 0, 5, false)) {
                return ItemStack.EMPTY;
            }
            if (stack1.isEmpty()) {
                slot.mayPlace(ItemStack.EMPTY);
            }
            if (stack1.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            } else {
                slot.setChanged();
            }
        }
        return stack;
    }

    public void updateResultSlot() {
        if (isBrewing())
            return;

        this.entity.set(1, 0);
        this.resultSlot.set(ItemStack.EMPTY);

        ItemStack materialItemStack1 = this.materialSlot1.getItem();
        ItemStack materialItemStack2 = this.materialSlot2.getItem();
        ItemStack materialItemStack3 = this.materialSlot3.getItem();
        ItemStack materialItemStack4 = this.materialSlot4.getItem();
        Map<Item, Integer> map = new HashMap<>();
        Item materialItem1 = materialItemStack1.getItem();
        Item materialItem2 = materialItemStack2.getItem();
        Item materialItem3 = materialItemStack3.getItem();
        Item materialItem4 = materialItemStack4.getItem();
        map.put(materialItem1, 1);
        if (map.containsKey(materialItem2)) {
            map.put(materialItem2, map.get(materialItem2) + 1);
        } else {
            map.put(materialItem2, 1);
        }
        if (map.containsKey(materialItem3)) {
            map.put(materialItem3, map.get(materialItem3) + 1);
        } else {
            map.put(materialItem3, 1);
        }
        if (map.containsKey(materialItem4)) {
            map.put(materialItem4, map.get(materialItem4) + 1);
        } else {
            map.put(materialItem4, 1);
        }

        ItemStack resultItemStack = getResult(map);
        if (!resultItemStack.isEmpty()) {
            this.resultSlot.set(resultItemStack);
            this.entity.set(1, 1);
            this.entity.setChanged();
        } else {
            //doNothing
        }

        if (isMaterialCompleted()) {
            ItemStack emptyMugItemStack = this.emptyMugSlot.getItem();
            if (emptyMugItemStack.getCount() >= 4) {
                Item beerItem = this.resultSlot.getItem().getItem();
                BiMap<Item, Integer> beerTypeMap = createBeerTypeMap().inverse();
                this.entity.set(0, getBrewingTimeInResultSlot());
                this.entity.set(2, beerTypeMap.get(beerItem));
                this.entity.set(3, 1);
                this.entity.setChanged();

                this.getBackResultList = getBackResult();

                this.materialSlot1.remove(1);
                this.materialSlot2.remove(1);
                this.materialSlot3.remove(1);
                this.materialSlot4.remove(1);
                this.emptyMugSlot.remove(4);
            }
        }
    }

    public List<ItemStack> getBackResult() {
        Map<Item, Integer> map = new HashMap<>();
        ItemStack materialItemStack1 = this.materialSlot1.getItem();
        ItemStack materialItemStack2 = this.materialSlot2.getItem();
        ItemStack materialItemStack3 = this.materialSlot3.getItem();
        ItemStack materialItemStack4 = this.materialSlot4.getItem();
        Item materialItem1 = materialItemStack1.getItem();
        Item materialItem2 = materialItemStack2.getItem();
        Item materialItem3 = materialItemStack3.getItem();
        Item materialItem4 = materialItemStack4.getItem();
        map.put(materialItem1, 1);
        if (map.containsKey(materialItem2)) {
            map.put(materialItem2, map.get(materialItem2) + 1);
        } else {
            map.put(materialItem2, 1);
        }
        if (map.containsKey(materialItem3)) {
            map.put(materialItem3, map.get(materialItem3) + 1);
        } else {
            map.put(materialItem3, 1);
        }
        if (map.containsKey(materialItem4)) {
            map.put(materialItem4, map.get(materialItem4) + 1);
        } else {
            map.put(materialItem4, 1);
        }

        List<ItemStack> getBackResultList = new ArrayList<>();
        if (map.containsKey(Items.WATER_BUCKET)) {
            ItemStack itemStackWaterBucket = new ItemStack(Items.BUCKET, map.get(Items.WATER_BUCKET));
            getBackResultList.add(itemStackWaterBucket);
        }
        if (map.containsKey(Items.MILK_BUCKET)) {
            ItemStack itemStackMilkBucket = new ItemStack(Items.BUCKET, map.get(Items.MILK_BUCKET));
            getBackResultList.add(itemStackMilkBucket);
        }

        return getBackResultList;
    }

    public List<ItemStack> getBackResultBeforeClose() {
        ItemStack materialItemStack1 = this.materialSlot1.getItem();
        ItemStack materialItemStack2 = this.materialSlot2.getItem();
        ItemStack materialItemStack3 = this.materialSlot3.getItem();
        ItemStack materialItemStack4 = this.materialSlot4.getItem();
        ItemStack emptyMugItemStack = this.emptyMugSlot.getItem();
        List<ItemStack> getBackResultBeforeCloseList = new ArrayList<>();
        if (!materialItemStack1.isEmpty()) {
            getBackResultBeforeCloseList.add(materialItemStack1);
        }
        if (!materialItemStack2.isEmpty()) {
            getBackResultBeforeCloseList.add(materialItemStack2);
        }
        if (!materialItemStack3.isEmpty()) {
            getBackResultBeforeCloseList.add(materialItemStack3);
        }
        if (!materialItemStack4.isEmpty()) {
            getBackResultBeforeCloseList.add(materialItemStack4);
        }
        if (!emptyMugItemStack.isEmpty()) {
            getBackResultBeforeCloseList.add(emptyMugItemStack);
        }
        if (this.getBackResultList != null)
            getBackResultBeforeCloseList.addAll(this.getBackResultList);
        return getBackResultBeforeCloseList;
    }

    public static Map<Item, String> createMaterialMap() {
        Map<Item, String> map = Maps.newLinkedHashMap();
        map.put(Items.WATER_BUCKET, "water_bucket");
        map.put(Items.MILK_BUCKET, "milk_bucket");
        map.put(Items.BLAZE_POWDER, "blaze_powder");
        map.put(Items.WHEAT, "wheat");
        map.put(Items.SUGAR, "sugar");
        map.put(Items.APPLE, "apple");
        map.put(Items.SWEET_BERRIES, "sweet_berrires");
        map.put(Items.BLUE_ICE, "blue_ice");
        map.put(Items.BREAD, "bread");
        map.put(Items.PUMPKIN, "pumpkin");
        return map;
    }

    public static Map<Item, Integer> createBrewingTimeMap() {
        Map<Item, Integer> map = Maps.newLinkedHashMap();
        map.put(ItemRegistry.beerMug.get(), 24000);
        map.put(ItemRegistry.beerMugBlazeStout.get(), 12000);
        map.put(ItemRegistry.beerMugBlazeMilkStout.get(), 18000);
        map.put(ItemRegistry.beerMugAppleLambic.get(), 24000);
        map.put(ItemRegistry.beerMugSweetBerryKriek.get(), 24000);
        map.put(ItemRegistry.beerMugHaarsIceyPaleLager.get(), 24000);
        map.put(ItemRegistry.beerMugPumpkinKvass.get(), 12000);
        return map;
    }

    public static BiMap<Integer, Item> createBeerTypeMap() {
        BiMap<Integer, Item> map = HashBiMap.create();
        map.put(1, ItemRegistry.beerMug.get());
        map.put(2, ItemRegistry.beerMugBlazeStout.get().asItem());
        map.put(3, ItemRegistry.beerMugBlazeMilkStout.get().asItem());
        map.put(4, ItemRegistry.beerMugAppleLambic.get().asItem());
        map.put(5, ItemRegistry.beerMugSweetBerryKriek.get());
        map.put(6, ItemRegistry.beerMugHaarsIceyPaleLager.get());
        map.put(7, ItemRegistry.beerMugPumpkinKvass.get());
        return map;
    }

    private ItemStack getResult(Map<Item, Integer> map) {
        //adding new recipes starts here
        if (map.containsKey(Items.WATER_BUCKET)) {
            if (map.get(Items.WATER_BUCKET) == 1) {
                if (map.containsKey(Items.WHEAT)) {
                    if (map.get(Items.WHEAT) == 3) {
                        return new ItemStack(ItemRegistry.beerMug.get(), 4);
                    } else if (map.get(Items.WHEAT) == 2) {
                        if (map.containsKey(Items.BLAZE_POWDER))
                            return new ItemStack(ItemRegistry.beerMugBlazeStout.get(), 4);
                        if (map.containsKey(Items.APPLE))
                            return new ItemStack(ItemRegistry.beerMugAppleLambic.get(), 4);
                        if (map.containsKey(Items.SWEET_BERRIES))
                            return new ItemStack(ItemRegistry.beerMugSweetBerryKriek.get(), 4);
                    } else if (map.get(Items.WHEAT) == 1) {
                        if (map.containsKey(Items.BLAZE_POWDER) && map.containsKey(Items.SUGAR)) {
                            return new ItemStack(ItemRegistry.beerMugBlazeMilkStout.get(), 4);
                        }
                    }
                } else if (map.containsKey(Items.BREAD)) {
                    if (map.get(Items.BREAD) == 2) {
                        if (map.containsKey(Items.PUMPKIN))
                            return new ItemStack(ItemRegistry.beerMugPumpkinKvass.get(), 4);
                    }
                }
            }
        } else if (map.containsKey(Items.WHEAT)) {
            if (map.get(Items.WHEAT) == 3) {
                if (map.containsKey(Items.BLUE_ICE))
                    return new ItemStack(ItemRegistry.beerMugHaarsIceyPaleLager.get(), 4);
            }
        }
        //adding new recipes ends here
        return ItemStack.EMPTY;
    }

    @Override
    public void removed(Player player) {
        try {
            java.util.List<ItemStack> getBackResultBeforeCloseList = getBackResultBeforeClose();
            if (getBackResultBeforeCloseList != null) {
                for (ItemStack itemStack : getBackResultBeforeClose()) {
                    ItemHandlerHelper.giveItemToPlayer(player, itemStack);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        player.level.playSound(player, player.getOnPos().offset(0, 1, 0), SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 1f, 1f);
        super.removed(player);
    }
}
