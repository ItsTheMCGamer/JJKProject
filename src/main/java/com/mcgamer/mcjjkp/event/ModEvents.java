package com.mcgamer.mcjjkp.event;

import com.mcgamer.mcjjkp.Config;
import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
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

import static com.mcgamer.mcjjkp.attachments.ModDataAttachments.*;
import static com.mcgamer.mcjjkp.util.ModDamageTypes.HAEMORRHAGE;

@EventBusSubscriber(modid = JJKMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void onUseArrow(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (event.getItemStack().is(Items.ARROW) && !event.getLevel().isClientSide && player.getData(INNATE_TECHNIQUE)
                .equals("blood_manipulation") && !player.getCooldowns().isOnCooldown(event.getItemStack().getItem())) {
            player.getCooldowns().addCooldown(event.getItemStack().getItem(), 15);

            player.hurt(new DamageSource(player.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(HAEMORRHAGE)), 1f);
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
        player.setData(LAST_ARROW_PRICK, player.getData(LAST_ARROW_PRICK) + 1);

        if(player.getData(CURSED_ENERGY_AVAILABLE) < player.getData(CURSED_ENERGY_MAX) && player.tickCount % 20 == 0) {
            player.setData(CURSED_ENERGY_AVAILABLE, player.getData(CURSED_ENERGY_AVAILABLE) + 2);
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
                player.addEffect(new MobEffectInstance(ModEffects.BLEEDING_EFFECT, 600, 0, false,
                        true, true), player);
            } else if(player.getData(BLOOD_DRAWN) > 5){
                player.addEffect(new MobEffectInstance(ModEffects.BLEEDING_EFFECT, 600, 1, false,
                        true, true), player);
            }
        }

        if(player.getData(BLOOD_DRAWN) > 0 && player.getData(LAST_ARROW_PRICK) >= 1800) {
            player.setData(BLOOD_DRAWN, player.getData(BLOOD_DRAWN) - 1);
            player.setData(LAST_ARROW_PRICK, 0);
        }
    }
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        TechniquesCommand.register(event.getDispatcher());
        TestCommand.register(event.getDispatcher());
    }
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            String innateTechniqueName = player.getData(INNATE_TECHNIQUE) != null ? player.getData(INNATE_TECHNIQUE) : null;
            for (ExtensionTechnique technique : ExtensionTechniqueRegistry.getAllTechniques()) {
                if (technique.getParentTechnique().equals(innateTechniqueName) && technique.isToggleable()) {
                    technique.setActive(false);
                    ModMessages.sendToPlayerClient(new S2CToggleTechnique(technique.isActive(), technique.getName()), player);
                }
            }
            ModDataAttachments.setCursedEnergy(player, 0);
        }
    }
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            String innateTechniqueName = player.getData(INNATE_TECHNIQUE) != null ? player.getData(INNATE_TECHNIQUE) : null;
            for (ExtensionTechnique technique : ExtensionTechniqueRegistry.getAllTechniques()) {
                if (technique.getParentTechnique().equals(innateTechniqueName) && technique.isToggleable()) {
                    technique.setActive(false);
                    ModMessages.sendToPlayerClient(new S2CToggleTechnique(technique.isActive(), technique.getName()), player);
                }
            }
        }

        if(Config.randomAssignTechniques && !event.getEntity().getData(PLAYER_HAS_JOINED)) {
            event.getEntity().setData(PLAYER_HAS_JOINED, true);
            event.getEntity().setData(INNATE_TECHNIQUE, "blood_manipulation");

        }

        if(!event.getEntity().level().isClientSide) {
            ModMessages.sendToPlayerClient(new S2CSyncCursedEnergy(event.getEntity()
                    .getData(CURSED_ENERGY_AVAILABLE)), (ServerPlayer)event.getEntity());
        }
    }
}
