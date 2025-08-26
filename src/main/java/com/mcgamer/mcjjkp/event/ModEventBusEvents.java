package com.mcgamer.mcjjkp.event;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.client.CursedEnergyHudOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = JJKMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void onRegisterGuiLayersEvent(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "hud.cursed_energy_overlay"),
                new CursedEnergyHudOverlay());
    }
}
