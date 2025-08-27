package com.mcgamer.mcjjkp.techniques.blood_manipulation;

import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import com.mcgamer.mcjjkp.techniques.ExtensionTechnique;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class FlowingRedScaleTechnique extends ExtensionTechnique {

    public FlowingRedScaleTechnique() {
        super(BloodManipulationTechnique.ID, "flowing_red_scale");
    }

    @Override
    public void execute() {
        Player player = Minecraft.getInstance().player;
        if(hasRequiredCursedEnergy(player)) {

            player.setData(ModDataAttachments.CURSED_ENERGY_AVAILABLE,
                    player.getData(ModDataAttachments.CURSED_ENERGY_AVAILABLE) - this.requiredCursedEnergy());
            player.setData(ModDataAttachments.FLOWING_RED_SCALE_ACTIVE, true);
        }
    }

    public static int getCooldown() {
        return 20;
    }

    @Override
    public int requiredCursedEnergy() {
        return 15;
    }
}
