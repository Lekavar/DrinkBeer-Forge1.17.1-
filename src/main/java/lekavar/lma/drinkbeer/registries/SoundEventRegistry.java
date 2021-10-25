package lekavar.lma.drinkbeer.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundEventRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "drinkbeer");
    public static final RegistryObject<SoundEvent> DRINKING_BEER = register("drinking_beer");
    public static final RegistryObject<SoundEvent> POURING = register("pouring");
    public static final RegistryObject<SoundEvent> POURING_CHRISTMAS_VER = register("pouring_christmas");
    public static final RegistryObject<SoundEvent> IRON_CALL_BELL_TINKLING = register("iron_call_bell_tinkling");
    public static final RegistryObject<SoundEvent> GOLDEN_CALL_BELL_TINKLING = register("golden_call_bell_tinkling");
    public static final RegistryObject<SoundEvent> NIGHT_HOWL_DRINKING_EFFECT_1 = register("night_howl_drinking_effect_1");
    public static final RegistryObject<SoundEvent> NIGHT_HOWL_DRINKING_EFFECT_2 = register("night_howl_drinking_effect_2");
    public static final RegistryObject<SoundEvent> NIGHT_HOWL_DRINKING_EFFECT_3 = register("night_howl_drinking_effect_3");
    public static final RegistryObject<SoundEvent> NIGHT_HOWL_DRINKING_EFFECT_4 = register("night_howl_drinking_effect_4");
    public static final RegistryObject<SoundEvent> UNPACKING_PACKAGE = register("unpacking_package");


    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation("drinkbeer", name)));
    }
}
