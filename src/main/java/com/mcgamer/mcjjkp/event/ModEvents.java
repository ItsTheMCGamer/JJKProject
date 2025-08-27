package com.mcgamer.mcjjkp.event;

import com.mcgamer.mcjjkp.Config;
import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.command.TechniquesCommand;
import com.mcgamer.mcjjkp.command.TestCommand;
import com.mcgamer.mcjjkp.components.ModDataComponents;
import com.mcgamer.mcjjkp.effect.ModEffects;
import com.mcgamer.mcjjkp.item.ModItems;
import com.mcgamer.mcjjkp.networking.ModMessages;
import com.mcgamer.mcjjkp.networking.packets.S2CSyncCursedEnergy;
import com.mcgamer.mcjjkp.networking.packets.S2CToggleTechnique;
import com.mcgamer.mcjjkp.techniques.ExtensionTechnique;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniqueRegistry;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniques;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Map;

import static com.mcgamer.mcjjkp.attachments.ModDataAttachments.*;
import static com.mcgamer.mcjjkp.util.ModDamageTypes.HAEMORRHAGE;

@EventBusSubscriber(modid = JJKMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    private static int arrowPrickCooldown = 0;

    @SubscribeEvent
    public static void onUseArrow(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (event.getItemStack().is(Items.ARROW) && !event.getLevel().isClientSide && player.getData(INNATE_TECHNIQUE)
                .equals("blood_manipulation") && !player.getCooldowns().isOnCooldown(event.getItemStack().getItem())) {
            player.getCooldowns().addCooldown(event.getItemStack().getItem(), 10);

            player.hurt(new DamageSource(player.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(HAEMORRHAGE)), 2f);
            event.getItemStack().consume(1, player);

            ItemStack bloodTippedArrow = ModItems.BLOOD_TIPPED_ARROW_ITEM.toStack();
            bloodTippedArrow.set(ModDataComponents.ARROW_OWNER, player.getName().toString());
            player.addItem(bloodTippedArrow);

            player.setData(BLOOD_DRAWN, player.getData(BLOOD_DRAWN) + 1);
        }
    }
    @SubscribeEvent
    public static void tick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        Map<String, Integer> cooldowns = player.getData(TECHNIQUES_COOLDOWN);

        arrowPrickCooldown++;

        if(player.getData(CURSED_ENERGY_AVAILABLE) < player.getData(CURSED_ENERGY_MAX) && player.tickCount % 60 == 0) {
            player.setData(CURSED_ENERGY_AVAILABLE, player.getData(CURSED_ENERGY_AVAILABLE) + 1);

            /** Syncs Cursed Energy */
            if(!player.level().isClientSide) {
                if(player.getData(CURSED_ENERGY_AVAILABLE) < 0) {
                    player.setData(CURSED_ENERGY_AVAILABLE, 0);
                    ModMessages.sendToPlayerClient(new S2CSyncCursedEnergy(player.getData(CURSED_ENERGY_AVAILABLE)),
                            (ServerPlayer)player);
                }
            ModMessages.sendToPlayerClient(new S2CSyncCursedEnergy(player.getData(CURSED_ENERGY_AVAILABLE)),
                    (ServerPlayer)player);
            }
        }

        if (player.hasData(BLOOD_DRAWN)) {
            if(player.getData(BLOOD_DRAWN) <= 5 && player.getData(BLOOD_DRAWN) > 0) {
                player.addEffect(new MobEffectInstance(ModEffects.HAEMORRHAGE_EFFECT, 2000, 0, false,
                        true), player);
            } else {
                player.addEffect(new MobEffectInstance(ModEffects.HAEMORRHAGE_EFFECT, 2000, 1, false,
                        true), player);
            }
        }
        if(player.getData(BLOOD_DRAWN) > 0 && arrowPrickCooldown >= 2000) {
            player.setData(BLOOD_DRAWN, player.getData(BLOOD_DRAWN) - 1);
            arrowPrickCooldown = 0;
        }
    }
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if(Config.randomAssignTechniques && !event.getEntity().getData(PLAYER_HAS_JOINED)) {
            event.getEntity().setData(PLAYER_HAS_JOINED, true);
            event.getEntity().setData(INNATE_TECHNIQUE, "blood_manipulation");

        }

        if(!event.getEntity().level().isClientSide) {
            ModMessages.sendToPlayerClient(new S2CSyncCursedEnergy(event.getEntity()
                    .getData(CURSED_ENERGY_AVAILABLE)), (ServerPlayer)event.getEntity());
        }
    }
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        TechniquesCommand.register(event.getDispatcher());
        TestCommand.register(event.getDispatcher());
    }
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            String techniqueName = player.getData(INNATE_TECHNIQUE);
            ExtensionTechnique technique = ExtensionTechniqueRegistry.getTechnique(ExtensionTechniques.valueOf(techniqueName));
            if (technique != null && technique.isToggleable() && technique.isActive()) {
                technique.setActive(false);
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            String techniqueName = player.getData(INNATE_TECHNIQUE);
            ExtensionTechniques type = ExtensionTechniques.valueOf(techniqueName);
            if (type != null) {
                ExtensionTechnique technique = ExtensionTechniqueRegistry.getTechnique(type);
                if (technique != null) {
                    ModMessages.sendToServer(new S2CToggleTechnique(technique.isActive(), type.name()));
                }
            }
        }
    }
}
