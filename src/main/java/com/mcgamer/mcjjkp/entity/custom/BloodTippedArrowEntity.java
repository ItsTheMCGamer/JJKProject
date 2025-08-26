package com.mcgamer.mcjjkp.entity.custom;

import com.mcgamer.mcjjkp.entity.ModEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class BloodTippedArrowEntity extends AbstractArrow {

    public BloodTippedArrowEntity(EntityType<? extends BloodTippedArrowEntity> entityType, Level level) {
        super(entityType, level);
        this.entityData.isDirty();
    }

    public BloodTippedArrowEntity(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.BLOOD_TIPPED_ARROW_ENTITY.get(), owner, level, pickupItemStack, firedFromWeapon);
        this.entityData.isDirty();
    }

    public BloodTippedArrowEntity(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.BLOOD_TIPPED_ARROW_ENTITY.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
        this.entityData.isDirty();
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 targetPos = this.getDeltaMovement();
        float distance = 20;

        if (!this.inGround && this.getOwner() != null && !this.isInWaterOrRain()) {
            this.level().addParticle(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY(), this.getZ(),
                    0, 0, 0);

            List<LivingEntity> entitiesInRange = this.level().getEntitiesOfClass(LivingEntity.class,
                    this.getBoundingBox().inflate(20), e -> e.isAlive() && e != this.getOwner());
            LivingEntity target = null;

            for(LivingEntity entity : entitiesInRange) {
                if(entity.distanceTo(this) < distance) {
                    distance = entity.distanceTo(this);
                    targetPos = entity.position();
                    target = entity;
                }
            }
            redirectArrow(targetPos, target);
        } else if(this.isInWaterOrRain() && !this.level().isClientSide) {
            var normalArrow = new Arrow(
                    this.level(),
                    (LivingEntity)this.getOwner(),
                    this.getDefaultPickupItem(),
                    this.getWeaponItem()
            );

            normalArrow.setDeltaMovement(this.getDeltaMovement());

            normalArrow.setPos(this.position());

            normalArrow.setXRot(this.getXRot());
            normalArrow.setYRot(this.getYRot());

            normalArrow.pickup = this.pickup;

            this.level().addFreshEntity(normalArrow);

            this.playSound(SoundEvents.FIRE_EXTINGUISH, 0.5F, 2.7F);

            this.discard();
            return;
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    public final void redirectArrow(Vec3 targetPos, LivingEntity target) {
        if(target != null) {
            targetPos = new Vec3(targetPos.x, targetPos.y + 0.5 * target.getEyeHeight(), targetPos.z);
            Vec3 thisPos = position();
            Vec3 thisVelocity = getDeltaMovement();
            Vec3 toTarget = targetPos.subtract(thisPos);
            double thisSpeed = thisVelocity.length();

            //double homingStrength = Math.clamp(0.05 + (1 - (distance / 20)) * 0.4, 0.05, 0.25);;
            double homingStrength = 0.15;
            Vec3 newVelocity = thisVelocity.scale(1 - homingStrength)
                    .add(toTarget.normalize().scale(thisSpeed * homingStrength));
            setDeltaMovement(newVelocity);
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if(this.getOwner() instanceof ServerPlayer player) {
            System.out.println("Firing Take Aim trigger");
            CriteriaTriggers.PLAYER_HURT_ENTITY.trigger(
                    player,
                    result.getEntity(),
                    this.damageSources().arrow(this, this.getOwner()),
                    (float)this.getBaseDamage(),
                    (float)this.getBaseDamage(),
                    false);
        }
    }
}
