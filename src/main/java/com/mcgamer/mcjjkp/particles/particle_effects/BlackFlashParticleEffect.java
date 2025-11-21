package com.mcgamer.mcjjkp.particles.particle_effects;

import com.mcgamer.mcjjkp.particles.ModParticles;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;

public class BlackFlashParticleEffect {

    public void spawnParticles(Vec3 pos, Level level) {
        int amount = (int) Math.ceil(Mth.randomBetweenInclusive(RandomSource.create(), 35, 45));
        double spawnRadius = Mth.randomBetween(RandomSource.create(), 0.45F, 0.55F);
        double motionScale = Mth.randomBetween(RandomSource.create(), 0.95F, 1.05F);

        var particle = WorldParticleBuilder.create(ModParticles.BLACK_FLASH_PARTICLE)
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setScaleData(GenericParticleData
                        .create(Mth.randomBetween(RandomSource.create(), 0.55F, 0.45F),
                        Mth.randomBetween(RandomSource.create(), 0.1F, 0.2F))
                        .setEasing(Easing.QUAD_IN_OUT).build())
                .setTransparencyData(GenericParticleData.create(1F, 0.5F).setEasing(Easing.QUARTIC_IN).build())
                .setSpinData(SpinParticleData.create(-0.04F, 0.04F).build())
                .setRandomMotion(1) //to modify to add unpredictable oscillations
                .setLifetime(30)
                .setFullBrightLighting()
                .disableNoClip();


        for(int i = 0; i <= amount - 1; i++) {
            var u = Mth.randomBetween(RandomSource.create(), 0.0F, 1.0F);
            var v = Mth.randomBetween(RandomSource.create(), 0.0F, 1.0F);

            var theta = Mth.TWO_PI * u;
            var phi = Math.acos(2.0F * v - 1.0F);

            var x = Mth.sin((float)phi) * Mth.cos(theta) * spawnRadius;
            var y = Mth.sin((float)phi) * Mth.sin(theta) * spawnRadius;
            var z = Mth.cos((float)phi) * spawnRadius;

            Vec3 motionVec = new Vec3(x * motionScale, y * motionScale, z * motionScale);

            particle.setMotion(motionVec);
            particle.spawn(level, pos.x + x, pos.y + y, pos.z + z);
        }
    }
}
