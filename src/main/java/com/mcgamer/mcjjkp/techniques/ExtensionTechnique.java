package com.mcgamer.mcjjkp.techniques;

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
        return 15;
    }
}
