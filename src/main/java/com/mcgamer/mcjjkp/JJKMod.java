package com.mcgamer.mcjjkp;

import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import com.mcgamer.mcjjkp.block.ModBlocks;
import com.mcgamer.mcjjkp.components.ModDataComponents;
import com.mcgamer.mcjjkp.effect.ModEffects;
import com.mcgamer.mcjjkp.entity.ModEntities;
import com.mcgamer.mcjjkp.entity.client.BloodTippedArrowRenderer;
import com.mcgamer.mcjjkp.item.ModItems;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniques;
import com.mcgamer.mcjjkp.techniques.InnateTechniques;
import com.mcgamer.mcjjkp.techniques.blood_manipulation.BloodManipulationTechnique;
import com.mcgamer.mcjjkp.techniques.blood_manipulation.FlowingRedScaleTechnique;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
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

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(JJKMod.MOD_ID)
public class JJKMod {
    public static final String MOD_ID = "mcjjkp";
    private static final Logger LOGGER = LogUtils.getLogger();

    private static void registerExtensionTechniques() {
        ExtensionTechniques.register(new FlowingRedScaleTechnique());

    }

    private static void registerInnateTechniques() {
        InnateTechniques.register(new BloodManipulationTechnique());

    }

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public JJKMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        registerExtensionTechniques();
        registerInnateTechniques();
        ModDataAttachments.register(modEventBus);
        ModDataComponents.register(modEventBus);

        ModEffects.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {

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
