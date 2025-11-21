package com.mcgamer.mcjjkp.techniques.ten_shadows;

import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import com.mcgamer.mcjjkp.gui.overlays.TechniqueItemOverlay;
import com.mcgamer.mcjjkp.techniques.ExtensionTechnique;
import net.minecraft.world.entity.player.Player;

public class ShadowBladeTechnique extends ExtensionTechnique {
    public ShadowBladeTechnique() {
        super("ten_shadows", "Shadow Blade", 20, 20, true,
                true, 5);
    }

    @Override
    public void activate(Player player) {
        ModDataAttachments.consumeCursedEnergy(player, this.getEnergyCost());

        TechniqueItemOverlay.showTechniqueSlot(this.getName());
    }

    @Override
    public void deactivate(Player player) {
        TechniqueItemOverlay.hideTechniqueSlot();
    }
}
