package lekavar.lma.drinkbeer.registries;

import lekavar.lma.drinkbeer.recipes.BrewingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegistry {
    public static class Type {
        public static final RecipeType<BrewingRecipe> BREWING = RecipeType.register("drinkbeer:brewing");
    }

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "drinkbeer");
    public static final RegistryObject<RecipeSerializer<BrewingRecipe>> BREWING = RECIPE_SERIALIZERS.register("brewing", BrewingRecipe.Serializer::new);
}
