package lekavar.lma.drinkbeer.registries;

import lekavar.lma.drinkbeer.effects.DrunkFrostWalkerStatusEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MobEffectRegistry {
    public static final DeferredRegister<MobEffect> STATUS_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "drinkbeer");
    public static final RegistryObject<MobEffect> DRUNK_FROST_WALKER = STATUS_EFFECTS.register("drunk_frost_walker", DrunkFrostWalkerStatusEffect::new);
}
