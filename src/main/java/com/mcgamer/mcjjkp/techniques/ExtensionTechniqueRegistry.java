package com.mcgamer.mcjjkp.techniques;

import com.mcgamer.mcjjkp.techniques.blood_manipulation.FlowingRedScaleTechnique;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExtensionTechniqueRegistry {
    private static final Map<ExtensionTechniques, ExtensionTechnique> TECHNIQUES = new HashMap<>();

    public static void init() {
        register(ExtensionTechniques.FLOWING_RED_SCALE, new FlowingRedScaleTechnique());
        // Register more techniques here
    }

    private static void register(ExtensionTechniques type, ExtensionTechnique technique) {
        TECHNIQUES.put(type, technique);
    }

    public static ExtensionTechnique getTechnique(ExtensionTechniques type) {
        return TECHNIQUES.get(type);
    }

    public static Optional<ExtensionTechnique> getTechniqueByName(String name) {
        return Optional.ofNullable(TECHNIQUES.get(name));
    }

    public static Collection<ExtensionTechnique> getAllTechniques() {
        return TECHNIQUES.values();
    }
}
