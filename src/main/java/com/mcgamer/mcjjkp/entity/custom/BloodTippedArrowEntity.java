package com.mcgamer.mcjjkp.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class BloodTippedArrowEntity extends Arrow {

    @Nullable
    private LivingEntity lockedTarget = null;

    public BloodTippedArrowEntity(EntityType<? extends BloodTippedArrowEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BloodTippedArrowEntity(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(level, owner, pickupItemStack, firedFromWeapon);
    }

    public BloodTippedArrowEntity(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(level, x, y, z, pickupItemStack, firedFromWeapon);
    }

    @Override
    public void tick() {
        super.tick();

        float distance = 10;

        if (!this.inGround && this.getOwner() != null && !this.isInWaterOrRain()) {
            this.level().addParticle(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY(), this.getZ(),
                    0, 0, 0);

            // Acquire target once
            if (lockedTarget == null || !lockedTarget.isAlive()) {
                List<LivingEntity> entitiesInRange = this.level().getEntitiesOfClass(
                        LivingEntity.class,
                        this.getBoundingBox().inflate(distance),
                        e -> e.isAlive() && e != this.getOwner()
                );

                lockedTarget = entitiesInRange.stream()
                        .min((a, b) -> Double.compare(a.distanceTo(this), b.distanceTo(this)))
                        .orElse(null);
            }

            if (lockedTarget != null) {
                redirectArrow(lockedTarget);
            }
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
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    public final void redirectArrow(LivingEntity target) {
        if(target != null) {
            Vec3 targetPos = new Vec3(target.getX(), target.getY() + 0.5 * target.getEyeHeight(), target.getZ());
            Vec3 thisPos = position();
            Vec3 thisVelocity = getDeltaMovement();
            Vec3 toTarget = targetPos.subtract(thisPos);
            double thisSpeed = thisVelocity.length();

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

        if (!this.level().isClientSide && this.getOwner() instanceof ServerPlayer serverPlayer) {
            var mgr = serverPlayer.server.getAdvancements();
            var adv = mgr.get(ResourceLocation.fromNamespaceAndPath("minecraft", "adventure/shoot_arrow"));
            if (adv != null) {
                var acc = serverPlayer.getAdvancements();
                for (var crit : acc.getOrStartProgress(adv).getRemainingCriteria()) {
                    acc.award(adv, crit);
                }
                System.out.println("Take Aim manually awarded.");
            }
        }
    }
}
