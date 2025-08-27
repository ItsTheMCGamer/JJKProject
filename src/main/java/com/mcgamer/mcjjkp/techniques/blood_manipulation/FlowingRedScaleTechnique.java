package com.mcgamer.mcjjkp.techniques.blood_manipulation;

import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import com.mcgamer.mcjjkp.effect.ModEffects;
import com.mcgamer.mcjjkp.networking.ModMessages;
import com.mcgamer.mcjjkp.networking.packets.S2CToggleTechnique;
import com.mcgamer.mcjjkp.techniques.ExtensionTechnique;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public class FlowingRedScaleTechnique extends ExtensionTechnique {
    public FlowingRedScaleTechnique() {
        super("blood_manipulation", "flowing_red_scale", 3, 20, true);
    }

    @Override
    public void activate(Player player) {
        if (canUse(player)) {
            ModDataAttachments.consumeCursedEnergy(player, getEnergyCost());

            startCooldown();

            setActive(true);
            player.addEffect(new MobEffectInstance(ModEffects.FLOWING_RED_SCALE_EFFECT, 200, 0, false, true));
        } else {
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("Not enough cursed energy to use " + getName() + "!"), true);
            deactivate(player);
        }
    }

    @Override
    public void deactivate(Player player) {
        player.removeEffect(ModEffects.FLOWING_RED_SCALE_EFFECT);
        if (player instanceof ServerPlayer serverPlayer) {
            ModMessages.sendToPlayerClient(new S2CToggleTechnique(false, getName()), serverPlayer);
        }
        setActive(false);
    }
}
