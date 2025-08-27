package com.mcgamer.mcjjkp.attachments;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.networking.ModMessages;
import com.mcgamer.mcjjkp.networking.packets.S2CSyncCursedEnergy;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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

    public static final Supplier<AttachmentType<Integer>> LAST_ARROW_PRICK = ATTACHMENT_TYPES
            .register("last_arrow_prick", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

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

    /**
     * Helper functions
     */
    public static int getCursedEnergy(Player player) {
        Integer data = player.getData(CURSED_ENERGY_AVAILABLE);
        return data != null ? data : 0;
    }

    public static boolean hasCursedEnergy(Player player) {
        return player.hasData(CURSED_ENERGY_AVAILABLE);
    }

    public static void setCursedEnergy(Player player, Integer energy) {
        player.setData(CURSED_ENERGY_AVAILABLE, energy);
        ModMessages.sendToPlayerClient(new S2CSyncCursedEnergy(player.getData(CURSED_ENERGY_AVAILABLE)),
                (ServerPlayer)player);
    }

    public static void consumeCursedEnergy(Player player, Integer energyCost) {
        player.setData(CURSED_ENERGY_AVAILABLE, player.getData(CURSED_ENERGY_AVAILABLE) - energyCost);
        ModMessages.sendToPlayerClient(new S2CSyncCursedEnergy(player.getData(CURSED_ENERGY_AVAILABLE)),
                (ServerPlayer)player);
    }
}
