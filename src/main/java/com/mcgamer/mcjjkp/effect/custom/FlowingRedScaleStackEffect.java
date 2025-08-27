package com.mcgamer.mcjjkp.effect.custom;

import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import com.mcgamer.mcjjkp.effect.ModEffects;
import com.mcgamer.mcjjkp.networking.ModMessages;
import com.mcgamer.mcjjkp.networking.packets.S2CSyncCursedEnergy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import static com.mcgamer.mcjjkp.attachments.ModDataAttachments.CURSED_ENERGY_AVAILABLE;

public class FlowingRedScaleStackEffect extends MobEffect {
    public FlowingRedScaleStackEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide && entity.tickCount % 20 == 0) {
            if (entity instanceof Player player && player.getData(ModDataAttachments.CURSED_ENERGY_AVAILABLE) >= 10) {
                player.setData(ModDataAttachments.CURSED_ENERGY_AVAILABLE,
                        player.getData(ModDataAttachments.CURSED_ENERGY_AVAILABLE) - 10);
                ModMessages.sendToPlayerClient(new S2CSyncCursedEnergy(player.getData(CURSED_ENERGY_AVAILABLE)),
                        (ServerPlayer)player);
            } else {
                entity.removeEffect(ModEffects.FLOWING_RED_SCALE_STACK_EFFECT);
            }
        }

        return super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
