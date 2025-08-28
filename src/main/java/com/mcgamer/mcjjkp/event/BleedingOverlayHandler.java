package com.mcgamer.mcjjkp.event;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.effect.ModEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = JJKMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class BleedingOverlayHandler {
    private static final ResourceLocation BLOOD_OVERLAY_LOCATION = ResourceLocation
            .fromNamespaceAndPath(JJKMod.MOD_ID, "textures/overlay/floaters.png");

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        MobEffectInstance bleedingEffect = minecraft.player.getEffect(ModEffects.BLEEDING_EFFECT);
        if (bleedingEffect == null) return;

        float intensity = 0.8F;

        renderBleedingOverlay(event.getGuiGraphics(), intensity);
    }

    private static void renderBleedingOverlay(GuiGraphics guiGraphics, float scalar) {
        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        guiGraphics.pose().pushPose();

        float scale = Mth.lerp(scalar * 0.3F, 1.0F, 1.05F); // Subtle scaling
        guiGraphics.pose().translate((float)screenWidth / 2.0F, (float)screenHeight / 2.0F, 0.0F);
        guiGraphics.pose().scale(scale, scale, scale);
        guiGraphics.pose().translate((float)(-screenWidth) / 2.0F, (float)(-screenHeight) / 2.0F, 0.0F);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();

        float red = 1.0F;
        float green = 0.08F * (1.0F - scalar * 0.4F);
        float blue = 0.08F * (1.0F - scalar * 0.5F);
        float alpha = scalar * 0.5F;

        guiGraphics.setColor(red, green, blue, alpha);
        guiGraphics.blit(BLOOD_OVERLAY_LOCATION, 0, 0, 0, 0.0F, 0.0F,
                screenWidth, screenHeight, screenWidth, screenHeight);

        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();

        guiGraphics.pose().popPose();
    }
}