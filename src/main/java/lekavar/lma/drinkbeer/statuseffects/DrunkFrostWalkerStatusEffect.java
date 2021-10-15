package lekavar.lma.drinkbeer.statuseffects;

import lekavar.lma.drinkbeer.registry.StatusEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;

import java.awt.*;

public class DrunkFrostWalkerStatusEffect extends MobEffect {
    public DrunkFrostWalkerStatusEffect() {
        super(MobEffectCategory.NEUTRAL, new Color(30, 144, 255, 255).getRGB());
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int p_19468_) {
        int remainingTime = entity.getEffect(StatusEffectRegistry.DRUNK_FROST_WALKER.get()).getDuration();
        entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, remainingTime));
        FrostWalkerEnchantment.onEntityMoved(entity, entity.level, new BlockPos(entity.getOnPos().offset(0,1,0)), 1);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}