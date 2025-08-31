package com.mcgamer.mcjjkp.techniques;

import com.mcgamer.mcjjkp.techniques.blood_manipulation.BloodEdgeTechnique;
import com.mcgamer.mcjjkp.techniques.blood_manipulation.FlowingRedScaleStackTechnique;
import com.mcgamer.mcjjkp.techniques.blood_manipulation.FlowingRedScaleTechnique;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExtensionTechniqueRegistry {
    private static final Map<ExtensionTechniques, ExtensionTechnique> TECHNIQUES = new HashMap<>();

    public static void init() {
        register(ExtensionTechniques.FLOWING_RED_SCALE, new FlowingRedScaleTechnique());
        register(ExtensionTechniques.FLOWING_RED_SCALE_STACK, new FlowingRedScaleStackTechnique());
        register(ExtensionTechniques.BLOOD_EDGE, new BloodEdgeTechnique());
    }

    private static void register(ExtensionTechniques type, ExtensionTechnique technique) {
        TECHNIQUES.put(type, technique);
    }

    public static ExtensionTechnique getTechnique(ExtensionTechniques type) {
        return TECHNIQUES.get(type);
    }

    public static Optional<ExtensionTechnique> getTechniqueByName(String name) {
        try {
            ExtensionTechniques techniqueType = ExtensionTechniques.valueOf(name.toUpperCase());
            return Optional.ofNullable(TECHNIQUES.get(techniqueType));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static Collection<ExtensionTechnique> getAllTechniques() {
        return TECHNIQUES.values();
    }
}