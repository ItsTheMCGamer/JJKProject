package com.mcgamer.mcjjkp.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class HaemorrhageEffect extends MobEffect {
    protected HaemorrhageEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if(amplifier == 0) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false,
                    false), livingEntity);
        } if(amplifier >= 1) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 2, false,
                    false), livingEntity);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 2, false,
                    false), livingEntity);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 2, false,
                    false), livingEntity);
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
