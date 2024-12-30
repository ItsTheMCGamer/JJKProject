package com.mcgamer.mcjjkp.entity;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.entity.custom.BloodTippedArrowEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, JJKMod.MOD_ID);

    public static final Supplier<EntityType<BloodTippedArrowEntity>> BLOOD_TIPPED_ARROW_ENTITY =
            ENTITY_TYPES.register("blood_tipped_arrow", () -> EntityType.Builder.
                            <BloodTippedArrowEntity>of(BloodTippedArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20)
                    .build("blood_tipped_arrow_key"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
