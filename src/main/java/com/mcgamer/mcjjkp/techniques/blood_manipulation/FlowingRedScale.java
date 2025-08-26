package com.mcgamer.mcjjkp.techniques.blood_manipulation;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import com.mcgamer.mcjjkp.event.ModEvents;
import com.mcgamer.mcjjkp.networking.ModMessages;
import com.mcgamer.mcjjkp.networking.packets.S2CFlowingRedScaleActive;
import com.mcgamer.mcjjkp.networking.packets.S2CSyncCursedEnergy;
import com.mcgamer.mcjjkp.techniques.ExtensionTechnique;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import static com.mcgamer.mcjjkp.attachments.ModDataAttachments.CURSED_ENERGY_AVAILABLE;
import static com.mcgamer.mcjjkp.attachments.ModDataAttachments.FLOWING_RED_SCALE_ACTIVE;

public class FlowingRedScale extends ExtensionTechnique {

    public FlowingRedScale() {
        super(BloodManipulationTechnique.ID, "flowing_red_scale");
    }

    @Override
    public void execute() {
        Player player = Minecraft.getInstance().player;

        if(ModEvents.slotOneCooldown >= getCooldown() && hasRequiredCursedEnergy(player)) {
            ModEvents.slotOneCooldown = 0;
            player.setData(ModDataAttachments.CURSED_ENERGY_AVAILABLE,
                    player.getData(ModDataAttachments.CURSED_ENERGY_AVAILABLE) - this.requiredCursedEnergy());
            player.setData(ModDataAttachments.FLOWING_RED_SCALE_ACTIVE, true);


        }
    }

    public static int getCooldown() {
        return 10;
    }

    @Override
    public int requiredCursedEnergy() {
        return 15;
    }
}
