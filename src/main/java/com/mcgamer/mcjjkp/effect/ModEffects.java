package com.mcgamer.mcjjkp.effect;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.effect.custom.FlowingRedScaleEffect;
import com.mcgamer.mcjjkp.effect.custom.FlowingRedScaleStackEffect;
import com.mcgamer.mcjjkp.effect.custom.BleedingEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, JJKMod.MOD_ID);


    public static final Holder<MobEffect> FLOWING_RED_SCALE_EFFECT = MOB_EFFECTS.register("flowing_red_scale",
            () -> new FlowingRedScaleEffect(MobEffectCategory.BENEFICIAL, 0xff0000)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "flowing_red_scale_speed"), 0.2f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE,
                            ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "flowing_red_scale_strength"), 0.2f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final Holder<MobEffect> FLOWING_RED_SCALE_STACK_EFFECT =
            MOB_EFFECTS.register("flowing_red_scale_stack", () ->
                    new FlowingRedScaleStackEffect(MobEffectCategory.BENEFICIAL, 0xff0000)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "flowing_red_scale_stack_speed"), 0.6f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE,
                            ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "flowing_red_scale_stack_strength"), 0.6f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final Holder<MobEffect> BLEEDING_EFFECT = MOB_EFFECTS.register("bleeding", () ->
                    new BleedingEffect(MobEffectCategory.HARMFUL, 0xff0000));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
