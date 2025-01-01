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
            () -> AttachmentType.builder(() -> 20).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> CURSED_ENERGY = ATTACHMENT_TYPES.register("cursed_energy",
            () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<String>> INNATE_TECHNIQUE = ATTACHMENT_TYPES
            .register("innate_technique", () -> AttachmentType.builder(() -> "").serialize(Codec.STRING)
                    .copyOnDeath().build());
    public static final Supplier<AttachmentType<Boolean>> PLAYER_HAS_JOINED = ATTACHMENT_TYPES
            .register("player_has_joined", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL)
                    .copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> GRADE_PROGRESS = ATTACHMENT_TYPES
            .register("grade_progress", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> PHYSICAL_PROWESS = ATTACHMENT_TYPES
            .register("physical_prowess", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT)
                    .copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> DEFENSE = ATTACHMENT_TYPES
            .register("defense", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT)
                    .copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> WEAPONS_MASTERY = ATTACHMENT_TYPES
            .register("weapons_mastery", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT)
                    .copyOnDeath().build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
