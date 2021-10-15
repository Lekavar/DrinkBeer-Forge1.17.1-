package lekavar.lma.drinkbeer.registry;

import lekavar.lma.drinkbeer.statuseffects.DrunkFrostWalkerStatusEffect;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;

public class StatusEffectRegistry {
    public static final DeferredRegister<MobEffect> STATUS_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "drinkbeer");
    public static final RegistryObject<MobEffect> DRUNK_FROST_WALKER = STATUS_EFFECTS.register ("drunk_frost_walker", DrunkFrostWalkerStatusEffect::new);
}
