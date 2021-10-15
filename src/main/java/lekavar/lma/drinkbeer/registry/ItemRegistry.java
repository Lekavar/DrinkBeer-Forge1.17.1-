package lekavar.lma.drinkbeer.registry;

import lekavar.lma.drinkbeer.blocks.BeerMugBlock;
import lekavar.lma.drinkbeer.items.BeerMugItem;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static lekavar.lma.drinkbeer.registry.GroupRegistry.*;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "drinkbeer");

    //general
    public static final RegistryObject<Item> beerBarrel = ITEMS.register("beer_barrel", () -> new BlockItem(BlockRegistry.beerBarrel.get(), new Item.Properties().tab(GENERAL_BLOCK_GROUP)));
    public static final RegistryObject<Item> emptyBeerMug = ITEMS.register("empty_beer_mug", () -> new BlockItem(BlockRegistry.emptyBeerMug.get(), new Item.Properties().tab(GENERAL_BLOCK_GROUP)));

    //beer
    public static final RegistryObject<Item> beerMug = ITEMS.register("beer_mug", () -> new BeerMugItem(BlockRegistry.beerMugBlock.get(), new Item.Properties().tab(BEER_GROUP).food(new FoodProperties.Builder().nutrition(2).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200), 1).build())));
    public static final RegistryObject<Item> beerMugBlazeStout = ITEMS.register("beer_mug_blaze_stout", () -> new BeerMugItem(BlockRegistry.beerMugBlazeStoutBlock.get(), new Item.Properties().tab(BEER_GROUP).food(new FoodProperties.Builder().nutrition(2).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1800), 1).build())));
    public static final RegistryObject<Item> beerMugBlazeMilkStout = ITEMS.register("beer_mug_blaze_milk_stout", () -> new BeerMugItem(BlockRegistry.beerMugBlazeMilkStoutBlock.get(), new Item.Properties().tab(BEER_GROUP).food(new FoodProperties.Builder().nutrition(2).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400), 1).build())));
    public static final RegistryObject<Item> beerMugAppleLambic = ITEMS.register("beer_mug_apple_lambic", () -> new BeerMugItem(BlockRegistry.beerMugAppleLambicBlock.get(), new Item.Properties().tab(BEER_GROUP).food(new FoodProperties.Builder().nutrition(3).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 300), 1).build())));
    public static final RegistryObject<Item> beerMugSweetBerryKriek = ITEMS.register("beer_mug_sweet_berry_kriek", () -> new BeerMugItem(BlockRegistry.beerMugSweetBerryKriekBlock.get(), new Item.Properties().tab(BEER_GROUP).food(new FoodProperties.Builder().nutrition(3).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400), 1).alwaysEat().build())));
    public static final RegistryObject<Item> beerMugHaarsIceyPaleLager = ITEMS.register("beer_mug_haars_icey_pale_lager", () -> new BeerMugItem(BlockRegistry.beerMugHaarsIceyPaleLagerBlock.get(), new Item.Properties().tab(BEER_GROUP).food(new FoodProperties.Builder().nutrition(1).alwaysEat().effect(() -> new MobEffectInstance(StatusEffectRegistry.DRUNK_FROST_WALKER.get(), 1200), 1).build())));
    public static final RegistryObject<Item> beerMugPumpkinKvass = ITEMS.register("beer_mug_pumpkin_kvass", () -> new BeerMugItem(BlockRegistry.beerMugPumpkinKvassBlock.get(), new Item.Properties().tab(BEER_GROUP).food(new FoodProperties.Builder().nutrition(9).alwaysEat().build())));
}
