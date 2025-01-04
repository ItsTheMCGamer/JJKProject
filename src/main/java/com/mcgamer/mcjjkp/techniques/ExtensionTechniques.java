package com.mcgamer.mcjjkp.techniques;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtensionTechniques {

    private static final List<ExtensionTechnique> techniques = new ArrayList<>();
    private static final Map<String, ExtensionTechnique> abilitiesByName = new HashMap<>();

    @Nullable
    public static ExtensionTechnique get(String name) {
        return abilitiesByName.get(name);
    }

    public static List<ExtensionTechnique> all() {
        return techniques;
    }

    public static void register(ExtensionTechnique technique) {
        techniques.add(technique);
        abilitiesByName.put(technique.getName(), technique);
    }
}
