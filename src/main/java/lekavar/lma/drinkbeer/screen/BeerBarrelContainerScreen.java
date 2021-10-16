package lekavar.lma.drinkbeer.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lekavar.lma.drinkbeer.container.BeerBarrelContainer;
import lekavar.lma.drinkbeer.registry.SoundEventRegistry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

public class BeerBarrelContainerScreen extends AbstractContainerScreen<BeerBarrelContainer> {

    private final ResourceLocation BEER_BARREL_CONTAINER_RESOURCE = new ResourceLocation("drinkbeer", "textures/gui/container/beer_barrel.png");
    private final int textureWidth = 176;
    private final int textureHeight = 166;
    private Inventory inventory;

    public BeerBarrelContainerScreen(BeerBarrelContainer screenContainer, Inventory inv, Component title) {
        super(screenContainer, inv, title);
        this.imageWidth = textureWidth;
        this.imageHeight = textureHeight;

        this.inventory = inv;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        renderBackground(stack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BEER_BARREL_CONTAINER_RESOURCE);
        int i = (this.width - this.getXSize()) / 2;
        int j = (this.height - this.getYSize()) / 2;
        blit(stack, i, j, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(PoseStack stack, int x, int y) {
        drawCenteredString(stack, this.font, this.title, (int) this.textureWidth / 2, (int) this.titleLabelY, 4210752);
        this.font.draw(stack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 4210752);
        String str = menu.getIsBrewing() == 1 ? convertTickToTime(menu.getRemainingBrewingTime()) : convertTickToTime(menu.getBrewingTimeInResultSlot());
        int i = (this.width - this.textureWidth) / 2;
        int j = (this.height - this.textureHeight) / 2;
        this.font.draw(stack, str, (float) 128, (float) 54, new Color(64, 64, 64, 255).getRGB());
        if (menu.pouring) {
            this.inventory.player.level.playSound(this.inventory.player, this.inventory.player.getOnPos().offset(0, 1, 0), SoundEventRegistry.POURING.get(), SoundSource.BLOCKS, 1f, 1f);
            menu.stopPouring();
        }
    }

    public String convertTickToTime(int tick) {
        String result;
        if (tick > 0) {
            double time = tick / 20;
            int m = (int) (time / 60);
            int s = (int) (time % 60);
            result = m + ":" + s;
        } else result = "";
        return result;
    }
}
