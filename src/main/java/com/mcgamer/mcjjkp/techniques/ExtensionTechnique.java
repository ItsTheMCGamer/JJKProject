package com.mcgamer.mcjjkp.techniques;

import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class ExtensionTechnique {
    private final String parentTechnique;
    private final String name;
    private final int energyCost;
    private final int cooldown;
    private final boolean isMaxOutput;
    private final boolean isToggleable;
    private final int energyCostPerSecond;


    private long lastUsed = 0;
    private boolean isActive = false;

    public ExtensionTechnique(String innateTechnique, String name, int energyCost, int cooldown, boolean isMaxOutput,
                              boolean isToggleable, int energyCostPerSecond) {
        this.parentTechnique = innateTechnique;
        this.name = name;
        this.energyCost = energyCost;
        this.cooldown = cooldown;
        this.isMaxOutput = isMaxOutput;
        this.isToggleable = isToggleable;
        this.energyCostPerSecond = isToggleable ? energyCostPerSecond : 0;
    }

    Map<String, String> innateTechniqueColor = new HashMap<String, String>();

    {
        innateTechniqueColor.put("blood_manipulation", "§4");
    }

    public abstract void activate(Player player);
    public abstract void deactivate(Player player);

    public boolean canUse(Player player) {
        return player.hasData(ModDataAttachments.CURSED_ENERGY_AVAILABLE)
                && ModDataAttachments.getCursedEnergy(player) >= energyCost && !isOnCooldown() && !isActive;
    }

    public boolean isOnCooldown() {
        return (System.currentTimeMillis() - lastUsed) * 1000 < cooldown;
    }

    public void startCooldown() {
        this.lastUsed = System.currentTimeMillis();
    }

    public void toggle(Player player) {
        if (isToggleable) {
            if (isActive) {
                deactivate(player);
                isActive = false;
            } else {
                activate(player);
                isActive = true;
            }
        }
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void setLastUsed(long lastUsed) {
        this.lastUsed = lastUsed;
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("lastUsed", lastUsed);
        tag.putBoolean("isActive", isActive);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        lastUsed = tag.getLong("lastUsed");
        isActive = tag.getBoolean("isActive");
    }

    public String getName() {
        return name;
    }
    public String getParentTechnique() {
        return parentTechnique;
    }
    public int getEnergyCost() {
        return energyCost;
    }
    public int getEnergyCostPerSecond() {
        return energyCostPerSecond;
    }
    public int getCooldown() {
        return cooldown;
    }
    public boolean isToggleable() {
        return isToggleable;
    }
    public boolean isActive() {
        return isActive;
    }
    public boolean isMaxOutput() {
        return isMaxOutput;
    }
    public String getInnateTechniqueColor() {
        return innateTechniqueColor.getOrDefault(parentTechnique, "§f");
    }
}
