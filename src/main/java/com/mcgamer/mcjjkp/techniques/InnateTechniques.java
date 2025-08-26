package com.mcgamer.mcjjkp.techniques;

import javax.annotation.Nullable;
import java.util.*;

public class InnateTechniques {

    private static final List<InnateTechnique> innateTechniques = new ArrayList<>();
    private static final Map<UUID, InnateTechnique> innateTechniquesById = new HashMap<>();
    private static final Map<String, InnateTechnique> innateTechniquesByName = new HashMap<>();
    private static final Map<UUID, Byte> networkIdByTechnique = new HashMap<>();
    private static final Map<Byte, UUID> networkIdToTechnique = new HashMap<>();
    private static byte nextNetworkId = 1;

    @Nullable
    public static InnateTechnique get(@Nullable UUID id) {
        return innateTechniquesById.get(id);
    }

    @Nullable
    public static InnateTechnique get(String name) {
        return innateTechniquesById.get(name);
    }

    @Nullable
    public static String getName(UUID id) {
        InnateTechnique innateTechnique = get(id);
        return innateTechnique != null ? innateTechnique.getName() : null;
    }

    @Nullable
    public static byte getNetworkId(UUID id) {
        return networkIdByTechnique.get(id);
    }

    @Nullable
    public static byte getNetworkId(InnateTechnique style) {
        return getNetworkId(style.getId());
    }

    public static InnateTechnique get(byte networkId) {
        UUID id = networkIdToTechnique.get(networkId);
        return get(id);
    }

    public static List<InnateTechnique> all() {
        return innateTechniques;
    }

    public static void register(InnateTechnique innateTechnique) {
        innateTechniques.add(innateTechnique);
        innateTechniquesById.put(innateTechnique.getId(), innateTechnique);
        innateTechniquesByName.put(innateTechnique.getName(), innateTechnique);

        byte networkId = nextNetworkId++;
        networkIdByTechnique.put(innateTechnique.getId(), networkId);
        networkIdToTechnique.put(networkId, innateTechnique.getId());

    }
}
