package com.mcgamer.mcjjkp.techniques.blood_manipulation;

import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import com.mcgamer.mcjjkp.gui.overlays.TechniqueItemOverlay;
import com.mcgamer.mcjjkp.techniques.ExtensionTechnique;
import net.minecraft.world.entity.player.Player;

public class BloodEdgeTechnique extends ExtensionTechnique {
    public BloodEdgeTechnique() {
        super("blood_manipulation", "Blood Edge", 20, 20, true,
                true, 4);
    }

    @Override
    public void activate(Player player) {
        ModDataAttachments.consumeCursedEnergy(player, getEnergyCost());

        TechniqueItemOverlay.showTechniqueSlot(getName());

    }

    @Override
    public void deactivate(Player player) {

    }
}
