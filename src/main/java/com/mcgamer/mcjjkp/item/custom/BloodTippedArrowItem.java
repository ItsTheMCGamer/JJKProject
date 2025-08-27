package com.mcgamer.mcjjkp.item.custom;

import com.mcgamer.mcjjkp.components.ModDataComponents;
import com.mcgamer.mcjjkp.entity.custom.BloodTippedArrowEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class BloodTippedArrowItem extends ArrowItem {

    public BloodTippedArrowItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter,
                                     @Nullable ItemStack weapon) {
        if(Objects.equals(ammo.get(ModDataComponents.ARROW_OWNER), shooter.getName().toString())) {
            return new BloodTippedArrowEntity(level, shooter, ammo.copyWithCount(1), weapon);
        } else {
            return new Arrow(level, shooter, ammo.copyWithCount(1), weapon);
        }
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        BloodTippedArrowEntity bloodTippedArrow = new BloodTippedArrowEntity(level, pos.x(), pos.y(),
                pos.z(), stack.copyWithCount(1), (ItemStack)null);
        bloodTippedArrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return bloodTippedArrow;
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(0xD40D04);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.get(ModDataComponents.ARROW_OWNER) != null) {
            tooltipComponents.add(Component.literal(stack.get(ModDataComponents.ARROW_OWNER)
                    .replaceFirst("^literal\\{", "").replaceFirst("}", "")
            + "'s Blood"));
        }


        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
