package com.mcgamer.mcjjkp.entity.custom;

import com.mcgamer.mcjjkp.entity.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class BloodTippedArrowEntity extends AbstractArrow {

    public BloodTippedArrowEntity(EntityType<? extends BloodTippedArrowEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BloodTippedArrowEntity(Level level, LivingEntity owner, ItemStack pickupItemStack, @javax.annotation.Nullable ItemStack firedFromWeapon) {
        super(ModEntities.BLOOD_TIPPED_ARROW_ENTITY.get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    public BloodTippedArrowEntity(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.BLOOD_TIPPED_ARROW_ENTITY.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }

    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
