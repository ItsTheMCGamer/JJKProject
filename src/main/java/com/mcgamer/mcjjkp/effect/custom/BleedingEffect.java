package com.mcgamer.mcjjkp.effect.custom;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.effect.ModEffects;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BleedingEffect extends MobEffect {
    public BleedingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {

        if(!livingEntity.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "bleeding_weakness"))) {
            livingEntity.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID,
                    "bleeding_weakness"), -4.0F, AttributeModifier.Operation.ADD_VALUE));
        }

        if(!livingEntity.getAttribute(Attributes.BLOCK_BREAK_SPEED).hasModifier(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "bleeding_mining"))) {
            livingEntity.getAttribute(Attributes.BLOCK_BREAK_SPEED).addPermanentModifier(new AttributeModifier(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID,
                    "bleeding_mining"), -0.9F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        if(!livingEntity.getAttribute(Attributes.ATTACK_SPEED).hasModifier(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "bleeding_attack"))) {
            livingEntity.getAttribute(Attributes.ATTACK_SPEED).addPermanentModifier(new AttributeModifier(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID,
                    "bleeding_attack"), -0.7F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeMap) {
        if (attributeMap.hasAttribute(Attributes.ATTACK_DAMAGE)) {
            attributeMap.getInstance(Attributes.ATTACK_DAMAGE)
                    .removeModifier(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "bleeding_weakness"));
        }
        if (attributeMap.hasAttribute(Attributes.BLOCK_BREAK_SPEED)) {
            attributeMap.getInstance(Attributes.BLOCK_BREAK_SPEED)
                    .removeModifier(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "bleeding_mining"));
        }


        super.removeAttributeModifiers(attributeMap);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int frequency = 20;
        return duration % frequency == 0;
    }
}
