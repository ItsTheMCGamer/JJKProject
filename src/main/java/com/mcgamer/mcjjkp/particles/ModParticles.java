package com.mcgamer.mcjjkp.particles;

import com.mcgamer.mcjjkp.JJKMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, JJKMod.MOD_ID);

    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> BLACK_FLASH_PARTICLE =
            PARTICLES.register("black_flash_particle", LodestoneWorldParticleType::new);


    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        for (DeferredHolder<ParticleType<?>, ? extends ParticleType<?>> entry : PARTICLES.getEntries()) {
            event.registerSpriteSet((LodestoneWorldParticleType)entry.get(), LodestoneWorldParticleType.Factory::new);
        }
    }
}
