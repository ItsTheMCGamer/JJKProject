package com.mcgamer.mcjjkp.techniques.blood_manipulation;

import com.mcgamer.mcjjkp.techniques.InnateTechnique;
import net.minecraft.commands.arguments.NbtTagArgument;

import java.awt.*;
import java.util.UUID;

public class BloodManipulationTechnique extends InnateTechnique {

    public static UUID ID = UUID.fromString("7c80ddaa-36c7-4b52-8174-41e45d8bc3bc");

    public BloodManipulationTechnique() {
        addTechnique("flowing_red_scale");
    }

    @Override
    public void readFromNBT(NbtTagArgument nbt) {

    }

    @Override
    public void writeToNBT(NbtTagArgument nbt) {

    }

    @Override
    public String getName() {
        return "blood_manipulation";
    }

    @Override
    public UUID getId() {
        return ID;
    }

}
