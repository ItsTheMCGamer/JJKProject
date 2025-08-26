package com.mcgamer.mcjjkp.setup;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.effect.ModEffects;
import com.mcgamer.mcjjkp.render.FlowingRedScaleLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.mcgamer.mcjjkp.render.FlowingRedScaleLayer.NORMAL_TEXTURE;
import static com.mcgamer.mcjjkp.render.FlowingRedScaleLayer.STACK_TEXTURE;

@EventBusSubscriber(modid = JJKMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        LayerDefinition energyOverlayLayer = LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.5F), 0.0F), 64, 64);

        event.registerLayerDefinition(FlowingRedScaleLayer.Vanilla.ENERGY_LAYER, () -> energyOverlayLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.AddLayers event) {
        addLayerToPlayerSkin(event, PlayerSkin.Model.SLIM);
        addLayerToPlayerSkin(event, PlayerSkin.Model.WIDE);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, PlayerSkin.Model skinName) {
        EntityRenderer<? extends Player> render = event.getSkin(skinName);
        if (render instanceof LivingEntityRenderer livingRenderer) {
            livingRenderer.addLayer(new FlowingRedScaleLayer.Vanilla(livingRenderer, STACK_TEXTURE, ModEffects.FLOWING_RED_SCALE_STACK_EFFECT));
            livingRenderer.addLayer(new FlowingRedScaleLayer.Vanilla(livingRenderer, NORMAL_TEXTURE, ModEffects.FLOWING_RED_SCALE_EFFECT));
        }
    }
}
