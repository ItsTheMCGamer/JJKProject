package com.mcgamer.mcjjkp.attachments;

import com.mcgamer.mcjjkp.JJKMod;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.function.Supplier;

public class ModDataAttachments {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.
            create(NeoForgeRegistries.ATTACHMENT_TYPES, JJKMod.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> BLOOD_DRAWN = ATTACHMENT_TYPES.register("blood_drawn",
            () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> CURSED_ENERGY_MAX = ATTACHMENT_TYPES
            .register("cursed_energy_max", () -> AttachmentType.builder(() -> 100)
                    .serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> CURSED_ENERGY_AVAILABLE = ATTACHMENT_TYPES
            .register("cursed_energy_available", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<String>> INNATE_TECHNIQUE = ATTACHMENT_TYPES
            .register("innate_technique", () -> AttachmentType.builder(() -> "").serialize(Codec.STRING)
                    .copyOnDeath().build());
    public static final Supplier<AttachmentType<Boolean>> PLAYER_HAS_JOINED = ATTACHMENT_TYPES
            .register("player_has_joined", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL)
                    .copyOnDeath().build());

    /**
     * Cooldowns
     */
    public static final AttachmentType<HashMap<String, Integer>> TECHNIQUES_COOLDOWN =
            AttachmentType.builder(() -> new HashMap<String, Integer>()).build();

    /**
     * Continuous techniques check
     */
    public static final Supplier<AttachmentType<Boolean>> FLOWING_RED_SCALE_ACTIVE = ATTACHMENT_TYPES
            .register("flowing_red_scale_active", () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL).build());
    public static final Supplier<AttachmentType<Boolean>> FLOWING_RED_SCALE_STACK_ACTIVE = ATTACHMENT_TYPES
            .register("flowing_red_scale_stack_active", () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL).build());
    public static final Supplier<AttachmentType<Boolean>> LIMITLESS_ACTIVE = ATTACHMENT_TYPES
            .register("limitless_active", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build());

    /**
     * Attachments for ranking/level-up/skill tree system
     */
    public static final Supplier<AttachmentType<Integer>> GRADE_PROGRESS_XP = ATTACHMENT_TYPES
            .register("grade_progress_xp", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> STRENGTH_XP = ATTACHMENT_TYPES
            .register("strength_xp", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT)
                    .copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> SPEED_XP = ATTACHMENT_TYPES
            .register("speed_xp", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT)
                    .copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> DEFENSE_XP = ATTACHMENT_TYPES
            .register("defense_xp", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT)
                    .copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> WEAPON_PROFICIENCY_XP = ATTACHMENT_TYPES
            .register("weapon_proficiency_xp", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT)
                    .copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> CURSED_ENERGY_XP = ATTACHMENT_TYPES
            .register("cursed_energy_xp", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT)
                    .copyOnDeath().build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
