package com.mcgamer.mcjjkp.item.custom;

import com.mcgamer.mcjjkp.particles.ModParticleEffects;
import com.mcgamer.mcjjkp.particles.particle_effects.BlackFlashParticleEffect;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlackFlashItem extends Item {

    public BlackFlashItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(player.level().isClientSide) {
            ModParticleEffects.BLACK_FLASH_PARTICLE_EFFECT.spawnParticles(entity.position(), player.level());
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
