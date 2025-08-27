package com.mcgamer.mcjjkp.techniques;

public enum ExtensionTechniques {
    FLOWING_RED_SCALE("flowing_red_scale");

    private final String id;

    ExtensionTechniques(String id) {
        this.id = id;
    }

    public String getId() { return id; }
}
