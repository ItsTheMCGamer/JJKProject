package com.mcgamer.mcjjkp.screen;

import com.mcgamer.mcjjkp.JJKMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TechniquesScreen extends Screen {

    ResourceLocation BLOOD_MANIPULATION = ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID,
            "textures/screen/blood_manipulation_innate_technique.png");

    public TechniquesScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();

        //this.addRenderableWidget(new ImageButton(100, 100, 32, 32,
        //        new WidgetSprites(BLOOD_MANIPULATION, BLOOD_MANIPULATION), b ->
        //        Minecraft.getInstance().setScreen(null)));

        ImageButton button = new ImageButton(100, 100, 32, 32,
                new WidgetSprites(BLOOD_MANIPULATION, BLOOD_MANIPULATION), b -> Minecraft.getInstance().setScreen(null));
        this.addRenderableWidget(button);

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int height = this.height / 2;
        int width = this.width / 2;
        WidgetSprites sprites = new WidgetSprites(BLOOD_MANIPULATION, BLOOD_MANIPULATION);


        super.renderBackground(graphics, mouseX, mouseY, partialTick);

        this.addRenderableWidget(new ImageButton(width + 100, height + 100, 32, 32, sprites,
                b -> Minecraft.getInstance().setScreen(null)));

        super.render(graphics, mouseX, mouseY, partialTick);

        //graphics.blit(BLOOD_MANIPULATION, 300, 300, 0, 0, 32, 32);
        graphics.blit(BLOOD_MANIPULATION, width, height, 0, 0, 40, 40,
                40, 40);


        //graphics.renderComponentHoverEffect(this.font, Style.EMPTY,
        //        100, 100);
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
