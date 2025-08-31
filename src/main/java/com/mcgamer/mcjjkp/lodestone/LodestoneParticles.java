package com.mcgamer.mcjjkp.lodestone;

import net.minecraft.client.*;
import net.minecraft.client.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.awt.Color;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class LodestoneParticles {

    private static int tickCounter = 0;
    private static boolean lodestoneInitialized = false;

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            tickCounter++;

            // Only spawn particles occasionally, not every tick
            if (tickCounter % 20 == 0) { // Every second (20 ticks)
                spawnExampleParticles(player.level(), player.position());
            }
        }
    }

    public static void spawnExampleParticles(Level level, Vec3 pos) {
        if (!level.isClientSide) return;

        // Ensure we're on the main thread
        if (!Minecraft.getInstance().isSameThread()) {
            Minecraft.getInstance().execute(() -> spawnExampleParticles(level, pos));
            return;
        }

        // Initialize Lodestone if not already done
        if (!lodestoneInitialized) {
            try {
                // Force initialization of Lodestone classes
                team.lodestar.lodestone.registry.client.LodestoneRenderTypes.class.getName();
                team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType.class.getName();
                lodestoneInitialized = true;
                System.out.println("Lodestone initialized successfully in particles class!");
            } catch (Exception e) {
                System.err.println("Failed to initialize Lodestone in particles: " + e.getMessage());
                return; // Don't try to spawn particles if initialization failed
            }
        }

        try {
            Color startingColor = new Color(100, 0, 100);
            Color endingColor = new Color(0, 100, 200);
            WorldParticleBuilder.create(LodestoneParticleTypes.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.5f, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.75f, 0.25f).build())
                    .setColorData(ColorParticleData.create(startingColor, endingColor).setCoefficient(1.4f).setEasing(Easing.BOUNCE_IN_OUT).build())
                    .setSpinData(SpinParticleData.create(0.2f, 0.4f).setSpinOffset((level.getGameTime() * 0.2f) % 6.28f).setEasing(Easing.QUARTIC_IN).build())
                    .setLifetime(40)
                    .addMotion(0, 0.01f, 0)
                    .enableNoClip()
                    .spawn(level, pos.x, pos.y, pos.z);
        } catch (Exception e) {
            System.err.println("Failed to spawn Lodestone particles: " + e.getMessage());
            e.printStackTrace();
            // Reset initialization flag so we can try again
            lodestoneInitialized = false;
        }
    }
}