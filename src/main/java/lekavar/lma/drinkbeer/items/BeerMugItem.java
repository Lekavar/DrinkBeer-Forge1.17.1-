package lekavar.lma.drinkbeer.items;

import lekavar.lma.drinkbeer.registries.ItemRegistry;
import lekavar.lma.drinkbeer.registries.SoundEventRegistry;
import lekavar.lma.drinkbeer.utils.ModCreativeTab;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BeerMugItem extends BlockItem {
    private final static double MAX_PLACE_DISTANCE = 2.0D;
    private final static int BASE_NIGHT_VISION_TIME = 2400;
    private final boolean hasExtraTooltip;

    public BeerMugItem(Block block, int nutrition, boolean hasExtraTooltip) {
        super(block, new Item.Properties().tab(ModCreativeTab.BEAR).stacksTo(16)
                .food(new FoodProperties.Builder().nutrition(nutrition).alwaysEat().build()));
        this.hasExtraTooltip = hasExtraTooltip;
    }

    public BeerMugItem(Block block, int nutrition, MobEffect effect, int duration, boolean hasExtraTooltip) {
        super(block, new Item.Properties().tab(ModCreativeTab.BEAR).stacksTo(16)
                .food(new FoodProperties.Builder().nutrition(nutrition).alwaysEat().effect(() -> new MobEffectInstance(effect, duration), 1).build()));
        this.hasExtraTooltip = hasExtraTooltip;
    }

    public BeerMugItem(Block block, int nutrition, Supplier<MobEffectInstance> effectIn, boolean hasExtraTooltip) {
        super(block, new Item.Properties().tab(ModCreativeTab.BEAR).stacksTo(16)
                .food(new FoodProperties.Builder().nutrition(nutrition).alwaysEat().effect(effectIn, 1).build()));
        this.hasExtraTooltip = hasExtraTooltip;
    }


    @Override
    public SoundEvent getEatingSound() {
        return SoundEventRegistry.DRINKING_BEER.get();
    }

    @Override
    protected boolean canPlace(BlockPlaceContext p_195944_1_, BlockState p_195944_2_) {
        if ((p_195944_1_.getClickLocation().distanceTo(p_195944_1_.getPlayer().position()) > MAX_PLACE_DISTANCE))
            return false;
        else {
            return super.canPlace(p_195944_1_, p_195944_2_);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        String name = this.asItem().toString();
        if (hasEffectNoticeTooltip()) {
            tooltip.add(new TranslatableComponent("item.drinkbeer." + name + ".tooltip").setStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE)));
        }
        String hunger = String.valueOf(stack.getItem().getFoodProperties().getNutrition());
        tooltip.add(new TranslatableComponent("drinkbeer.restores_hunger").setStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE)).append(hunger));
    }

    private boolean hasEffectNoticeTooltip() {
        return this.hasExtraTooltip;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity livingEntity) {
        // Handle special drinking effect
        if (stack.getItem() == ItemRegistry.BEER_MUG_NIGHT_HOWL_KVASS.get()) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, getNightVisionTime(world.getMoonPhase())));
            if (!world.isClientSide()) {
                world.playSound(null, livingEntity.blockPosition(), getRandomNightHowlSound(), SoundSource.PLAYERS, 1.2f, 1f);
            }
        }
        // Return empty mug
        if (livingEntity instanceof Player && ((Player) livingEntity).isCreative()) {
            ItemStack temp = stack.copy();
            livingEntity.eat(world,stack);
            return temp;
        } else {
            if (stack.getCount() == 1) {
                livingEntity.eat(world,stack);
                return new ItemStack(ItemRegistry.EMPTY_BEER_MUG.get());
            } else {
                ItemStack emptyMug = new ItemStack(ItemRegistry.EMPTY_BEER_MUG.get(), 1);
                if (livingEntity instanceof Player) {
                    ItemHandlerHelper.giveItemToPlayer((Player) livingEntity, emptyMug);
                } else {
                    Containers.dropItemStack(world, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), emptyMug);
                }
                return super.finishUsingItem(stack, world, livingEntity);
            }
        }
    }

    private int getNightVisionTime(int moonPhase) {
        return BASE_NIGHT_VISION_TIME + (moonPhase == 0 ? Math.abs(moonPhase - 1 - 4) * 1200 : Math.abs(moonPhase - 4) * 1200);
    }

    private SoundEvent getRandomNightHowlSound() {
        List<SoundEvent> available = ForgeRegistries.SOUND_EVENTS.getValues().stream().filter(soundEvent -> soundEvent.getRegistryName().toString().contains("night_howl_drinking_effect")).collect(Collectors.toList());
        return available.get(new Random().nextInt(available.size()));
    }
}
