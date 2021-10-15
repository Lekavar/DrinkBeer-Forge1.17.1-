package lekavar.lma.drinkbeer.registry;

import lekavar.lma.drinkbeer.container.BeerBarrelContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, "drinkbeer");
    public static final RegistryObject<MenuType<BeerBarrelContainer>> beerBarrelContainer = CONTAINERS.register("beer_barrel_container", () -> IForgeContainerType.create((int windowId, Inventory inv, FriendlyByteBuf data) -> new BeerBarrelContainer(windowId, inv, data)));
}