package com.mcgamer.mcjjkp.events;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.gui.overlays.CursedEnergyHudOverlay;
import com.mcgamer.mcjjkp.particles.ModParticles;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "hud.cursed_energy_overlay"),
                new CursedEnergyHudOverlay());
    }

    @SubscribeEvent
    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        ModParticles.registerParticleFactory(event);
    }
}
