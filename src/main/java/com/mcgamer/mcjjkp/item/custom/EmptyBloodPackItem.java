package com.mcgamer.mcjjkp.item.custom;

import com.mcgamer.mcjjkp.components.ModDataComponents;
import com.mcgamer.mcjjkp.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EmptyBloodPackItem extends Item {
    public EmptyBloodPackItem(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if(timeCharged >= 60 && livingEntity instanceof Player player) {
            player.sendSystemMessage(Component.literal("HERE"));
            player.getUseItem().consume(1, livingEntity);
            player.addItem(new ItemStack(ModItems.BLOOD_PACK_ITEM.get()));

            ModItems.BLOOD_PACK_ITEM.toStack().set(ModDataComponents.OWNER, player.getName().toString());
        }
        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }
}
