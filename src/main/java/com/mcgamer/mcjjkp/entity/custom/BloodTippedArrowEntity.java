package com.mcgamer.mcjjkp.entity.custom;

import com.mcgamer.mcjjkp.entity.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BloodTippedArrowEntity extends AbstractArrow {

    int lifetime = 0;

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

        Random random = new Random();
        Vec3 targetPos = this.getDeltaMovement();
        float distance = 10;

        lifetime++;

        if (!this.inGround && this.getOwner() != null) {
            this.level().addParticle(ParticleTypes.FALLING_LAVA, this.getX(), this.getY(), this.getZ(),
                    random.nextInt(1), 0, random.nextInt(1));

            List<Player> playersInRange = this.level().getEntitiesOfClass(Player.class,
                    this.getBoundingBox().inflate(4));
            List<Monster> monstersInRange = this.level().getEntitiesOfClass(Monster.class,
                    this.getBoundingBox().inflate(4));
            LivingEntity target = null;

            if(!playersInRange.isEmpty()  && lifetime > 5) {
                for(Player player : playersInRange) {
                    if(player.distanceTo(this) < distance && player != this.getOwner()) {
                        distance = player.distanceTo(this);
                        targetPos = player.position();
                        target = player;
                    }
                }
                redirectArrow(targetPos, target);
            } else if(!monstersInRange.isEmpty()) {
                for(Monster monster : monstersInRange) {
                    if(monster.distanceTo(this) < distance) {
                        distance = monster.distanceTo(this);
                        targetPos = monster.position();
                        target = monster;
                    }
                }
                redirectArrow(targetPos, target);
            }
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    public final void redirectArrow(Vec3 targetPos, LivingEntity target) {
        targetPos = new Vec3(targetPos.x, targetPos.y + target.getEyeHeight(), targetPos.z);
        var thisPos = position();
        var thisVelocity = getDeltaMovement();
        var normalizedDifference = targetPos.subtract(thisPos).normalize();
        var thisSpeed = thisVelocity.length();
        setDeltaMovement(normalizedDifference.scale(thisSpeed));
    }
}
