package com.mcgamer.mcjjkp.components;

import com.mcgamer.mcjjkp.JJKMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, JJKMod.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ARROW_OWNER =
            register("arrow_owner", builder -> builder.persistent(ExtraCodecs.PLAYER_NAME));



    private static <T>DeferredHolder<DataComponentType<?>,
            DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENTS_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS_TYPES.register(eventBus);
    }

}
