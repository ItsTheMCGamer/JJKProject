package com.mcgamer.mcjjkp.effect.custom;

import com.mcgamer.mcjjkp.techniques.ExtensionTechniqueRegistry;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniques;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FlowingRedScaleEffect extends MobEffect {
    public FlowingRedScaleEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        int energyCostPerSecond = ExtensionTechniqueRegistry.getTechnique(ExtensionTechniques.FLOWING_RED_SCALE).getEnergyCostPerSecond();
        if(livingEntity instanceof LocalPlayer player && livingEntity.tickCount % 20 == 0) {
            //ModDataAttachments.consumeCursedEnergy(player, energyCostPerSecond);
        }


        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
