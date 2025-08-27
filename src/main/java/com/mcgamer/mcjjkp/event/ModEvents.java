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
import com.mcgamer.mcjjkp.networking.packets.C2SRemoveModifiers;
import com.mcgamer.mcjjkp.networking.packets.S2CFlowingRedScaleActive;
import com.mcgamer.mcjjkp.networking.packets.S2CSyncCursedEnergy;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import java.util.Random;

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
            applyHaemorrhageEffect(player);
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
            Random random = new Random();
            var randInt = random.nextInt(3);
            event.getEntity().setData(INNATE_TECHNIQUE, "blood_manipulation");
            // assignTechnique(event.getEntity(), randInt);

        }

        /** Syncs Cursed Energy */
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
    public static void onLivingEntityDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if(entity instanceof Player) {
            if (entity.getData(FLOWING_RED_SCALE_ACTIVE)) {
                ModMessages.sendToServer(new C2SRemoveModifiers(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID,
                        "flowing_red_scale.strength_boost"), Attributes.ATTACK_DAMAGE));
                ModMessages.sendToServer(new C2SRemoveModifiers(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID,
                        "flowing_red_scale.speed_boost"), Attributes.MOVEMENT_SPEED));
                ModMessages.sendToServer(new C2SRemoveModifiers(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID,
                        "flowing_red_scale.health_boost"), Attributes.MAX_HEALTH));
            }
            if (entity.getData(FLOWING_RED_SCALE_STACK_ACTIVE)) {
                ModMessages.sendToServer(new C2SRemoveModifiers(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID,
                        "flowing_red_scale_stack.strength_boost"), Attributes.ATTACK_DAMAGE));
                ModMessages.sendToServer(new C2SRemoveModifiers(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID,
                        "flowing_red_scale_stack.speed_boost"), Attributes.MOVEMENT_SPEED));
                ModMessages.sendToServer(new C2SRemoveModifiers(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID,
                        "flowing_red_scale_stack.health_boost"), Attributes.MAX_HEALTH));
            }
        }
    }


    private static void applyHaemorrhageEffect(Player player) {
        if(player.getData(BLOOD_DRAWN) <= 5 && player.getData(BLOOD_DRAWN) > 0) {
            System.out.println("no");
            player.addEffect(new MobEffectInstance(ModEffects.HAEMORRHAGE_EFFECT, 2000, 0, false,
                    true), player);
        } else {
            player.addEffect(new MobEffectInstance(ModEffects.HAEMORRHAGE_EFFECT, 2000, 1, false,
                    true), player);
        }
    }
    public static void assignTechnique(Player player, Integer technique) {
        switch (technique) {
            case 1, 2:
                player.setData(INNATE_TECHNIQUE, "blood_manipulation");
            case 0:
                System.out.println("hello");
        }

    }
}
