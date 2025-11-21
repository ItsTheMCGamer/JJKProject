package com.mcgamer.mcjjkp;

import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import com.mcgamer.mcjjkp.block.ModBlocks;
import com.mcgamer.mcjjkp.components.ModDataComponents;
import com.mcgamer.mcjjkp.effect.ModEffects;
import com.mcgamer.mcjjkp.entity.ModEntities;
import com.mcgamer.mcjjkp.entity.client.BloodTippedArrowRenderer;
import com.mcgamer.mcjjkp.item.ModItems;
import com.mcgamer.mcjjkp.particles.ModParticles;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniqueRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(JJKMod.MOD_ID)
public class JJKMod {
    public static final String MOD_ID = "mcjjkp";
    private static final Logger LOGGER = LogUtils.getLogger();

    public JJKMod(IEventBus modEventBus, ModContainer modContainer) {

        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        ExtensionTechniqueRegistry.init();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModDataAttachments.register(modEventBus);
        ModDataComponents.register(modEventBus);

        ModParticles.PARTICLES.register(modEventBus);

        ModEffects.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModItems.BLOOD_PACK_ITEM.get());
            event.accept(ModItems.EMPTY_BLOOD_PACK_ITEM.get());
            event.accept(ModItems.BLOOD_EDGE_ITEM.get());
            event.accept(ModItems.SHADOW_BLADE_ITEM.get());
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        for(Player player : event.getServer().getPlayerList().getPlayers()) {
            System.out.println("Player added: " + player.getName());
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            EntityRenderers.register(ModEntities.BLOOD_TIPPED_ARROW_ENTITY.get(), BloodTippedArrowRenderer::new);

        }
    }

    public static ResourceLocation prefix(String str) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, str);
    }
}
