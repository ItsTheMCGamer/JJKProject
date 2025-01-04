package com.mcgamer.mcjjkp.techniques;

import com.mcgamer.mcjjkp.util.GoreCoreNBTInterfaces;
import net.minecraft.commands.arguments.NbtTagArgument;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class InnateTechnique implements GoreCoreNBTInterfaces.ReadableWritable {

    private final List<ExtensionTechnique> innateTechniques;

    @Nullable
    private UUID parentTechniqueId;

    public InnateTechnique() {
        this(null);
    }

    public InnateTechnique(UUID parentTechniqueId) {
        innateTechniques = new ArrayList<>();
        this.parentTechniqueId = parentTechniqueId;
    }

    protected void addTechnique(String abilityName) {
        innateTechniques.add(ExtensionTechniques.get(abilityName));
    }

    public abstract String getName();

    public abstract UUID getId();

    public byte getNetworkId() {
        return InnateTechniques.getNetworkId(getId());
    }

    public List<ExtensionTechnique> getAllTechniques() {
        return innateTechniques;
    }

    @Override
    public void readFromNBT(NbtTagArgument nbt) {
    }

    @Override
    public void writeToNBT(NbtTagArgument nbt) {
    }
}
