package lekavar.lma.drinkbeer.items;

import lekavar.lma.drinkbeer.DrinkBeer;
import lekavar.lma.drinkbeer.registry.ItemRegistry;
import lekavar.lma.drinkbeer.registry.SoundEventRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class BeerMugItem extends BlockItem {
    public BeerMugItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEventRegistry.DRINKING_BEER.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String name = this.asItem().toString();
        if (this.asItem() != ItemRegistry.beerMugPumpkinKvass.get()) {
            tooltip.add(new TranslatableComponent("item.drinkbeer." + name + ".tooltip").setStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE)));
        }
        String hunger = String.valueOf(asItem().getFoodProperties().getNutrition());
        tooltip.add(new TranslatableComponent("drinkbeer.restores_hunger").setStyle(Style.EMPTY.applyFormat(ChatFormatting.BLUE)).append(hunger));
    }
}
