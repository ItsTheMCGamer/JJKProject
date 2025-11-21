package com.mcgamer.mcjjkp.item.custom;

import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import com.mcgamer.mcjjkp.components.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BloodPackItem extends Item {
    public BloodPackItem(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if(timeCharged >= 60 && livingEntity instanceof Player player) {
            player.setData(ModDataAttachments.BLOOD_DRAWN, player.getData(ModDataAttachments.BLOOD_DRAWN) - 2);

            player.getUseItem().consume(1, player);
        }

        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
                                TooltipFlag tooltipFlag) {
        if (stack.get(ModDataComponents.OWNER) != null) {
            tooltipComponents.add(Component.literal(stack.get(ModDataComponents.OWNER)
                    .replaceFirst("^literal\\{", "").replaceFirst("}", "")
                    + "'s Blood"));
        }
    }
}
