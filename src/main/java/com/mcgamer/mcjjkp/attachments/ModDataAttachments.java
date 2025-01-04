package com.mcgamer.mcjjkp.attachments;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.techniques.blood_manipulation.FlowingRedScale;
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
    public static final Supplier<AttachmentType<Integer>> CURSED_ENERGY = ATTACHMENT_TYPES.register("cursed_energy",
            () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<String>> INNATE_TECHNIQUE = ATTACHMENT_TYPES
            .register("innate_technique", () -> AttachmentType.builder(() -> "").serialize(Codec.STRING)
                    .copyOnDeath().build());
    public static final Supplier<AttachmentType<Boolean>> PLAYER_HAS_JOINED = ATTACHMENT_TYPES
            .register("player_has_joined", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL)
                    .copyOnDeath().build());
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

    /**
     * Cooldowns
     */
    public static final Supplier<AttachmentType<Integer>> ARROW_PRICK_COOLDOWN =
            ATTACHMENT_TYPES.register("arrow_prick_cooldown", () -> AttachmentType.builder(() -> 20)
                    .serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> COOLDOWN_SLOT_ONE =
            ATTACHMENT_TYPES.register("cooldown_slot_one", () -> AttachmentType.builder(() -> {
                if(1 != 0) {
                    return  2 * FlowingRedScale.getCooldown();
                } else {
                    return 0;
                }})
                    .serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> COOLDOWN_SLOT_TWO =
            ATTACHMENT_TYPES.register("cooldown_slot_two", () -> AttachmentType.builder(() -> 1000000)
                    .serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> COOLDOWN_SLOT_THREE =
            ATTACHMENT_TYPES.register("cooldown_slot_three", () -> AttachmentType.builder(() -> 1000000)
                    .serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> COOLDOWN_SLOT_FOUR =
            ATTACHMENT_TYPES.register("cooldown_slot_four", () -> AttachmentType.builder(() -> 1000000)
                    .serialize(Codec.INT).build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
