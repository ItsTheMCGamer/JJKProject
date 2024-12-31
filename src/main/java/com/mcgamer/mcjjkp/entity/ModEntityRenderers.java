package com.mcgamer.mcjjkp.entity;

import com.mcgamer.mcjjkp.entity.client.BloodTippedArrowRenderer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModEntityRenderers {
    private static final Map<EntityType<?>, EntityRendererProvider<?>> PROVIDERS = new Object2ObjectOpenHashMap<>();

    public static <T extends Entity> void register(EntityType<? extends T> entityType, EntityRendererProvider<T> provider) {
        PROVIDERS.put(entityType, provider);
    }

    static {
        //register(ModEntities.BLOOD_TIPPED_ARROW_ENTITY.get(), BloodTippedArrowRenderer::new);

    }
}
