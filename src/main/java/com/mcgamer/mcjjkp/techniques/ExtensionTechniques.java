package com.mcgamer.mcjjkp.techniques;

public enum ExtensionTechniques {
    FLOWING_RED_SCALE("flowing_red_scale"),
    FLOWING_RED_SCALE_STACK("flowing_red_scale_stack");

    private final String id;

    ExtensionTechniques(String id) {
        this.id = id;
    }

    public String getId() { return id; }
}
