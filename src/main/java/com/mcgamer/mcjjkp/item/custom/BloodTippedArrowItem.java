package com.mcgamer.mcjjkp.item.custom;

import com.mcgamer.mcjjkp.entity.ModEntities;
import com.mcgamer.mcjjkp.entity.custom.BloodTippedArrowEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BloodTippedArrowItem extends ArrowItem {

    public BloodTippedArrowItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter,
                                     @Nullable ItemStack weapon) {
        return new BloodTippedArrowEntity(ModEntities.BLOOD_TIPPED_ARROW_ENTITY.get(), level);
    }

    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        BloodTippedArrowEntity bloodTippedArrow = new BloodTippedArrowEntity(level, pos.x(),
                pos.y(), pos.z(), stack.copyWithCount(1), (ItemStack)null);
        bloodTippedArrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return bloodTippedArrow;
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(0xD40D04);
    }
}
