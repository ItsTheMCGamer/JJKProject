package com.mcgamer.mcjjkp.util;

import com.mcgamer.mcjjkp.JJKMod;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes extends DamageSources{

    public ModDamageTypes(RegistryAccess registry) {
        super(registry);
    }

    public static final ResourceKey<DamageType> HAEMORRHAGE = ResourceKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "haemorrhage"));


}
