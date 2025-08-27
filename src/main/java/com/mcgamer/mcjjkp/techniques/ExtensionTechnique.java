package com.mcgamer.mcjjkp.techniques;

import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public abstract class ExtensionTechnique {
    private final UUID type;
    private final String name;

    public ExtensionTechnique(UUID bendingType, String name) {
        this.type = bendingType;
        this.name = name;
    }

    protected String techniqueName() {
        return type.toString();
    }

    public final UUID getTechniqueId() {
        return type;
    }

    public String getName() {
        return name;
    }

    public abstract void execute();

    public static int getCooldown() {
        return 0;
    }

    public int requiredCursedEnergy() {
        return 0;
    }

    public final boolean hasRequiredCursedEnergy(Player player) {
        return player.getData(ModDataAttachments.CURSED_ENERGY_AVAILABLE) >= this.requiredCursedEnergy();
    }
}
