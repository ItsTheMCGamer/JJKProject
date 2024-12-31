package com.mcgamer.mcjjkp.attachments;

import com.mcgamer.mcjjkp.JJKMod;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataAttachments {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.
            create(NeoForgeRegistries.ATTACHMENT_TYPES, JJKMod.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> BLOOD_DRAWN = ATTACHMENT_TYPES.register("blood_drawn",
            () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> COOLDOWN = ATTACHMENT_TYPES.register("cooldown",
            () -> AttachmentType.builder(() -> -1).serialize(Codec.INT).build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
